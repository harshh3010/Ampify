package serverClasses;

import serverClasses.requests.*;
import services.*;
import utilities.*;

import java.io.*;
import java.net.Socket;

public class HandleClientRequest implements Runnable {

    private Socket socket;
    private ObjectInputStream objectInputStream;  //Input Stream of client socket
    private ObjectOutputStream objectOutputStream; //Output Stream of client socket

    public HandleClientRequest(Socket socket) {
        this.socket = socket;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("CLIENT IP ADDRESS: " + socket.getInetAddress().getHostAddress());

        while (true) {
            Object object = null;
            try {
                try {
                    object = objectInputStream.readObject();
                } catch (IOException e) {
                    System.out.println("CLIENT DISCONNECTED\n");
                    break;
                }

                assert object != null;
                String request = object.toString();

                if (request.equals(String.valueOf(ServerRequest.SIGNUP_REQUEST))) {

                    System.out.println("Received signup request from client.");

                    SignUpRequest signUpRequest = (SignUpRequest) object;
                    objectOutputStream.writeObject(AmpifyServices.registerUser(signUpRequest));
                    objectOutputStream.flush();
                }
                if (request.equals(String.valueOf(ServerRequest.LOGIN_REQUEST))) {

                    System.out.println("Received login request from client");

                    LoginRequest log = (LoginRequest) object;
                    objectOutputStream.writeObject(AmpifyServices.userLogin(log));
                    objectOutputStream.flush();
                }

                if (request.equals(String.valueOf(ServerRequest.LANGUAGE_SHOW))) {

                    System.out.println("Received request to fetch languages");

                    LanguageFetchRequest lang = (LanguageFetchRequest) object;
                    objectOutputStream.writeObject(AmpifyServices.showAllLanguages(lang));
                    objectOutputStream.flush();
                }

                if (request.equals(String.valueOf(ServerRequest.GENRES_SHOW))) {

                    System.out.println("Received request to fetch genres");

                    GenresFetchRequest obj = (GenresFetchRequest) object;
                    objectOutputStream.writeObject(AmpifyServices.showAllGenres(obj));
                    objectOutputStream.flush();
                }
                if (request.equals(String.valueOf(ServerRequest.ARTIST_SHOW))) {
                    ArtistFetchRequest art = (ArtistFetchRequest) object;

                    if (art.getType().equals(String.valueOf(ArtistsAlbumFetchType.ALL))) {

                        System.out.println("Received request to fetch all artists");

                        objectOutputStream.writeObject(AmpifyServices.showAllArtists(art));
                        objectOutputStream.flush();
                    } else if (art.getType().equals(String.valueOf(ArtistsAlbumFetchType.TOP))) {

                        System.out.println("Received request to fetch top artists");

                        objectOutputStream.writeObject(AmpifyServices.showTopArtists(art));
                        objectOutputStream.flush();
                    }

                }

                if (request.equals(String.valueOf(ServerRequest.ALBUM_SHOW))) {
                    AlbumFetchRequest album = (AlbumFetchRequest) object;

                    if (album.getType().equals(String.valueOf(ArtistsAlbumFetchType.TOP))) {

                        System.out.println("Received request to fetch top albums");

                        objectOutputStream.writeObject(AmpifyServices.showTopAlbums(album));
                        objectOutputStream.flush();
                    }

                }


                if (request.equals(String.valueOf(ServerRequest.SUBMIT_CHOICES))) {

                    System.out.println("Received request to submit choices");

                    SubmitChoicesRequest submitChoicesRequest = (SubmitChoicesRequest) object;
                    objectOutputStream.writeObject(AmpifyServices.saveChoices(submitChoicesRequest));
                    objectOutputStream.flush();
                }

                if (request.equals(String.valueOf(ServerRequest.GET_CHOICES))) {

                    System.out.println("Received request to fetch choices");

                    ChoicesFetchRequest choicesFetchRequest = (ChoicesFetchRequest) object;
                    objectOutputStream.writeObject(AmpifyServices.getUserChoices(choicesFetchRequest));
                    objectOutputStream.flush();
                }

                //if request is to fetch songs!!
                if (request.equals(String.valueOf(ServerRequest.SONG_SHOW))) {
                    SongFetchRequest songType = (SongFetchRequest) object;
                    //if request is to display top songs
                    if (songType.getType().equals(String.valueOf(SongFetchType.TOP))) {

                        System.out.println("Received request to fetch top songs");

                        objectOutputStream.writeObject(AmpifyServices.showTopSongs(songType));
                        objectOutputStream.flush();
                    }
                    //if request is to display songs of particular artist
                    else if (songType.getType().equals(String.valueOf(SongFetchType.SONGS_OF_PARTICULAR_ARTIST))) {

                        System.out.println("Received request to fetch songs of particular artist");

                        objectOutputStream.writeObject(AmpifyServices.showSongsOfParticularArtist(songType));
                        objectOutputStream.flush();
                    }
                    //if request is to display songs of particular album
                    else if (songType.getType().equals(String.valueOf(SongFetchType.SONGS_OF_PARTICULAR_ALBUM))) {

                        System.out.println("Received request to fetch songs of particular album");

                        objectOutputStream.writeObject(AmpifyServices.showSongsOfParticularAlbum(songType));
                        objectOutputStream.flush();
                    }
                    //if request is to display songs of user's choice!!!
                    else if (songType.getType().equals(String.valueOf(SongFetchType.SONGS_OF_USER_CHOICE))) {

                        System.out.println("Received request to fetch songs of user choice");

                        objectOutputStream.writeObject(AmpifyServices.showSongsOfUserChoice(songType));
                        objectOutputStream.flush();
                    }
                    //if request is to display recently added songs to the server!!
                    else if (songType.getType().equals(String.valueOf(SongFetchType.RECENT_ADDED_SONGS))) {

                        System.out.println("Received request to fetch recently added songs");

                        objectOutputStream.writeObject(AmpifyServices.showRecentAddedSongs(songType));
                        objectOutputStream.flush();
                    }
                    //if request is to display last played song of user!!
                    else if (songType.getType().equals(String.valueOf(SongFetchType.LAST_PLAYED_SONG))) {

                        System.out.println("Received request to fetch last played song");

                        objectOutputStream.writeObject(AmpifyServices.showLastPlayedSong(songType));
                        objectOutputStream.flush();
                    }
                    //if request is to display recently played song of user!!
                    else if (songType.getType().equals(String.valueOf(SongFetchType.RECENT_PLAYED_SONGS_BY_USER))) {

                        System.out.println("Received request to fetch recently played songs");

                        objectOutputStream.writeObject(AmpifyServices.showRecentlyPlayedSong(songType));
                        objectOutputStream.flush();
                    }
                    //if request is to display most played song of user!!
                    else if (songType.getType().equals(String.valueOf(SongFetchType.MOST_PLAYED_SONGS_BY_USER))) {

                        System.out.println("Received request to fetch most played songs");

                        objectOutputStream.writeObject(AmpifyServices.showMostPlayedSongByUser(songType));
                        objectOutputStream.flush();
                    }
                    //if request is to display trending song!!
                    else if (songType.getType().equals(String.valueOf(SongFetchType.TRENDING_SONGS))) {

                        System.out.println("Received request to fetch trending songs");

                        objectOutputStream.writeObject(AmpifyServices.showTrendingSongs(songType));
                        objectOutputStream.flush();
                    }
                    //if request is to display previously played at same time song!!
                    else if (songType.getType().equals(String.valueOf(SongFetchType.PREVIOUSLY_PLAYED_BY_USER))) {

                        System.out.println("Received request to fetch previously played songs");

                        objectOutputStream.writeObject(AmpifyServices.showPreviouslyPlayedSongs(songType));
                    }
                    //if request is to display favourite played song of user!!
                    else if (songType.getType().equals(String.valueOf(SongFetchType.FAVOURITE_SONGS))) {

                        System.out.println("Received request to fetch favourite songs");

                        objectOutputStream.writeObject(AmpifyServices.showFavouriteSong(songType));
                        objectOutputStream.flush();
                    }
                }

                if (request.equals((String.valueOf(ServerRequest.UPDATE_HISTORY)))) {

                    System.out.println("Received request to update song history");

                    AddToHistoryRequest addToHistoryRequest = (AddToHistoryRequest) object;
                    objectOutputStream.writeObject(AmpifyServices.playSongAddHistory(addToHistoryRequest));
                    objectOutputStream.flush();
                }

                if (request.equals((String.valueOf(ServerRequest.FETCH_USER_HISTORY)))) {

                    System.out.println("Received request to fetch user history");

                    FetchUserHistoryRequest fetchUserHistoryRequest = (FetchUserHistoryRequest) object;
                    objectOutputStream.writeObject(AmpifyServices.showUserHistory(fetchUserHistoryRequest));
                    objectOutputStream.flush();
                }

                //if request is for playlist operations!!
                if (request.equals(String.valueOf(ServerRequest.PLAYLIST_REQUEST))) {
                    PlaylistRequest playlistRequest = (PlaylistRequest) object;
                    //if request is to create playlist
                    if (playlistRequest.getType().equals(String.valueOf(PlaylistType.CREATE_PLAYLIST))) {

                        System.out.println("Received request to create playlist");

                        objectOutputStream.writeObject(AmpifyServices.creatingPlaylist(playlistRequest));
                        objectOutputStream.flush();
                    }
                    //if request is to fetch mine playlists
                    else if (playlistRequest.getType().equals(String.valueOf(PlaylistType.FETCH_USER_PLAYLISTS))) {

                        System.out.println("Received request to fetch user's playlist");

                        objectOutputStream.writeObject(AmpifyServices.getUserPlaylist(playlistRequest));
                        objectOutputStream.flush();

                    }
                    //if request is to add song to a playlist
                    else if (playlistRequest.getType().equals(String.valueOf(PlaylistType.ADD_SONG_TO_A_PLAYLIST))) {

                        System.out.println("Received request to add a song to playlist");

                        objectOutputStream.writeObject(AmpifyServices.addingSongToPlaylist(playlistRequest));
                        objectOutputStream.flush();
                    }
                    //if request is to fetch songs of a particular playlist
                    else if (playlistRequest.getType().equals(String.valueOf(PlaylistType.FETCH_SONGS_OF_A_PLAYLIST))) {

                        System.out.println("Received request to fetch songs of a playlist");

                        objectOutputStream.writeObject(AmpifyServices.getSongsOfPlaylist(playlistRequest));
                        objectOutputStream.flush();
                    }
                    //if request is to delete a particular playlist
                    else if (playlistRequest.getType().equals(String.valueOf(PlaylistType.DELETE_PLAYLIST))) {

                        System.out.println("Received request to delete playlist");

                        objectOutputStream.writeObject(AmpifyServices.deletePlaylist(playlistRequest));
                        objectOutputStream.flush();
                    }
                }

                //if request is for notification operations!!
                if (request.equals(String.valueOf(ServerRequest.NOTIFICATION_REQUEST))) {
                    NotificationRequest notificationRequest = (NotificationRequest) object;
                    //if request is to send notification
                    if (notificationRequest.getType().equals(String.valueOf(NotificationType.SEND))) {

                        System.out.println("Received request to send an invite to playlist");

                        objectOutputStream.writeObject(AmpifyServices.sendingNotification(notificationRequest));
                        objectOutputStream.flush();
                    }
                    //if request is to get all my notifications
                    else if (notificationRequest.getType().equals(String.valueOf(NotificationType.GET_NOTIFICATIONS))) {

                        System.out.println("Received request to get invites");

                        objectOutputStream.writeObject(AmpifyServices.gettingNotification(notificationRequest));
                        objectOutputStream.flush();
                    }
                    //if request is to confirm notification i.e. add as member and later on will also delete that notification
                    else if (notificationRequest.getType().equals(String.valueOf(NotificationType.CONFIRM_NOTIFICATION))) {

                        System.out.println("Received request to accept invite");

                        objectOutputStream.writeObject(AmpifyServices.confirmNotification(notificationRequest));
                        objectOutputStream.flush();
                    }
                    //if request is to delete notification
                    else if (notificationRequest.getType().equals(String.valueOf(NotificationType.DELETE_NOTIFICATION))) {

                        System.out.println("Received request to decline invite");

                        objectOutputStream.writeObject(AmpifyServices.deleteNotification(notificationRequest));
                        objectOutputStream.flush();
                    }
                }
                if (request.equals((String.valueOf(ServerRequest.SEARCH_REQUEST)))) {

                    System.out.println("Received request to search for songs");

                    SearchRequest searchRequest = (SearchRequest) object;
                    objectOutputStream.writeObject(AmpifyServices.showSearchResults(searchRequest));
                    objectOutputStream.flush();
                }
                if (request.equals((String.valueOf(ServerRequest.ADD_TO_FAVOURITE)))) {

                    System.out.println("Received request to add song to favourites");

                    AddToFavouriteRequest addToFavouriteRequest = (AddToFavouriteRequest) object;
                    objectOutputStream.writeObject(AmpifyServices.addSongToFavoutite(addToFavouriteRequest));
                    objectOutputStream.flush();
                }

            } catch (StreamCorruptedException e) {
                try {

                    System.out.println("AN ERROR OCCURRED... RESETTING THE STREAM");

                    objectInputStream = new ObjectInputStream(socket.getInputStream());
                    objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                } catch (IOException e1) {
                    System.out.println("UNABLE TO RESET THE STREAM");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
