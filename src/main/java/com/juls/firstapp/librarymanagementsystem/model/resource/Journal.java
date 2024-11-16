package com.juls.firstapp.librarymanagementsystem.model.resource;

import com.juls.firstapp.librarymanagementsystem.model.enums.ResourceType;

public class Journal extends LibraryResource{
    private String issueNumber;
    private String frequency;

    public Journal(String title, String issueNumber, String frequency){
        super(title,ResourceType.JOURNAL);
        this.issueNumber = issueNumber;
        this.frequency = frequency;
    }

    public Journal(String title) {
        super(title, ResourceType.JOURNAL);
    }

    public Journal(){
        super("",ResourceType.JOURNAL);
    }

    @Override
    public void setResourceType() {
        super.resourceType = ResourceType.JOURNAL;
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "Journal{" +
                "issueNumber='" + issueNumber + '\'' +
                ", frequency='" + frequency + '\'' +
                ", resourceId=" + resourceId +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", resourceType=" + resourceType +
                '}';
    }
}
