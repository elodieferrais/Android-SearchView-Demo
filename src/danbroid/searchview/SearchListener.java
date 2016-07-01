package danbroid.searchview;

/**
 * Created by elodief on 7/1/16.
 */

public interface SearchListener {
    void showSearch(boolean visibility);
    void onQueryTextSubmit(String query);
    void onQueryTextChange(String newText);
}
