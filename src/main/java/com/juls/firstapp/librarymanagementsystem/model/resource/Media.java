package com.juls.firstapp.librarymanagementsystem.model.resource;

import com.juls.firstapp.librarymanagementsystem.model.enums.MediaFormat;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceType;

public class Media extends LibraryResource {

    private MediaFormat format;

    public Media(String title, MediaFormat mediaFormat){
        super(title);
        setResourceType();
        this.format = mediaFormat;
    }

    public Media(String title){
        super(title,ResourceType.MEDIA);
    }

    @Override
    public void setResourceType() {
        super.resourceType = ResourceType.MEDIA;
    }

    public MediaFormat getFormat() {
        return format;
    }

    public void setFormat(MediaFormat format) {
        this.format = format;
    }
}
