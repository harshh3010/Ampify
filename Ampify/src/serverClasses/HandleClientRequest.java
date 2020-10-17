package serverClasses;

import serverClasses.requests.*;
import services.*;
import utilities.ArtistsFetchType;
import utilities.ServerRequest;

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
        System.out.println(socket.getInetAddress().getHostAddress());

        while (true) {
            Object object = null;
            try {
                try {
                    object = ois.readObject();
                } catch (EOFException e) {
                    System.out.println("Client disconnected");
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
                    System.out.print("hii");
                    LanguageFetchRequest lang = (LanguageFetchRequest) object;
                    oos.writeObject(AmpifyServices.showAllLanguages(lang));
                    oos.flush();
                }

                if (request.equals(String.valueOf(ServerRequest.GENRES_SHOW))) {
                    System.out.print("hii");
                    GenresFetchRequest obj = (GenresFetchRequest) object;
                    oos.writeObject(AmpifyServices.showAllGenres(obj));
                    oos.flush();
                }
                if (request.equals(String.valueOf(ServerRequest.ARTIST_SHOW))) {
                    ArtistFetchRequest art = (ArtistFetchRequest) object;

                    if(art.getType().equals(String.valueOf(ArtistsFetchType.ALL))){
                        oos.writeObject(AmpifyServices.showAllArtists(art));
                        oos.flush();
                    }else if(art.getType().equals(String.valueOf(ArtistsFetchType.TOP))){
                        oos.writeObject(AmpifyServices.showTopArtists(art));
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
