package ua.helpdesk.model;

import java.io.Serializable;

public enum UserProfileType implements Serializable {
    USER("USER"),
    ADMIN("ADMIN"),
    SUPPORT("SUPPORT");

    String userType;

    private UserProfileType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

}
