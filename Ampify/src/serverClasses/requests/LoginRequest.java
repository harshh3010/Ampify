
package serverClasses.requests;

import model.UserAuth;
import utilities.ServerRequest;

import java.io.Serializable;

public class LoginRequest implements Serializable {

    private UserAuth userAuth;

    public LoginRequest(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    public UserAuth getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    @Override
    public String toString() {
        return String.valueOf(ServerRequest.LOGIN_REQUEST);
    }
}
