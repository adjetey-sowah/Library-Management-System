-- ADD A LIBRARY RESOURCE;

DELIMITER //

CREATE PROCEDURE insertLibraryResource(
    IN p_title VARCHAR(255),
    IN p_status VARCHAR(20),
    IN p_resource_type VARCHAR(20)
)
BEGIN
INSERT INTO library_resource (title, status, resource_type)
VALUES (p_title, p_status, p_resource_type);
END //

DELIMITER ;


    -- ADD A BOOK

    DELIMITER //

CREATE PROCEDURE insertBook(
    IN p_book_id BIGINT,
    IN p_author VARCHAR(255),
    IN p_isbn VARCHAR(255),
    IN p_genre VARCHAR(50),
    IN p_publication_date DATE
)
BEGIN
INSERT INTO Book (book_id, author, isbn, genre, publication_date)
VALUES (p_book_id, p_author, p_isbn, p_genre, p_publication_date);
END //

DELIMITER ;


    DELIMITER //

CREATE PROCEDURE insertJournal(
    IN p_journal_id BIGINT,
    IN p_issue_no VARCHAR(255),
    IN p_frequency VARCHAR(255)
)
BEGIN
INSERT INTO Journal (journal_id, issue_no, frequency)
VALUES (p_journal_id, p_issue_no, p_frequency);
END //

DELIMITER ;


