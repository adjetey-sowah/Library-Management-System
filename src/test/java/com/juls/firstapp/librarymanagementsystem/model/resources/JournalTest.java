package com.juls.firstapp.librarymanagementsystem.model.resources;

import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceType;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceType;
import com.juls.firstapp.librarymanagementsystem.model.resource.Journal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JournalTest {

    @InjectMocks
    private Journal journal;

    private final String TEST_TITLE = "Test Journal";
    private final String TEST_ISSUE_NUMBER = "ISSUE_001";
    private final String TEST_FREQUENCY = "Monthly";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        journal = new Journal(TEST_TITLE, TEST_ISSUE_NUMBER, TEST_FREQUENCY);
    }

    @Test
    void testJournalConstructorWithAllParameters() {
        assertNotNull(journal);
        assertEquals(TEST_TITLE, journal.getTitle());
        assertEquals(TEST_ISSUE_NUMBER, journal.getIssueNumber());
        assertEquals(TEST_FREQUENCY, journal.getFrequency());
        assertEquals(ResourceType.JOURNAL, journal.getResourceType());
    }

    @Test
    void testJournalConstructorWithTitleOnly() {
        Journal titleOnlyJournal = new Journal(TEST_TITLE);
        assertNotNull(titleOnlyJournal);
        assertEquals(TEST_TITLE, titleOnlyJournal.getTitle());
        assertEquals(ResourceType.JOURNAL, titleOnlyJournal.getResourceType());
    }

    @Test
    void testJournalDefaultConstructor() {
        Journal defaultJournal = new Journal();
        assertNotNull(defaultJournal);
        assertEquals("", defaultJournal.getTitle());
        assertEquals(ResourceType.JOURNAL, defaultJournal.getResourceType());
    }

    @Test
    void testSettersAndGetters() {
        journal.setIssueNumber("ISSUE_002");
        journal.setFrequency("Weekly");
        journal.setResourceId(2L);
        journal.setTitle("Updated Journal Title");

        assertEquals("ISSUE_002", journal.getIssueNumber());
        assertEquals("Weekly", journal.getFrequency());
        assertEquals(2L, journal.getResourceId());
        assertEquals("Updated Journal Title", journal.getTitle());
    }

    @Test
    void testToString() {
        String expectedToString = "Journal{" +
                "issueNumber='" + TEST_ISSUE_NUMBER + '\'' +
                ", frequency='" + TEST_FREQUENCY + '\'' +
                ", resourceId=null" +
                ", title='" + TEST_TITLE + '\'' +
                ", status=" + journal.getResourceStatus()+
                ", resourceType=" + ResourceType.JOURNAL +
                '}';
        assertEquals(expectedToString, journal.toString());
    }

    @Test
    void testSetResourceType() {
        journal.setResourceType();
        assertEquals(ResourceType.JOURNAL, journal.getResourceType());
    }
}
