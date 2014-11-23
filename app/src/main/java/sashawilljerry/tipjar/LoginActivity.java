package sashawilljerry.tipjar;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.activation.DataHandler;
import javax.mail.internet.MimeMessage;
import javax.activation.DataHandler.*;


public class LoginActivity extends ActionBarActivity {

    public final static String LOGIN2SETTINGS = "sashawilljerry.tipjar.LOGIN2SETTINGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
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

    public void saveEmail(View view) {
        Intent login2settings = new Intent(this, CounterActivity.class);
        EditText editText1 = (EditText) findViewById(R.id.email_address);
        String emailaddress = editText1.getText().toString();
        login2settings.putExtra(LOGIN2SETTINGS, emailaddress);
    }



}
