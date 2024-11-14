CREATE TABLE users (user_id BIGINT AUTO_INCREMENT PRIMARY KEY UNIQUE , name VARCHAR(255) NOT NULL ,
                    email VARCHAR(255), phone VARCHAR(13), role VARCHAR(10) );

CREATE TABLE  Patron(user_id BIGINT PRIMARY KEY UNIQUE , membership_type VARCHAR(50) NOT NULL, FOREIGN KEY (user_id)
    references users(user_id));

CREATE TABLE Librarian(user_id BIGINT  PRIMARY KEY UNIQUE, password VARCHAR(255), FOREIGN KEY (user_id)
    references users(user_id));


CREATE TABLE library_resource (
                                  resource_id BIGINT PRIMARY KEY AUTO_INCREMENT UNIQUE,
                                  title VARCHAR(255) NOT NULL,
                                  status VARCHAR(20) NOT NULL,
                                  resource_type VARCHAR(20) CHECK (resource_type IN ('BOOK', 'JOURNAL', 'MEDIA'))
);

CREATE TABLE Book(
                    book_id BIGINT UNIQUE  NOT NULL,
                    author VARCHAR(255),
                    isbn VARCHAR(255),
                    genre VARCHAR(50),
                    publication_date DATE, FOREIGN KEY (book_id) REFERENCES
                        library_resource(resource_id)
                 );

CREATE TABLE Media (
                       media_id BIGINT NOT NULL UNIQUE,
                       format VARCHAR(50) CHECK (format IN ('DVD', 'AUDIO')),
                       FOREIGN KEY (media_id) REFERENCES library_resource(resource_id)
);


CREATE TABLE Journal (
                         journal_id BIGINT PRIMARY KEY NOT NULL UNIQUE ,
                         issue_no varchar(255),
                         frequency varchar(255), FOREIGN KEY (journal_id) REFERENCES  library_resource(resource_id));

