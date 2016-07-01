package danbroid.searchview;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by elodief on 7/1/16.
 */

public class SearchHelper {

    public static SearchView initSearchView(AppCompatActivity appCompatActivity, final SearchListener searchListener) {
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) appCompatActivity.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = new SearchView(appCompatActivity.getSupportActionBar().getThemedContext());
        searchView.setSearchableInfo(searchManager.getSearchableInfo(appCompatActivity.getComponentName()));
        searchView.setSubmitButtonEnabled(false);
        searchView.setIconifiedByDefault(true);
        searchView.setMaxWidth(1000);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                searchListener.onQueryTextSubmit(query);
                return false;
            }

            @Override public boolean onQueryTextChange(String newText) {
                searchListener.onQueryTextChange(newText);
                return false;
            }
        });

        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView
            .findViewById(android.support.v7.appcompat.R.id.search_src_text);

        // Collapse the search menu when the user hits the back key
        searchAutoComplete.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    searchListener.showSearch(false);
            }
        });

        try {
            // This sets the cursor
            // resource ID to 0 or @null
            // which will make it visible
            // on white background
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");

            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchAutoComplete, 0);

        } catch (Exception e) {
        } finally {
            return searchView;
        }

    }


}
