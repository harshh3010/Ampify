package serverClasses.requests;

import utilities.ServerRequest;

import java.io.Serializable;

public class FetchUserHistoryRequest implements Serializable {
    private String email;
    private int rowcount;
    private int offset;

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public int getRowcount() {

        return rowcount;
    }

    public void setRowcount(int rowcount) {

        this.rowcount = rowcount;
    }

    public int getOffset() {

        return offset;
    }

    public void setOffset(int offset) {

        this.offset = offset;
    }

    public FetchUserHistoryRequest(String email, int offset, int rowcount) {
        this.email = email;
        this.rowcount = rowcount;
        this.offset = offset;
    }

    @Override
    public String toString() {

        return String.valueOf(ServerRequest.FETCH_USER_HISTORY);
    }

}
