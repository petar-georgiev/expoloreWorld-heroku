package com.example.explore.model.service;

public class CommentServiceModel {

    private Long routeId;
    private String message;
    private String creator;

    public String getMessage() {
        return message;
    }

    public Long getRouteId() {
        return routeId;
    }

    public CommentServiceModel setRouteId(Long routeId) {
        this.routeId = routeId;
        return this;
    }

    public CommentServiceModel setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public CommentServiceModel setCreator(String creator) {
        this.creator = creator;
        return this;
    }
}
