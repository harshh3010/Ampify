package services;

import serverClasses.Main;
import serverClasses.requests.SignUpRequest;
import utilities.Status;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUp {

    public String registerUser(SignUpRequest signUpRequest) {
        String query = "INSERT INTO user_auth(email,password) values(?,?);";
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            preparedStatement.setString(1, signUpRequest.getUserAuth().getEmail());
            preparedStatement.setString(2, signUpRequest.getUserAuth().getPassword());
            preparedStatement.executeUpdate();
            return String.valueOf(Status.SUCCESS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.valueOf(Status.FAILED);
    }

}
