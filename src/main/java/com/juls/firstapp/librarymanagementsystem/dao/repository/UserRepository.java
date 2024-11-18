package com.juls.firstapp.librarymanagementsystem.dao.repository;


import com.juls.firstapp.librarymanagementsystem.config.DatabaseConfig;
import com.juls.firstapp.librarymanagementsystem.dao.interfaces.UserDAO;
import com.juls.firstapp.librarymanagementsystem.model.enums.MembershipType;
import com.juls.firstapp.librarymanagementsystem.model.enums.UserRole;
import com.juls.firstapp.librarymanagementsystem.model.users.Librarian;
import com.juls.firstapp.librarymanagementsystem.model.users.Patron;
import com.juls.firstapp.librarymanagementsystem.model.users.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.sql.*;
import java.util.LinkedList;

public class UserRepository implements UserDAO {

    private final Connection connection;


    public UserRepository() throws SQLException, ClassNotFoundException {
         this.connection = new DatabaseConfig().getConnection();

    }


    @Override
    public boolean updatePatron(Patron patron) {
        String sql = "UPDATE User SET name= ?, email = ?, phone = ?, role = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,patron.getName());
            preparedStatement.setString(2,patron.getEmail());
            preparedStatement.setString(3,patron.getPhoneNum());

            int affectedRows =preparedStatement.executeUpdate();
            return affectedRows > 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public boolean updateLibrarian(Librarian librarian) throws SQLException {
        String sql = "UPDATE User SET name= ?, email = ?, phone = ?, role = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,librarian.getName());
            preparedStatement.setString(2,librarian.getEmail());
            preparedStatement.setString(3,librarian.getPhoneNum());

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

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
