package com.example.xeva.dto;


public class EventDTO {

    public String username;
    public String eventName;
    public String description;
    public String daysOfWeek;
    public int cyclical;
    public String mode;
    public String webAddress;
    public String tags;
    public String language;
    public int status;
    public String startDate;
    public String endDate;

    @Override
    public String toString() {
        return "EventDTO{" +
                "username='" + username + '\'' +
                ", eventName='" + eventName + '\'' +
                ", description='" + description + '\'' +
                ", daysOfWeek='" + daysOfWeek + '\'' +
                ", cyclical=" + cyclical +
                ", mode='" + mode + '\'' +
                ", webAddress='" + webAddress + '\'' +
                ", tags='" + tags + '\'' +
                ", language='" + language + '\'' +
                ", status=" + status +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    public EventDTO(String username, String eventName, String description, String daysOfWeek, int cyclical, String mode, String webAddress, String tags, String language, int status, String startDate, String endDate) {
        this.username = username;
        this.eventName = eventName;
        this.description = description;
        this.daysOfWeek = daysOfWeek;
        this.cyclical = cyclical;
        this.mode = mode;
        this.webAddress = webAddress;
        this.tags = tags;
        this.language = language;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public int getCyclical() {
        return cyclical;
    }

    public void setCyclical(int cyclical) {
        this.cyclical = cyclical;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
