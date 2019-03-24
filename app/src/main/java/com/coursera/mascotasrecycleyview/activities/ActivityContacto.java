package com.coursera.mascotasrecycleyview.activities;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coursera.mascotasrecycleyview.R;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ActivityContacto extends AppCompatActivity {

    private String correo;
    private String contrasenia;

    private EditText etNombreCompleto;
    private EditText etCorreo;
    private EditText etMensaje;
    private Button btnEnviar;

    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        setInitialValues();
        setUpLayout();
        setUpUIInteraction();
    }

    /**Metodo que agrega los valores a los componentes
     * @return void
     * */
    public void setInitialValues() {
        Toolbar miActionBar = (Toolbar) findViewById(R.id.miActionBar);
        setSupportActionBar(miActionBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etNombreCompleto = (EditText) findViewById(R.id.etNombreCompleto);
        etCorreo = (EditText) findViewById(R.id.etCorreo);
        etMensaje = (EditText) findViewById(R.id.etMensaje);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
    }

    /**Metodo que inicializa los componentes
     * @return void
     * */
    public void setUpLayout() {

    }

    /**Metodo donde se definen los comportamientos de los componente con los eventos de usuario
     * @return void
     * */
    public void setUpUIInteraction() {
        correo = "saulnoelm@gmail.com";//Tu correo
        contrasenia = "noeru1982";//Tu contrasenia

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Properties properties = new Properties();
                properties.put("mail.smtp.host", "smtp.googlemail.com");
                properties.put("mail.smtp.socketFactory.port", "465");
                properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.port", "465");

                try {
                    session = Session.getDefaultInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(correo, contrasenia);
                        }
                    });
                    if (session!= null) {
                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(correo));
                        message.setSubject("Envio de mensaje por app android");
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse((etCorreo.getText().toString())));
                        message.setContent(
                                "Hola " + etNombreCompleto.getText() + "<br>" +
                                etMensaje.getText(),
                                "text/html; charset=utf-8"
                        );
                        Transport.send(message);
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.correo_enviado), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
