package com.heimder;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import heimder.com.heimder.R;

/**
 * Created by JoaoEmilio on 28/12/2015.
 */
public abstract class HeimderAppCompatActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_secundario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_home:
                Log.i(Heimder.APP_NAME, "menu_home");
                finish();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
