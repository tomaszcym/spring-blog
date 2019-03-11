package com.tomaszcymerys.springblog.util;

import lombok.Getter;

@Getter
public class JsonMsg {

    private String action;
    private String entity;
    private Long entityId;
    private String message;

    private JsonMsg(String action, Class entity, Long entityId, String message) {
        this.action = action;
        this.entity = entity.getSimpleName();
        this.entityId = entityId;
        this.message = message;
    }

    public static JsonMsg created(Class entity, Long entityId) {
        String message = "Entity " + entity.getSimpleName() + " with ID " + entityId + " has been created.";
        return new JsonMsg("created", entity, entityId, message);
    }

    public static JsonMsg removed(Class entity, Long entityId) {
        String message = "Entity " + entity.getSimpleName() + " with ID " + entityId + " has been removed.";
        return new JsonMsg("removed", entity, entityId, message);
    }

    public static JsonMsg updated(Class entity, Long entityId) {
        String message = "Entity " + entity.getSimpleName() + " with ID " + entityId + " has been updated.";
        return new JsonMsg("updated", entity, entityId, message);
    }
}
