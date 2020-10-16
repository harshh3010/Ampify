package model;

import java.io.Serializable;

public class Language implements Serializable {

    private String language;

    public Language() {
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return this.getLanguage();
    }
}
