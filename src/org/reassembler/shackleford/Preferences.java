package org.reassembler.shackleford;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

public class Preferences extends Activity {
    private EditText txtDrawDelay;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.preferences);

        txtDrawDelay = (EditText) findViewById(R.id.txtDrawDelay);

        SharedPreferences preferences = getSharedPreferences(Deal.PREFERENCES_KEY, MODE_PRIVATE);

        txtDrawDelay.setText(String.valueOf(preferences.getInt(Deal.TIME_BETWEEN_CARDS_KEY, 2)));
    }

    @Override
    protected void onPause() {
        try {
            getSharedPreferences(Deal.PREFERENCES_KEY, MODE_PRIVATE)
                .edit().putInt(Deal.TIME_BETWEEN_CARDS_KEY,
                    Integer.parseInt(txtDrawDelay.getText().toString())).commit();
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }

        super.onPause();
    }
}