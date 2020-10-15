package services;

import model.Language;
import serverClasses.Main;
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
public class LanguageShow {

    public List<Language> show(LanguageFetchRequest languageRequest){
        String query="Select * from Language;";
        List<Language> languageList=new ArrayList<>();
        try {
            PreparedStatement preparedStatement=Main.connection.prepareStatement(query);

            ResultSet resultSet=preparedStatement.executeQuery();
            Language langSet;
            while (resultSet.next()){
                langSet=new Language();
                langSet.setLanguage(resultSet.getString(1));
                languageList.add(langSet);
            }
            return languageList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return languageList;
    }

}
