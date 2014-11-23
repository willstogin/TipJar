package sashawilljerry.tipjar;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;

import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.activation.DataHandler;
import javax.mail.internet.MimeMessage;
import javax.activation.DataHandler.*;


public class Login extends ActionBarActivity {

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

    public void onButtonPressed(View view) {
        System.out.println("Here");
        sendMessage();
    }

    public void sendMessage() {
        System.out.println("here2");
        Properties props = new Properties();
        //props.put("mail.smtp.host", "in-v3.mailjet.com");
        props.put("mail.smtp.host", "in.mailjet.com");
        props.put ("mail.smtp.socketFactory.port", "465");
        props.put ("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put ("mail.smtp.auth", "true");
        props.put ("mail.smtp.port", "465");
        //props.put("mail.smtp.host", "smtp.gmail.com");
        //props.put("mail.smtp.starttls.enable", "true");
        System.out.println("here3");
        //Authenticator auth = new SMTPAuthenticator();
        final Session session = Session.getInstance(props,
                new javax.mail.Authenticator ()
                {
                    protected PasswordAuthentication getPasswordAuthentication ()
                    {
                        //return new PasswordAuthentication ("tipjar.wildhacks@gmail.com", "NUWildhacks");
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
                       msg.setFrom(new InternetAddress("tipjar.wildhacks@gmail.com"));
                       msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("tipjar.wildhacks2@gmail.com"));
                       msg.setSubject("Froot Loops are better than Cheerios");
                       msg.setSentDate(new Date());
                       msg.setText("Hello, world!\n");
                       //Transport.send(msg, "79b28e261e95381c54fba0078672d434", "94cc9e45a80b3f07fa0a10ded9fea14d");
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

    /**

    public void Test1(View view) {
        Intent intent= new Intent(this, AuthenticationActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_)

    }
     */

}
