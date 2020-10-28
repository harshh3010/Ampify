package serverClasses.requests;

import utilities.ServerRequest;

import java.io.Serializable;

public class GenresFetchRequest implements Serializable{

    private String genres;
    public GenresFetchRequest()
    {

    }
    public String getGenres(){

        return  genres;
    }
    public void setLanguage(String language){

        this.genres=genres;
    }

    @Override
    public String toString() {

        return String.valueOf(ServerRequest.GENRES_SHOW);
    }
}

