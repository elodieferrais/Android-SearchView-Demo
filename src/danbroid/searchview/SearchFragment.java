package danbroid.searchview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by elodief on 7/1/16.
 */

public class SearchFragment extends Fragment implements SearchListener, MenuItemCompat.OnActionExpandListener {

    private MenuItem searchItem;
    private SearchView searchView;


    @Nullable @Override public View onCreateView(LayoutInflater inflater,
                                                 @Nullable ViewGroup container,
                                                 @Nullable Bundle savedInstanceState) {

        searchView = SearchHelper.initSearchView((AppCompatActivity) getActivity(), this);
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.empty_layout, container, false);
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        searchItem = menu.add(android.R.string.search_go);
        searchItem.setIcon(R.drawable.ic_search_white_36dp);
        MenuItemCompat.setOnActionExpandListener(searchItem, this);
        MenuItemCompat.setActionView(searchItem, searchView);
        MenuItemCompat.setShowAsAction(searchItem, MenuItemCompat.SHOW_AS_ACTION_ALWAYS |
                                                   MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
    }

    @Override
    public void showSearch(boolean visible) {
        if (visible)
            MenuItemCompat.expandActionView(searchItem);
        else
            MenuItemCompat.collapseActionView(searchItem);
    }

    @Override public void onQueryTextSubmit(String query) {
        Toast.makeText(getContext(), "Query submitted: " + query, Toast.LENGTH_SHORT).show();
    }

    @Override public void onQueryTextChange(String newText) {
        Toast.makeText(getContext(), "Query changed: " + newText, Toast.LENGTH_SHORT).show();

    }

    @Override public boolean onMenuItemActionExpand(MenuItem item) {
        Toast.makeText(getContext(), "Search opened", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override public boolean onMenuItemActionCollapse(MenuItem item) {
        Toast.makeText(getContext(), "Search closed", Toast.LENGTH_SHORT).show();
        return true;
    }
}
