package serverClasses.requests;

import model.Artist;
import model.Genres;
import model.Language;
import utilities.ServerRequest;

import java.io.Serializable;
import java.util.List;

public class ChoicesFetchRequest implements Serializable {

    private List<Language> languageList;
    private List<Genres> genresList;
    private List<Artist> artistList;
    private String email;

    public ChoicesFetchRequest() {
    }
    public ChoicesFetchRequest(String email) {

        this.email = email;
    }
    public ChoicesFetchRequest(List<Language> languageList, List<Genres> genresList, List<Artist> artistList) {
        this.languageList = languageList;
        this.genresList = genresList;
        this.artistList = artistList;
    }
    public String getEmail() {

        return email;
    }
    public void setEmail(String email) {

        this.email = email;
    }
    public List<Language> getLanguageList() {

        return languageList;
    }
    public void setLanguageList(List<Language> languageList) {

        this.languageList = languageList;
    }
    public List<Genres> getGenresList() {

        return genresList;
    }
    public void setGenresList(List<Genres> genresList) {

        this.genresList = genresList;
    }
    public List<Artist> getArtistList() {

        return artistList;
    }
    public void setArtistList(List<Artist> artistList) {

        this.artistList = artistList;
    }
    @Override
    public String toString() {

        return String.valueOf(ServerRequest.GET_CHOICES);
    }
}
