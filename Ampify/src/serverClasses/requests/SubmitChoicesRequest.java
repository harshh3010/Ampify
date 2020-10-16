package serverClasses.requests;

import utilities.ServerRequest;

import java.io.Serializable;
import java.util.List;

public class SubmitChoicesRequest implements Serializable {
    private List<String> selectedLanguages;
    private List<String> selectedGenres;
    private List<String> selectedArtists;

    public SubmitChoicesRequest() {
    }

    public SubmitChoicesRequest(List<String> selectedLanguages, List<String> selectedGenres, List<String> selectedArtists) {
        this.selectedLanguages = selectedLanguages;
        this.selectedGenres = selectedGenres;
        this.selectedArtists = selectedArtists;
    }

    public List<String> getSelectedLanguages() {
        return selectedLanguages;
    }

    public void setSelectedLanguages(List<String> selectedLanguages) {
        this.selectedLanguages = selectedLanguages;
    }

    public List<String> getSelectedGenres() {
        return selectedGenres;
    }

    public void setSelectedGenres(List<String> selectedGenres) {
        this.selectedGenres = selectedGenres;
    }

    public List<String> getSelectedArtists() {
        return selectedArtists;
    }

    public void setSelectedArtists(List<String> selectedArtists) {
        this.selectedArtists = selectedArtists;
    }

    @Override
    public String toString() {
        return String.valueOf(ServerRequest.SUBMIT_CHOICES);
    }
}
