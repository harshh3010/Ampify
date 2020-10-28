/*
UserApi class to allow global access to basic user details
 */

package utilities;

import model.*;

import java.io.File;
import java.util.List;

public class UserApi {

    private String email;
    private List<Language> likedLanguages;
    private List<Genres> likedGenres;
    private List<Artist> likedArtists;
    private List<Song> recentlyPlayed;
    private List<Artist> popularArtists;
    private List<Song> recentlyAdded;
    private List<Song> recommendedMusic;
    private List<Song> topSongs;
    private List<Album> topAlbums;
    private List<Song> mostPlayed;
    private List<Song> previouslyPlayed;
    private List<Playlist> personalPlaylists;
    private List<Playlist> groupPlaylist;
    private File selectedDirectory;

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

    public File getSelectedDirectory() {
        return selectedDirectory;
    }

    public void setSelectedDirectory(File selectedDirectory) {
        this.selectedDirectory = selectedDirectory;
    }

    public List<Playlist> getPersonalPlaylists() {
        return personalPlaylists;
    }

    public void setPersonalPlaylists(List<Playlist> personalPlaylists) {
        this.personalPlaylists = personalPlaylists;
    }

    public List<Playlist> getGroupPlaylist() {
        return groupPlaylist;
    }

    public void setGroupPlaylist(List<Playlist> groupPlaylist) {
        this.groupPlaylist = groupPlaylist;
    }

    public List<Song> getRecentlyPlayed() {
        return recentlyPlayed;
    }

    public void setRecentlyPlayed(List<Song> recentlyPlayed) {
        this.recentlyPlayed = recentlyPlayed;
    }

    public List<Artist> getPopularArtists() {
        return popularArtists;
    }

    public void setPopularArtists(List<Artist> popularArtists) {
        this.popularArtists = popularArtists;
    }

    public List<Song> getRecentlyAdded() {
        return recentlyAdded;
    }

    public void setRecentlyAdded(List<Song> recentlyAdded) {
        this.recentlyAdded = recentlyAdded;
    }

    public List<Song> getRecommendedMusic() {
        return recommendedMusic;
    }

    public void setRecommendedMusic(List<Song> recommendedMusic) {
        this.recommendedMusic = recommendedMusic;
    }

    public List<Song> getTopSongs() {
        return topSongs;
    }

    public void setTopSongs(List<Song> topSongs) {
        this.topSongs = topSongs;
    }

    public List<Album> getTopAlbums() {
        return topAlbums;
    }

    public void setTopAlbums(List<Album> topAlbums) {
        this.topAlbums = topAlbums;
    }

    public List<Song> getMostPlayed() {
        return mostPlayed;
    }

    public void setMostPlayed(List<Song> mostPlayed) {
        this.mostPlayed = mostPlayed;
    }

    public List<Song> getPreviouslyPlayed() {
        return previouslyPlayed;
    }

    public void setPreviouslyPlayed(List<Song> previouslyPlayed) {
        this.previouslyPlayed = previouslyPlayed;
    }

    public static void setInstance(UserApi instance) {
        UserApi.instance = instance;
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
