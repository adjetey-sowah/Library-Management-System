package com.juls.firstapp.librarymanagementsystem.model.resources;

import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceStatus;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceType;
import com.juls.firstapp.librarymanagementsystem.model.resource.Journal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class JournalTest {
    @Mock
    private ResourceStatus mockStatus;

    @Mock
    private ResourceType mockType;

    @InjectMocks
    private Journal journal;

    private final String TEST_TITLE = "Test Journal";
    private final String TEST_ISSUE = "Issue 1";
    private final String TEST_FREQUENCY = "Monthly";

    @BeforeEach
    void setUp() {
        journal = spy(new Journal(TEST_TITLE, TEST_ISSUE, TEST_FREQUENCY));
    }

    @Test
    void testJournalConstructorWithAllParameters() {
        assertNotNull(journal);
        assertEquals(TEST_TITLE, journal.getTitle());
        assertEquals(TEST_ISSUE, journal.getIssueNumber());
        assertEquals(TEST_FREQUENCY, journal.getFrequency());
        assertEquals(ResourceType.JOURNAL, journal.getResourceType());
        assertEquals(ResourceStatus.AVAILABLE, journal.getResourceStatus());
    }

    @Test
    void testDefaultConstructor() {
        Journal defaultJournal = spy(new Journal());
//        verify(defaultJournal).setResourceType();
        assertEquals("", defaultJournal.getTitle());
        assertEquals(ResourceType.JOURNAL, defaultJournal.getResourceType());
    }

    @Test
    void testSettersAndGetters() {
        Long resourceId = 1L;
        ResourceStatus mockNewStatus = mock(ResourceStatus.class);

        doNothing().when(journal).setResourceStatus(any(ResourceStatus.class));
        doNothing().when(journal).setResourceId(anyLong());

        journal.setResourceId(resourceId);
        journal.setTitle("New Title");
        journal.setIssueNumber("Issue 2");
        journal.setFrequency("Quarterly");
        journal.setResourceStatus(mockNewStatus);

        verify(journal).setResourceId(resourceId);
        verify(journal).setResourceStatus(mockNewStatus);
        assertEquals("New Title", journal.getTitle());
        assertEquals("Issue 2", journal.getIssueNumber());
        assertEquals("Quarterly", journal.getFrequency());
    }
}