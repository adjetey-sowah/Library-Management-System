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


    DELIMITER //

CREATE PROCEDURE insertMedia(
    IN p_media_id BIGINT,
    IN p_media_format VARCHAR(10)
)

BEGIN
INSERT INTO Media (media_id,format)
VALUES (p_media_id,p_media_format);
END //


CREATE PROCEDURE updateBook(
    bookId BIGINT,
    b_author VARCHAR(255),
    b_isbn VARCHAR(255),
    b_genre VARCHAR(255),
    pub_date DATE
)

BEGIN
    UPDATE book
    set
        book.author = b_author,
        book.isbn = b_isbn,
        book.genre = b_genre,
        publication_date = pub_date

    where book_id = bookId;
END //



CREATE PROCEDURE updateJournal(
    j_id BIGINT,
    j_issue_no VARCHAR(255),
    j_frequency VARCHAR(255)
)

BEGIN
    UPDATE journal
    set issue_no = j_issue_no,
        frequency = j_frequency
    where journal_id = j_id;

    END //




CREATE PROCEDURE updateResource(
    r_id BIGINT,
    r_title VARCHAR(255),
    r_status VARCHAR(20)
)
BEGIN
    UPDATE library_resource
    set title = r_title,
        status = r_status
    where resource_id = r_id;
END //

DELIMITER ;



CREATE PROCEDURE getAllTransactions()
BEGIN
    SELECT *
    FROM transaction t
             LEFT JOIN library_resource lr
                       ON t.resource_id = lr.resource_id
             LEFT JOIN users p
                       ON t.user_id = p.user_id;
END;


create procedure findTransactionRange(
    from_date date,
    to_date date
)

BEGIN
    select * from
        transaction
    where
        borrowed_date between
            from_date and to_date;

end ;
delimiter ;


    DELIMITER ;

