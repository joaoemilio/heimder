package com.heimder.ui.helper;

import android.app.Activity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;

import com.heimder.Heimder;

/**
 * Created by JoaoEmilio on 29/12/2015.
 */
public class UIHelper {

    protected Activity activity;

    protected String getEditTextString(int id) {
        String result = null;

        EditText editText = (EditText)activity.findViewById(id);
        result = editText.getText().toString();

        return result;
    }

    protected Object getSpinnerItemSelected(int id) {
        Object result = null;

        Spinner spinner = (Spinner)activity.findViewById(id);
        result = spinner.getSelectedItem();
        Log.i(Heimder.APP_NAME, "spinner selected item: " + result);

        return result;
    }

}
