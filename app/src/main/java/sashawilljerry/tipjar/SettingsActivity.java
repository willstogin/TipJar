package sashawilljerry.tipjar;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

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

    public void sendMessage() {
        System.out.println("here2");
        Properties props = new Properties();
        props.put("mail.smtp.host", "in.mailjet.com");
        props.put ("mail.smtp.socketFactory.port", "465");
        props.put ("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put ("mail.smtp.auth", "true");
        props.put ("mail.smtp.port", "465");
        System.out.println("here3");
        final Session session = Session.getInstance(props,
                new javax.mail.Authenticator ()
                {
                    protected PasswordAuthentication getPasswordAuthentication ()
                    {
                        return new PasswordAuthentication ("79b28e261e95381c54fba0078672d434", "94cc9e45a80b3f07fa0a10ded9fea14d");
                    }
                });
        System.out.println("here4");
        try {
            Thread thread = new Thread(new Runnable(){
                @Override
                public void run() {
                    try{
                        MimeMessage msg = new MimeMessage(session);
                        System.out.println("here5");
                        msg.setFrom(new InternetAddress("sasha.weiss@u.northwestern.edu"));
                        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("tipjar.wildhacks2@gmail.com"));
                        msg.setSubject("Testing 1234");
                        msg.setSentDate(new Date());
                        msg.setText("This is a test email.\n");
                        Transport.send(msg);
                    } catch (Exception mex) {
                        System.out.println("send failed, exception: " + mex);
                    }
                }
            });
            thread.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}