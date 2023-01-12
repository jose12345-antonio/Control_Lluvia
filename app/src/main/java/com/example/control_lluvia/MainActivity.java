package com.example.control_lluvia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +
                    //"(?=.*[a-z])" +
                    //"(?=.*[A-Z])" +
                    "(?=.*[a-zA-Z])" +
                    "(?=.*[@#$%^&+=])" +
                    "(?=\\S+$)" +
                    ".{4,}" +
                    "$");

    private EditText edtCorreoInicio, edtPasswordInicio;
    RadioButton radioButton;
    private boolean isActivateRadioButton;
    private AsyncHttpClient usuario_clien;
    Button btnInicio;
    TextView txtOlvidadPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario_clien = new AsyncHttpClient();
        if(Preferences.obtenerPreferenceBoolean(this,Preferences.PREFERENCE_ESTADO_BUTTON_SESION)) {
            if (Preferences.obtenerPreferenceStringRol(this, Preferences.PREFERENCE_ROL_LOGIN).equals("usuario")) {
                Intent intent = new Intent(getApplicationContext(), HomeUser.class);
                startActivity(intent);
                MainActivity.super.onBackPressed();

            }
        }

        txtOlvidadPassword = findViewById(R.id.txtOlvidadPassword);
        txtOlvidadPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), OlvidarPassword.class));
            }
        });

        edtCorreoInicio = findViewById(R.id.edtCorreoInicio);
        edtPasswordInicio = findViewById(R.id.edtPasswordInicio);

        btnInicio =findViewById(R.id.btnInicio);

        radioButton = findViewById(R.id.radioSesion);
        isActivateRadioButton = radioButton.isChecked();
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isActivateRadioButton){
                    radioButton.setChecked(false);
                }
                isActivateRadioButton = radioButton.isChecked();
            }
        });

        botonLogin();
    }
    private void botonLogin(){
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidationEmail();
                ValidationPassword();
                //pregunta mediante la sentencias if
                if (edtCorreoInicio.getText().toString().isEmpty() || edtPasswordInicio.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Hay Campos En Blanco!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String usuario = edtCorreoInicio.getText().toString().replace(" ", "%20");
                    String password = edtPasswordInicio.getText().toString().replace(" ", "%20");
                    String url = "https://devtesis.com/tesis-invernadero/logear.php?nombre_usuario="+usuario+"&password="+password;
                    //Se sincroniza con la web
                    usuario_clien.post(url, new AsyncHttpResponseHandler() {
                        //almacenar mediante el método onSuccess
                        @Override
                        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                            //Solicitud a la web
                            if (statusCode == 200) {
                                String respuesta = new String(responseBody);
                                if (respuesta.equalsIgnoreCase("null")) {
                                    Toast.makeText(MainActivity.this, "Error De Usuario y/o Contraseña!!", Toast.LENGTH_SHORT).show();

                                } else {
                                    try {
                                        //Se crea un objeto a la función de conversión a JSONObject
                                        JSONObject jsonObj = new JSONObject(respuesta);
                                        //Se crea un objeto a la clase de LogearUsuario
                                        Logear_usuario user = new Logear_usuario();
                                        //Establecer a la variable de la clase Logear_Usuario
                                        //todo lo que se obtiene de la clase JSONObj
                                        user.setId(jsonObj.getInt("id_usuario"));
                                        user.setPassword(jsonObj.getString("password"));
                                        user.setRol(jsonObj.getInt("rol_id"));
                                        //user.setNombre_rol(jsonObj.getString("nombre_rol"));
                                        Intent i = null;
                                        switch (user.getRol()) {
                                            case 1:
                                                user.setNombre_rol("Usuario");
                                                Preferences.guardarPreferenceBoolean(MainActivity.this,radioButton.isChecked(),Preferences.PREFERENCE_ESTADO_BUTTON_SESION);
                                                Preferences.guardarPreferenceString(MainActivity.this,edtCorreoInicio.getText().toString(),Preferences.PREFERENCE_USUARIO_LOGIN);
                                                Preferences.guardarPreferenceStringRol(MainActivity.this,"usuario",Preferences.PREFERENCE_ROL_LOGIN);
                                                //if(Preferences.obtenerPreferenceBoolean(MainActivity.this,Preferences.PREFERENCE_ESTADO_BUTTON_SESION)) {
                                                startActivity(new Intent(MainActivity.this, HomeUser.class));
                                                MainActivity.super.onBackPressed();
                                                //}

                                                break;

                                            default:
                                                Toast.makeText(getApplicationContext(),"PROBLEMA CON EL ROL",Toast.LENGTH_SHORT).show();
                                                break;
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText( MainActivity.this, "Error Desconocido. Intentelo De Nuevo!!", Toast.LENGTH_SHORT).show();
                            edtPasswordInicio.setText("");
                        }


                    });
                }
            }


        });
    } // C


    //Método de validación de correo electrónico
    private boolean ValidationEmail(){
        String emailInput = edtCorreoInicio.getText().toString().trim();

        if (emailInput.isEmpty()) {

            edtCorreoInicio.setError("El campo no puede estar vacío");

            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            return false;
        } else {
            return true;
        }
    }
    //Método de validación de contraseña
    private boolean ValidationPassword() {
        String passwordInput = edtPasswordInicio.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            edtPasswordInicio.setError("El campo no puede estar vacío");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {

            return false;
        } else {
            return true;
        }
    }

}