package serverClasses.requests;

import utilities.ServerRequest;

import java.io.Serializable;

public class SearchRequest implements Serializable {
    private String searchText;
    private int offset;
    private int rowcount;

    public SearchRequest(String searchText, int offset, int rowcount) {
        this.searchText = searchText;
        this.offset = offset;
        this.rowcount = rowcount;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getRowcount() {
        return rowcount;
    }

    public void setRowcount(int rowcount) {
        this.rowcount = rowcount;
    }

    @Override
    public String toString() {
        return String.valueOf(ServerRequest.SEARCH_REQUEST);
    }

}
