package com.juls.firstapp.librarymanagementsystem.model.resources;

import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceStatus;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceType;
import com.juls.firstapp.librarymanagementsystem.model.resource.LibraryResource;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class LibraryResourceTest {

    // Concrete implementation of LibraryResource for testing
    private static class TestLibraryResource extends LibraryResource {
        public TestLibraryResource(String title) {
            super(title);
        }

        public TestLibraryResource(String title, ResourceType resourceType) {
            super(title, resourceType);
        }

        @Override
        public void setResourceType() {
            this.resourceType = ResourceType.BOOK; // Default type for testing
        }
    }

    private TestLibraryResource resource;
    private final String TEST_TITLE = "Test Resource";

    @BeforeEach
    void setUp() {
        resource = new TestLibraryResource(TEST_TITLE);
    }

    @Test
    void testConstructorWithTitle() {
        assertNotNull(resource);
        assertEquals(TEST_TITLE, resource.getTitle());
        assertEquals(ResourceStatus.AVAILABLE, resource.getResourceStatus());
        assertNull(resource.getResourceId());
    }

    @Test
    void testConstructorWithTitleAndType() {
        TestLibraryResource resourceWithType = new TestLibraryResource(TEST_TITLE, ResourceType.JOURNAL);
        assertEquals(TEST_TITLE, resourceWithType.getTitle());
        assertEquals(ResourceType.JOURNAL, resourceWithType.getResourceType());
        assertEquals(ResourceStatus.AVAILABLE, resourceWithType.getResourceStatus());
    }

    @Test
    void testSetAndGetTitle() {
        String newTitle = "New Title";
        resource.setTitle(newTitle);
        assertEquals(newTitle, resource.getTitle());
    }

    @Test
    void testSetAndGetResourceId() {
        Long resourceId = 123L;
        resource.setResourceId(resourceId);
        assertEquals(resourceId, resource.getResourceId());
    }

    @Test
    void testSetAndGetResourceStatus() {
        // Test initial status
        assertEquals(ResourceStatus.AVAILABLE, resource.getResourceStatus());

        // Test status change
        resource.setResourceStatus(ResourceStatus.BORROWED);
        assertEquals(ResourceStatus.BORROWED, resource.getResourceStatus());

        // Test another status change
        resource.setResourceStatus(ResourceStatus.BORROWED);
        assertEquals(ResourceStatus.BORROWED, resource.getResourceStatus());
    }

    @Test
    void testGetResourceType() {
        TestLibraryResource resourceWithType = new TestLibraryResource(TEST_TITLE, ResourceType.MEDIA);
        assertEquals(ResourceType.MEDIA, resourceWithType.getResourceType());
    }

    @Test
    void testSetResourceType() {
        resource.setResourceType();
        assertEquals(ResourceType.BOOK, resource.getResourceType());
    }

    @Test
    void testToString() {
        // Setup resource with all fields populated
        resource.setResourceId(1L);
        resource.setResourceStatus(ResourceStatus.BORROWED);
        resource.setResourceType();

        String resourceString = resource.toString();

        // Verify all fields are included in toString
        assertTrue(resourceString.contains("resourceId=1"));
        assertTrue(resourceString.contains("title='" + TEST_TITLE + "'"));
        assertTrue(resourceString.contains("status=" + ResourceStatus.BORROWED));
        assertTrue(resourceString.contains("resourceType=" + ResourceType.BOOK));
    }

    @Test
    void testNullTitle() {
        TestLibraryResource nullTitleResource = new TestLibraryResource(null);
        assertNull(nullTitleResource.getTitle());
    }

    @Test
    void testEmptyTitle() {
        TestLibraryResource emptyTitleResource = new TestLibraryResource("");
        assertEquals("", emptyTitleResource.getTitle());
    }

    @Test
    void testResourceStatusTransitions() {
        // Test all possible status transitions
        resource.setResourceStatus(ResourceStatus.BORROWED);
        assertEquals(ResourceStatus.BORROWED, resource.getResourceStatus());

        resource.setResourceStatus(ResourceStatus.AVAILABLE);
        assertEquals(ResourceStatus.AVAILABLE, resource.getResourceStatus());

        resource.setResourceStatus(ResourceStatus.BORROWED);
        assertEquals(ResourceStatus.BORROWED, resource.getResourceStatus());

    }
    }