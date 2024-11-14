-- Create the LibraryResource table
CREATE TABLE LibraryResource (
  resource_id VARCHAR(255) PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  status VARCHAR(60) NOT NULL
  constraint status_check check (status IN ('AVAILABLE','BORROWED','RESERVED'))
);

-- Create the Book table
CREATE TABLE Book (
  resource_id VARCHAR(255) PRIMARY KEY,
  author VARCHAR(255),
  isbn VARCHAR(255),
  genre INT,
  publication_date DATE,
  FOREIGN KEY (resource_id) REFERENCES LibraryResource(resource_id)
);

-- Create the Journal table
CREATE TABLE Journal (
  resource_id VARCHAR(255) PRIMARY KEY,
  issue_no VARCHAR(255),
  frequency VARCHAR(255),
  FOREIGN KEY (resource_id) REFERENCES LibraryResource(resource_id)
);

-- Create the User table
CREATE TABLE User (
  user_id BIGINT PRIMARY KEY auto_increment,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  phone VARCHAR(13) NOT NULL
);

-- Create the Patron table
CREATE TABLE Patron (
user_id BIGINT primary KEY auto_increment,
  membership_type varchar(50),
  FOREIGN KEY (user_id) REFERENCES User(user_id)
);

-- Create the Librarian table
CREATE TABLE Librarian (
user_id BIGINT PRIMARY KEY auto_increment,
  password VARCHAR(255) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES User(user_id)
  
);

-- Create the Transaction table
CREATE TABLE Transaction (
  transaction_id BIGINT PRIMARY KEY auto_increment,
  resource_id VARCHAR(255),
  user_id BIGINT,
  borrowed_date DATETIME NOT NULL,
  due_date DATETIME NOT NULL,
  returned_date DATETIME NOT NULL,
  FOREIGN KEY (resource_id) REFERENCES LibraryResource(resource_id),
  FOREIGN KEY (user_id) REFERENCES User(user_id)
);

-- Create the Reservations table
CREATE TABLE Reservations (
  reservation_id BIGINT PRIMARY KEY auto_increment,
  user_id BIGINT,
  resource_id VARCHAR(255),
  reservation_date DATETIME NOT NULL,
  status VARCHAR(10) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES User(user_id),
  FOREIGN KEY (resource_id) REFERENCES LibraryResource(resource_id)
);