package danbroid.searchview;

import java.lang.reflect.Field;

import org.slf4j.LoggerFactory;
import org.slf4j.impl.AndroidLoggerFactory;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

  private MenuItem searchItem;
  private SearchRecentSuggestions suggestions;
  private SearchView searchView;

  @Override
  protected void onCreate(Bundle state) {
    super.onCreate(state);

    setContentView(R.layout.layout);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    suggestions = new SearchRecentSuggestions(this, SuggestionProvider.AUTHORITY, SuggestionProvider.MODE);

    // Associate searchable configuration with the SearchView
    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

    searchView = new SearchView(getSupportActionBar().getThemedContext());
    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    searchView.setSubmitButtonEnabled(true);
    searchView.setIconifiedByDefault(true);
    searchView.setMaxWidth(1000);

    SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView
        .findViewById(android.support.v7.appcompat.R.id.search_src_text);

    // Collapse the search menu when the user hits the back key
    searchAutoComplete.setOnFocusChangeListener(new OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus)
          showSearch(false);
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
    }

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    searchItem = menu.add(android.R.string.search_go);

    searchItem.setIcon(R.drawable.ic_search_white_36dp);

    MenuItemCompat.setActionView(searchItem, searchView);

    MenuItemCompat.setShowAsAction(searchItem,
        MenuItemCompat.SHOW_AS_ACTION_ALWAYS | MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

    menu.add(0, R.id.menu_about, 0, R.string.lbl_about);

    return super.onCreateOptionsMenu(menu);
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    showSearch(false);
    Bundle extras = intent.getExtras();
    String userQuery = String.valueOf(extras.get(SearchManager.USER_QUERY));
    String query = String.valueOf(extras.get(SearchManager.QUERY));

    Toast.makeText(this, "query: " + query + " user_query: " + userQuery, Toast.LENGTH_SHORT).show();
  }

  protected void showSearch(boolean visible) {
    if (visible)
      MenuItemCompat.expandActionView(searchItem);
    else
      MenuItemCompat.collapseActionView(searchItem);
  }

  /**
   * Called when the hardware search button is pressed
   */
  @Override
  public boolean onSearchRequested() {
    showSearch(true);

    // dont show the built-in search dialog
    return false;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case R.id.menu_about:
      showAboutDialog();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  protected void showAboutDialog() {

    AlertDialog.Builder builder = new AlertDialog.Builder(getSupportActionBar().getThemedContext(),
        R.style.DialogTheme);
    builder.setPositiveButton(android.R.string.ok, null);
    builder.setTitle(getString(R.string.app_name) + " version: " + getString(R.string.versionName));
    builder.setIcon(R.drawable.ic_launcher);
    SpannableString aboutMessage = new SpannableString(Html.fromHtml(getString(R.string.msg_about)));
    builder.setMessage(aboutMessage);

    TextView messageText = (TextView) builder.show().findViewById(android.R.id.message);
    messageText.setMovementMethod(LinkMovementMethod.getInstance());
  }
}
