package com.tianmaying.model;

public class Project {
    
    private String name;
    private String description;
    private String url;
    private String logo;

    public Project(String name, String description, String url, String logo) {
       this.name = name;
       this.description = description;
       this.url = url;
       this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
    
    
}