package serverClasses;

import serverClasses.requests.*;
import services.*;
import utilities.*;

import java.io.*;
import java.net.Socket;

public class HandleClientRequest implements Runnable {

    private Socket socket;
    private ObjectInputStream ois;  //Input Stream of client socket
    private ObjectOutputStream oos; //Output Stream of client socket

    public HandleClientRequest(Socket socket) {
        this.socket = socket;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
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
                    object = ois.readObject();
                } catch (EOFException e) {
                    System.out.println("CLIENT DISCONNECTED");
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                assert object != null;
                String request = object.toString();

                if (request.equals(String.valueOf(ServerRequest.SIGNUP_REQUEST))) {
                    SignUpRequest signUpRequest = (SignUpRequest) object;
                    oos.writeObject(AmpifyServices.registerUser(signUpRequest));
                    oos.flush();
                }
                if (request.equals(String.valueOf(ServerRequest.LOGIN_REQUEST))) {
                    LoginRequest log = (LoginRequest) object;
                    oos.writeObject(AmpifyServices.userLogin(log));
                    oos.flush();
                }

                if (request.equals(String.valueOf(ServerRequest.LANGUAGE_SHOW))) {
                    LanguageFetchRequest lang = (LanguageFetchRequest) object;
                    oos.writeObject(AmpifyServices.showAllLanguages(lang));
                    oos.flush();
                }

                if (request.equals(String.valueOf(ServerRequest.GENRES_SHOW))) {
                    GenresFetchRequest obj = (GenresFetchRequest) object;
                    oos.writeObject(AmpifyServices.showAllGenres(obj));
                    oos.flush();
                }
                if (request.equals(String.valueOf(ServerRequest.ARTIST_SHOW))) {
                    ArtistFetchRequest art = (ArtistFetchRequest) object;

                    if (art.getType().equals(String.valueOf(ArtistsAlbumFetchType.ALL))) {
                        oos.writeObject(AmpifyServices.showAllArtists(art));
                        oos.flush();
                    } else if (art.getType().equals(String.valueOf(ArtistsAlbumFetchType.TOP))) {
                        oos.writeObject(AmpifyServices.showTopArtists(art));
                        oos.flush();
                    }

                }

                if (request.equals(String.valueOf(ServerRequest.ALBUM_SHOW))) {
                    AlbumFetchRequest album = (AlbumFetchRequest) object;

                    if (album.getType().equals(String.valueOf(ArtistsAlbumFetchType.TOP))) {
                        oos.writeObject(AmpifyServices.showTopAlbums(album));
                        oos.flush();
                    }

                }


                if (request.equals(String.valueOf(ServerRequest.SUBMIT_CHOICES))) {
                    SubmitChoicesRequest submitChoicesRequest = (SubmitChoicesRequest) object;
                    oos.writeObject(AmpifyServices.saveChoices(submitChoicesRequest));
                    oos.flush();
                }

                if (request.equals(String.valueOf(ServerRequest.GET_CHOICES))) {
                    ChoicesFetchRequest choicesFetchRequest = (ChoicesFetchRequest) object;
                    oos.writeObject(AmpifyServices.getUserChoices(choicesFetchRequest));
                    oos.flush();
                }

                //if request is to fetch songs!!
                if (request.equals(String.valueOf(ServerRequest.SONG_SHOW))) {
                    SongFetchRequest songType = (SongFetchRequest) object;
                    //if request is to display top songs
                    if (songType.getType().equals(String.valueOf(SongFetchType.TOP))) {
                        oos.writeObject(AmpifyServices.showTopSongs(songType));
                        oos.flush();
                    }//if request is to display songs of particular artist
                    else if (songType.getType().equals(String.valueOf(SongFetchType.SONGS_OF_PARTICULAR_ARTIST))) {
                        oos.writeObject(AmpifyServices.showSongsOfParticularArtist(songType));
                        oos.flush();
                    }//if request is to display songs of particular album
                    else if (songType.getType().equals(String.valueOf(SongFetchType.SONGS_OF_PARTICULAR_ALBUM))) {
                        oos.writeObject(AmpifyServices.showSongsOfParticularAlbum(songType));
                        oos.flush();
                    }//if request is to display songs of user's choice!!!
                    else if (songType.getType().equals(String.valueOf(SongFetchType.SONGS_OF_USER_CHOICE))) {
                        oos.writeObject(AmpifyServices.showSongsOfUserChoice(songType));
                        oos.flush();
                    }//if request is to display recentlu added sngs to the server!!
                    else if (songType.getType().equals(String.valueOf(SongFetchType.RECENT_ADDED_SONGS))) {
                        oos.writeObject(AmpifyServices.showRecentAddedSongs(songType));
                        oos.flush();
                    }//if request is to display last played song of user!!
                    else if (songType.getType().equals(String.valueOf(SongFetchType.LAST_PLAYED_SONG))) {
                        System.out.print("hi");
                        oos.writeObject(AmpifyServices.showLastPlayedSong(songType));
                        oos.flush();
                    }
                    //if request is to display recently played song of user!!
                    else if (songType.getType().equals(String.valueOf(SongFetchType.RECENT_PLAYED_SONGS_BY_USER))) {

                        oos.writeObject(AmpifyServices.showRecentlyPlayedSong(songType));
                        oos.flush();
                    }//if request is to display most played song of user!!
                    else if (songType.getType().equals(String.valueOf(SongFetchType.MOST_PLAYED_SONGS_BY_USER))) {
                        System.out.print("LOL");
                        oos.writeObject(AmpifyServices.showMostPlayedSongByUser(songType));
                        oos.flush();
                    }

                }

                if (request.equals((String.valueOf(ServerRequest.UPDATE_HISTORY)))) {
                    AddToHistoryRequest addToHistoryRequest = (AddToHistoryRequest) object;
                    oos.writeObject(AmpifyServices.playSongAddHistory(addToHistoryRequest));
                    oos.flush();
                }

                if (request.equals((String.valueOf(ServerRequest.FETCH_USER_HISTORY)))) {
                    FetchUserHistoryRequest fetchUserHistoryRequest = (FetchUserHistoryRequest) object;
                    oos.writeObject(AmpifyServices.showUserHistory(fetchUserHistoryRequest));
                    oos.flush();
                }

                //if request is for playlist operations!!
                if (request.equals(String.valueOf(ServerRequest.PLAYLIST_REQUEST))) {
                    PlaylistRequest playlistRequest = (PlaylistRequest) object;
                    //if request is to create playlist
                    if (playlistRequest.getType().equals(String.valueOf(PlaylistType.CREATE_PLAYLIST))) {
                        oos.writeObject(AmpifyServices.creatingPlaylist(playlistRequest));
                        oos.flush();
                    }//if request is to fetch mine playlists
                    else if (playlistRequest.getType().equals(String.valueOf(PlaylistType.FETCH_USER_PLAYLISTS))) {
                        oos.writeObject(AmpifyServices.getUserPlaylist(playlistRequest));
                        oos.flush();

                    }//if request is to add song to a playlist
                    else if (playlistRequest.getType().equals(String.valueOf(PlaylistType.ADD_SONG_TO_A_PLAYLIST))) {
                        oos.writeObject(AmpifyServices.addingSongToPlaylist(playlistRequest));
                        oos.flush();
                    }//if request is to fetch songs of a particular playlist
                    else if (playlistRequest.getType().equals(String.valueOf(PlaylistType.FETCH_SONGS_OF_A_PLAYLIST))) {
                        oos.writeObject(AmpifyServices.getSongsOfPlaylist(playlistRequest));
                        oos.flush();
                    }//if request is to delete a particular playlist
                    else if (playlistRequest.getType().equals(String.valueOf(PlaylistType.DELETE_PLAYLIST))) {
                        oos.writeObject(AmpifyServices.deletePlaylist(playlistRequest));
                        oos.flush();
                    }
                }

                //if request is for notification operations!!
                if (request.equals(String.valueOf(ServerRequest.NOTIFICATION_REQUEST))) {
                    NotificationRequest notificationRequest = (NotificationRequest) object;
                    //if request is to send notification
                    if (notificationRequest.getType().equals(String.valueOf(NotificationType.SEND))) {
                        oos.writeObject(AmpifyServices.sendingNotification(notificationRequest));
                        oos.flush();
                    }//if request is to get all my notifications
                    else if (notificationRequest.getType().equals(String.valueOf(NotificationType.GET_NOTIFICATIONS))) {
                        oos.writeObject(AmpifyServices.gettingNotification(notificationRequest));
                        oos.flush();
                    }//if request is to confirm notification i.e. add as member and later on will also delete that notification
                    else if (notificationRequest.getType().equals(String.valueOf(NotificationType.CONFIRM_NOTIFICATION))) {
                        oos.writeObject(AmpifyServices.confirmNotification(notificationRequest));
                        oos.flush();
                    }//if request is to delete notification
                    else if (notificationRequest.getType().equals(String.valueOf(NotificationType.DELETE_NOTIFICATION))) {
                        oos.writeObject(AmpifyServices.deleteNotification(notificationRequest));
                        oos.flush();
                    }
                }


            } catch (StreamCorruptedException e) {
                try {
                    ois = new ObjectInputStream(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
