package sashawilljerry.tipjar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Sasha on 11/22/2014.
 */

public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        Intent login2settings = getIntent();
        String emailaddress = login2settings.getStringExtra(LoginActivity.LOGIN2SETTINGS);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void bundleSettings(View view) {
        Intent settings2counter = new Intent(this, CounterActivity.class);
        Bundle fields = new Bundle();

        Intent login2settings = getIntent();
        String emailaddress = login2settings.getStringExtra(LoginActivity.LOGIN2SETTINGS);
        fields.putString("EMAILADDRESS", emailaddress);

        EditText emailsubject = (EditText) findViewById(R.id.emailsubject);
        String email_subject = emailsubject.getText().toString();
        fields.putString("EMAILSUBJECT", email_subject);

        EditText emailtext = (EditText) findViewById(R.id.emailtext);
        String email_text = emailtext.getText().toString();
        fields.putString("EMAILTEXT", email_text);

        EditText threshold = (EditText) findViewById(R.id.threshold);
        String cash_threshold = threshold.getText().toString();
        fields.putString("THRESHOLD", cash_threshold);

        EditText filepath = (EditText) findViewById(R.id.filepath);
        String file_path = filepath.getText().toString();
        fields.putString("FILEPATH", file_path);

        EditText accesstoken = (EditText) findViewById(R.id.accesstoken);
        String access_token = accesstoken.getText().toString();
        fields.putString("ACCESSTOKEN", access_token);

        settings2counter.putExtras(fields);
        startActivity(settings2counter);
    }


}