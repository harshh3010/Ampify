package services;

import model.User;
import serverClasses.Main;
import serverClasses.requests.LoginRequest;

import java.sql.ResultSet;

import utilities.DatabaseConstants;
import utilities.Status;
import utilities.LoginStatus;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Login {
    public User userLogin(LoginRequest LoginRequest) {
        String query = "Select * from " + DatabaseConstants.AUTH_TABLE_NAME + " where " + DatabaseConstants.AUTH_TABLE_COL_EMAIL + "=?;";
        User user = new User();
        try {
            PreparedStatement preparedStatement = Main.connection.prepareStatement(query);
            preparedStatement.setString(1, LoginRequest.getUserAuth().getEmail());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString(2).equals(LoginRequest.getUserAuth().getPassword())) {
                    String query1 = "Select * from " + DatabaseConstants.AUTH_TABLE_NAME + " where " + DatabaseConstants.AUTH_TABLE_COL_EMAIL + "=?;";
                    PreparedStatement preparedStatement1 = Main.connection.prepareStatement(query1);
                    preparedStatement1.setString(1, resultSet.getString(1));
                    ResultSet resultSet1 = preparedStatement1.executeQuery();
                    while (resultSet1.next()) {

                        user.setUserLoginStatus((String.valueOf(LoginStatus.SUCCESS)));
                        System.out.println("Logged in successfuly");
                        return user;
                    }
                }
                user.setUserLoginStatus(String.valueOf(LoginStatus.WRONG_CREDENTIALS));
                System.out.println("Wrong credentials");
                return user;
            }
            user.setUserLoginStatus(String.valueOf(LoginStatus.NO_SUCH_USER));
            System.out.println("No such user");
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        user.setUserLoginStatus(String.valueOf(LoginStatus.SERVER_SIDE_ERROR));
        return user;
    }
}
