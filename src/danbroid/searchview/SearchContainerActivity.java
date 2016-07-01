package danbroid.searchview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by elodief on 7/1/16.
 */

public class SearchContainerActivity extends AppCompatActivity {

    public static final String FRAGMENT_TAG = "fragmentTag";

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.toolbar_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new SearchFragment();
            getSupportFragmentManager().beginTransaction()
                                       .replace(R.id.fragmentContainer, fragment, FRAGMENT_TAG)
                                       .commit();
        }
    }
}
