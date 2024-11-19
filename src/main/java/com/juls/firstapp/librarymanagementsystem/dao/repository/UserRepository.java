package com.juls.firstapp.librarymanagementsystem.dao.repository;


import com.juls.firstapp.librarymanagementsystem.config.DatabaseConfig;
import com.juls.firstapp.librarymanagementsystem.dao.interfaces.UserDAO;
import com.juls.firstapp.librarymanagementsystem.model.enums.MembershipType;
import com.juls.firstapp.librarymanagementsystem.model.enums.UserRole;
import com.juls.firstapp.librarymanagementsystem.model.users.Librarian;
import com.juls.firstapp.librarymanagementsystem.model.users.Patron;
import com.juls.firstapp.librarymanagementsystem.model.users.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;


import java.sql.*;
import java.util.LinkedList;

public class UserRepository implements UserDAO {

    private final Connection connection;


    public UserRepository() throws SQLException, ClassNotFoundException {
         this.connection = new DatabaseConfig().getConnection();

    }




    public int insertUser(User user){
        String sql = "INSERT INTO Users (name, email, phone, role) VALUES (?, ?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            String role = String.valueOf(user.getRole().toString());
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPhoneNum());
            preparedStatement.setString(4, role);

            preparedStatement.executeUpdate();

            // Retrieve the generated key
            try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
                if(generatedKeys.next()){
                    return generatedKeys.getInt(1);     // Return the generated id
                }
                else {
                    throw new SQLException("Inserting user failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Could not insert User: "+e.getMessage());
        }

    }

    public void insertPatron(Patron patron){

        int userId = insertUser(patron);

        String sql = "INSERT INTO patron (user_id, membership_type) VALUES (?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1,userId);
            preparedStatement.setString(2, patron.getMembershipType().name());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertLibrarian(Librarian librarian){

        int id = insertUser(librarian);
        String sql = "INSERT INTO librarian (user_id, password) VALUES (?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1,id);
            preparedStatement.setString(2, passwordEncoder().encode(librarian.getPassword()));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public LinkedList<User> getAllUsers() throws SQLException {

        // The stored procedure name
        String sql = "{call get_all_users()}";

        // Prepare the callable statement
        CallableStatement callableStatement = connection.prepareCall(sql);

        ResultSet resultSet = callableStatement.executeQuery();
        LinkedList<User> users = new LinkedList<>();

        while (resultSet.next()){
            var user = new User();
            user.setUserId(resultSet.getLong("user_id"));
            user.setName(resultSet.getString("name"));
            user.setEmail(resultSet.getString("email"));
            user.setPhoneNum(resultSet.getString("phone"));
            user.setRole(UserRole.valueOf(resultSet.getString("role")));

            users.add(user);
        }

        return users;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        if (user instanceof Librarian){
            String sql = "UPDATE librarian SET password = ? where user_id = ?";

            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1,passwordEncoder().encode(((Librarian) user).getPassword()));
                preparedStatement.setLong(2,user.getUserId());
                preparedStatement.executeUpdate();
            }
            catch (SQLException e){
                throw new RuntimeException("Could not update user");
            }
        }
        else if(user instanceof Patron){
            String sql = "UPDATE patron SET membership_type = ? where user_id = ?";

            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1,((Patron) user).getMembershipType().toString());
                preparedStatement.setLong(2,user.getUserId());
                preparedStatement.executeUpdate();
            }
            catch (SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }

        String sql = "UPDATE Users SET name= ?, email = ?, phone = ?, role = ? where user_id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getEmail());
            preparedStatement.setString(3,user.getPhoneNum());
            preparedStatement.setString(4,user.getRole().toString());
            preparedStatement.setLong(5,user.getUserId());
            int affectedRows =preparedStatement.executeUpdate();
            return affectedRows > 1;
        }
    }

    @Override
    public boolean deleteUser(Long id) throws SQLException {

        String sql = "DELETE FROM users WHERE user_id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1,id);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        }
    }

    public String getLibrarianPassword(Long id) {
        String sql = "Select password from librarian where user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
                String password = "";
            while (resultSet.next()){
                password = resultSet.getString(1);
            }
                return password;

        } catch (SQLException e) {
            throw new RuntimeException("User not found: " + e.getMessage());
        }
    }

    public User getUserbyEmail(String email){
        String sql = "Select * from users where email=?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();

                User user = new User();
            while (resultSet.next()){
                user.setUserId(resultSet.getLong(1));
                user.setPhoneNum(resultSet.getString("phone"));
                user.setRole(UserRole.valueOf(resultSet.getString("role")));
            }

            return user;
        } catch (SQLException e) {
            throw new RuntimeException("No user with email found");
        }
    }

    public PasswordEncoder passwordEncoder(){;
        return new BCryptPasswordEncoder();
    }
}
