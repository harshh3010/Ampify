package services;

import model.Genres;
import model.Language;
import serverClasses.Main;
import serverClasses.requests.GenresFetchRequest;
import serverClasses.requests.LanguageFetchRequest;
import utilities.DatabaseConstants;

import java.awt.image.ImageObserver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import  model.Language;
public class GenresShow {

    public List<Genres> genresshow(GenresFetchRequest genresRequest){
        String query="Select * from Genres;";
        List<Genres> genresList=new ArrayList<>();
        try {
            PreparedStatement preparedStatement=Main.connection.prepareStatement(query);

            ResultSet resultSet=preparedStatement.executeQuery();
            Genres genresSet;
            while (resultSet.next()){
                genresSet=new Genres();
                genresSet.setGenres(resultSet.getString(1));
                genresList.add(genresSet);
            }
            return genresList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genresList;
    }

}