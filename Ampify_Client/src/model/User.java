package model;

import java.io.Serializable;

public class User extends UserAuth implements Serializable {

    private String userLoginStatus;

    public User() {

        super();
    }

    public String getUserLoginStatus() {

        return userLoginStatus;
    }

    public void setUserLoginStatus(String userLoginStatus) {

        this.userLoginStatus = userLoginStatus;
    }

    @Override
    public String toString() {

        return this.getEmail();
    }
}

