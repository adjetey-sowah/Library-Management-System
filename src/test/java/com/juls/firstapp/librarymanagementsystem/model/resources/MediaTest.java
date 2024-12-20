package com.juls.firstapp.librarymanagementsystem.model.resources;

import com.juls.firstapp.librarymanagementsystem.model.enums.MediaFormat;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceStatus;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceType;
import com.juls.firstapp.librarymanagementsystem.model.resource.Media;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MediaTest {
    @InjectMocks
    private Media media;

    private final String TEST_TITLE = "Test Media";
    private final MediaFormat TEST_FORMAT = MediaFormat.DVD;

    @Mock
    private ResourceType resourceType;

    @Mock
    private ResourceStatus resourceStatus;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        media = new Media(TEST_TITLE, TEST_FORMAT);
    }

    @Test
    void testMediaConstructorWithAllParameters() {
        assertNotNull(media);
        assertEquals(TEST_TITLE, media.getTitle());
        assertEquals(TEST_FORMAT, media.getFormat());
        assertEquals(ResourceType.MEDIA, media.getResourceType());
        assertEquals(ResourceStatus.AVAILABLE, media.getResourceStatus());
    }

    @Test
    void testDefaultConstructor() {
        Media defaultMedia = new Media();
        assertNotNull(defaultMedia);
        assertEquals("", defaultMedia.getTitle());
        assertEquals(ResourceType.MEDIA, defaultMedia.getResourceType());
    }

    @Test
    void testConstructorWithTitle() {
        Media titleMedia = new Media(TEST_TITLE);
        assertEquals(TEST_TITLE, titleMedia.getTitle());
        assertEquals(ResourceType.MEDIA, titleMedia.getResourceType());
    }

    @Test
    void testSettersAndGetters() {
        Long resourceId = 1L;
        media.setResourceId(resourceId);
        media.setTitle("New Title");
        media.setFormat(MediaFormat.AUDIO);
        media.setResourceStatus(ResourceStatus.BORROWED);

        assertEquals(resourceId, media.getResourceId());
        assertEquals("New Title", media.getTitle());
        assertEquals(MediaFormat.AUDIO, media.getFormat());
        assertEquals(ResourceStatus.BORROWED, media.getResourceStatus());
    }
}
