package ua.helpdesk.exception;

public enum EntityErrorType {
    ENTITY_NOT_FOUND("Entity not found"),
    ENTITY_NOT_FOUND_BY_ID("Entity not found by id: %s"),
    ENTITY_NOT_SAVED("Entity not saved: %s"),
    ENTITY_NOT_UPDATED("Entity not updated: %s");

    private String description;

    EntityErrorType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
