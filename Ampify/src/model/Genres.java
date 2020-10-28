package model;
import java.io.Serializable;
import java.io.Serializable;

public class Genres implements Serializable {
    private String genres;

    public Genres()
    {

    }
    public String getGenres(){

        return  genres;
    }
    public void setGenres(String genres){

        this.genres=genres;
    }
    @Override
    public String toString() {

        return this.getGenres();
    }
}