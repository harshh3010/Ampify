package serverClasses.requests;

import model.Artist;
import model.Genres;
import model.Language;
import utilities.ServerRequest;

import java.io.Serializable;
import java.util.List;

public class SubmitChoicesRequest implements Serializable {
    private String email;
    private List<Language> selectedLanguages;
    private List<Genres> selectedGenres;
    private List<Artist> selectedArtists;

    public SubmitChoicesRequest() {
    }

    public SubmitChoicesRequest(String email, List<Language> selectedLanguages, List<Genres> selectedGenres, List<Artist> selectedArtists) {
        this.email = email;
        this.selectedLanguages = selectedLanguages;
        this.selectedGenres = selectedGenres;
        this.selectedArtists = selectedArtists;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Language> getSelectedLanguages() {
        return selectedLanguages;
    }

    public void setSelectedLanguages(List<Language> selectedLanguages) {
        this.selectedLanguages = selectedLanguages;
    }

    public List<Genres> getSelectedGenres() {
        return selectedGenres;
    }

    public void setSelectedGenres(List<Genres> selectedGenres) {
        this.selectedGenres = selectedGenres;
    }

    public List<Artist> getSelectedArtists() {
        return selectedArtists;
    }

    public void setSelectedArtists(List<Artist> selectedArtists) {
        this.selectedArtists = selectedArtists;
    }

    @Override
    public String toString() {
        return String.valueOf(ServerRequest.SUBMIT_CHOICES);
    }
}
