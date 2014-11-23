package sashawilljerry.tipjar;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.*;


import java.util.Date;
import java.util.Properties;

import java.text.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



/**
 * Created by Sasha on 11/22/2014.
 */


public class CounterActivity extends ActionBarActivity {
    private String senderAddress;
    private String subject;
    private String text;
    private double threshold;
    private String filePath;
    private String accessToken;


    public CounterActivity(String senderAddress, String subject, String text, double threshold, String filePath, String token) {
        super();
        accessToken = token;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counter_activity);
        String input;
        String url;
        String afterDate;
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd'T'hh:mm:ss");
        JSONObject json;
        while(true) {
             afterDate = ft.format(new Date());
            try {
                Thread.sleep(30000);

            } catch (InterruptedException ex){
                Thread.currentThread().interrupt();

            }
             url = "https://api.venmo.com/v1/"+accessToken+"?after=" + afterDate;
             input = readPage(url);
             new AlertDialog.Builder(this).setTitle("Derp").setMessage(input);
             try {
                  json = new JSONObject(input);
             } catch (Exception e) {
             e.printStackTrace();
             }
                //parse for info and send the message(s)


        }

    }

    public String readPage(String url) {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.e(JSONObject.class.toString(), "Failed to download file");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();

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