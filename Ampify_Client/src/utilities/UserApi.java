/*
UserApi class to allow global access to basic user details
 */

package utilities;

import model.Artist;
import model.Genres;
import model.Language;

import java.util.List;

public class UserApi {

    private String email;
    private List<Language> likedLanguages;
    private List<Genres> likedGenres;
    private List<Artist> likedArtists;

    private static UserApi instance = null;

    // private constructor restricted to this class itself
    private UserApi() {
    }

    // static method to create instance of Singleton class
    public static UserApi getInstance() {
        if (instance == null)
            instance = new UserApi();
        return instance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Language> getLikedLanguages() {
        return likedLanguages;
    }

    public void setLikedLanguages(List<Language> likedLanguages) {
        this.likedLanguages = likedLanguages;
    }

    public List<Genres> getLikedGenres() {
        return likedGenres;
    }

    public void setLikedGenres(List<Genres> likedGenres) {
        this.likedGenres = likedGenres;
    }

    public List<Artist> getLikedArtists() {
        return likedArtists;
    }

    public void setLikedArtists(List<Artist> likedArtists) {
        this.likedArtists = likedArtists;
    }
}
