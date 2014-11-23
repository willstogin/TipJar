package sashawilljerry.tipjar;

import android.app.AlertDialog;
import android.content.Intent;
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

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


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


    public CounterActivity() {
        super();
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

        Intent setting2counter = getIntent();
        Bundle fields = setting2counter.getExtras();
        senderAddress = fields.getString("EMAILADDRESS");
        subject = fields.getString("EMAILSUBJECT");
        text = fields.getString("EMAILTEXT");
        threshold = fields.getDouble("THRESHOLD");
        filePath = fields.getString("FILEPATH");
        accessToken = fields.getString("ACCESSTOKEN");


        //while(true) {
             afterDate = ft.format(new Date());
            //try {
           //     Thread.sleep(3000);

            //} catch (InterruptedException ex){
            //    Thread.currentThread().interrupt();

            //}
             //url = "https://api.venmo.com/v1/"+accessToken+"?after=" + afterDate;
             //url = "https://api.venmo.com/v1/"+accessToken+"?limit=3";
             //input = readPage(url);
             new AlertDialog.Builder(this).setTitle("Derp").setMessage("sup");
             //try {
             //     json = new JSONObject(input);
            // } catch (Exception e) {
            // e.printStackTrace();
             //}
                //parse for info and send the message(s)


        //}

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
        Properties props = new Properties();
        props.put("mail.smtp.host", "in.mailjet.com");
        props.put ("mail.smtp.socketFactory.port", "465");
        props.put ("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put ("mail.smtp.auth", "true");
        props.put ("mail.smtp.port", "465");
        final Session session = Session.getInstance(props,
                new javax.mail.Authenticator ()
                {
                    protected PasswordAuthentication getPasswordAuthentication ()
                    {
                        return new PasswordAuthentication ("79b28e261e95381c54fba0078672d434", "94cc9e45a80b3f07fa0a10ded9fea14d");
                    }
                });
        try {
            Thread thread = new Thread(new Runnable(){
                @Override
                public void run() {
                    try{
                        // Create a default MimeMessage object.
                        Message message = new MimeMessage(session);

                        // Set From: header field of the header.
                        message.setFrom(new InternetAddress(senderAddress));

                        // Set To: header field of the header.
                        message.setRecipients(Message.RecipientType.TO,
                                InternetAddress.parse("tipjar.wildhacks2@gmail.com"));

                        // Set Subject: header field
                        message.setSubject(subject);

                        // Create the message part
                        BodyPart messageBodyPart = new MimeBodyPart();

                        // Now set the actual message
                        messageBodyPart.setText(text);

                        // Create a multipar message
                        Multipart multipart = new MimeMultipart();

                        // Set text message part
                        multipart.addBodyPart(messageBodyPart);

                        // Part two is attachment
                        messageBodyPart = new MimeBodyPart();
                        String filename = filePath;
                        DataSource source = new FileDataSource(filename);
                        messageBodyPart.setDataHandler(new DataHandler(source));
                        messageBodyPart.setFileName(filename);
                        multipart.addBodyPart(messageBodyPart);

                        // Send the complete message parts
                        message.setContent(multipart);

                        // Send message
                        Transport.send(message);

                        /*MimeMessage msg = new MimeMessage(session);
                        Multipart multipart = new MimeMultipart();
                        MimeBodyPart attachment = new MimeBodyPart();
                        attachment.attachFile(filePath);
                        MimeBodyPart messageBodyPart = new MimeBodyPart();
                        messageBodyPart.setContent(msg, text);
                        msg.setFrom(new InternetAddress(senderAddress));
                        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("tipjar.wildhacks2@gmail.com"));
                        msg.setSubject(subject);
                        msg.setSentDate(new Date());
                        Transport.send(msg);*/
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