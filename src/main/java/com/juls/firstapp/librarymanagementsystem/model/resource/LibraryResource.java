package com.juls.firstapp.librarymanagementsystem.model.resource;

import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceStatus;
import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceType;

public abstract class LibraryResource {
    protected Long resourceId;
    protected String title;
    protected ResourceStatus status;
    protected ResourceType resourceType;

    public LibraryResource(String title, ResourceType resourceType){
        this.title = title;
        this.resourceType = resourceType;
        this.status = ResourceStatus.AVAILABLE;
    }

    public LibraryResource(String title){

        this.title = title;
        this.status = ResourceStatus.AVAILABLE;
    }


    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getResourceId(){
        return  resourceId;
    }



    public void setResourceStatus(ResourceStatus status){
        this.status = status;
    }

    public ResourceStatus getResourceStatus(){
        return this.status;
    }

    public abstract void setResourceType();

    public ResourceType getResourceType(){
        return resourceType;
    }

    @Override
    public String toString() {
        return "LibraryResource{" +
                "resourceId=" + resourceId +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", resourceType=" + resourceType +
                '}';
    }
}
