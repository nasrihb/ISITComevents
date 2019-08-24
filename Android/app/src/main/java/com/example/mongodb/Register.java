package com.example.mongodb;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mongodb.Retrofit.IMyService;
import com.example.mongodb.Retrofit.RetrofitClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Register extends AppCompatActivity {

    EditText edt_register_mail, edt_register_password, edt_register_name;
    CheckBox checkBox;
    Button sign_up;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;

    // reCAPTCHA

    final String SiteKey = "6LfUfZoUAAAAAIZKSsDLv74aV09aXz0OIMp_fXDw";
    final String SecretKey = "6LfUfZoUAAAAAM53z7PiMp7HRwfJ5pKfzLl7-9lL";
    public String TAG = "Register";
    public String userResponseToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //init service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        //init view
        edt_register_mail = (EditText) findViewById(R.id.edt_email);
        edt_register_password = (EditText) findViewById(R.id.edt_password);
        edt_register_name = (EditText) findViewById(R.id.edt_nom);
        sign_up = (Button) findViewById(R.id.sign_up);
        checkBox = (CheckBox) findViewById(R.id.nerobot);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(edt_register_mail.getText().toString(),
                        edt_register_name.getText().toString(),
                        edt_register_password.getText().toString());
            }
        });
    }
    private void registerUser(String email, String name, String password) {

        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(edt_register_mail.getText().toString());   // On déclare un matcher, qui comparera le pattern avec la string passée en argument
        if (!m.matches()) {
            Toast.makeText(Register.this,"Invalid email",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(edt_register_mail.getText().toString())) {
            Toast.makeText(Register.this, "Email cannont be null or empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(edt_register_password.getText().toString())) {
            Toast.makeText(Register.this, "Password cannont be null or empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(edt_register_name.getText().toString())) {
            Toast.makeText(Register.this, "Name cannont be null or empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!(checkBox.isChecked())) {
            Toast.makeText(Register.this, "Checkbox not checked", Toast.LENGTH_SHORT).show();
            return;
        }
        compositeDisposable.add(iMyService.registerUser(email, name, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String response) throws Exception {
                        //Toast.makeText(Register.this, "" + response, Toast.LENGTH_SHORT).show();
                        if (response.equals("\"Registration Success\"")) {
                             final AlertDialog.Builder alerte = new AlertDialog.Builder(Register.this);
                             alerte.setTitle("Registration");
                             alerte.setIcon(R.drawable.registration_success);
                             alerte.setMessage("Registration Success");
                             alerte.setPositiveButton("OK", new DialogInterface.OnClickListener() { // ajouter un bouton pour l'alerte

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                     Intent i = new Intent(Register.this, Login.class);
                                    startActivity(i);
                            }
                            });
                             alerte.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // ajouter un autre bouton dans l'alerte

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                // TODO Auto-generated method stub
                                finish();
                            }
                        });
                        alerte.show();
                    }
                    else{

                            final AlertDialog.Builder alerte = new AlertDialog.Builder(Register.this);
                            alerte.setTitle("Registration");
                            alerte.setMessage("Email alredy exists");
                            alerte.setIcon(R.drawable.error);
                            alerte.setPositiveButton("OK", new DialogInterface.OnClickListener() { // ajouter un bouton pour l'alerte

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                  /*  Intent i = new Intent(Register.this, Register.class);
                                    startActivity(i);*/
                                    edt_register_mail.setText("");

                                }
                            });
                            alerte.show();
                        }
                    }

                })
        );
    }
    public void connect(View v) {
        SafetyNet.getClient(this).verifyWithRecaptcha(SiteKey)
                .addOnSuccessListener(this,
                        new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                            @Override
                            public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                                // Indicates communication with reCAPTCHA service was successful.
                                userResponseToken = response.getTokenResult();
                                Log.d(TAG, "response " + userResponseToken);
                                if (!userResponseToken.isEmpty()) {
                                    // new Check().execute();
                                }
                            }
                        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            // An error occurred when communicating with the reCAPTCHA service. Refer to the status code to
                            // handle the error appropriately.
                            ApiException apiException = (ApiException) e;
                            int statusCode = apiException.getStatusCode();
                            Log.d(TAG, "Error: " + CommonStatusCodes
                                    .getStatusCodeString(statusCode));
                        } else {
                            // A different, unknown type of error occurred.
                            Log.d(TAG, "Error: " + e.getMessage());
                        }
                    }
                });
    }

    public class Check extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Register.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please Wait ");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String isSuccess = "";
            InputStream is = null;
            String API = "https://www.google.com/recaptcha/api/siteverify?";
            String newAPI = API + "secret=" + SecretKey + "&response=" + userResponseToken;
            Log.d(TAG, " API  " + newAPI);
            try {
                URL url = new URL(newAPI);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(8000 /* milliseconds */);
                httpURLConnection.setConnectTimeout(4000 /* milliseconds */);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
// Starts the query
                httpURLConnection.connect();
                int response = httpURLConnection.getResponseCode();
                progressDialog.dismiss();
                System.out.println(response);
                is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                String result = stringBuilder.toString();
                Log.d("Api", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    System.out.println("Result Object :  " + jsonObject);
                    isSuccess = jsonObject.getString("success");
                    System.out.println("obj " + isSuccess);
                } catch (Exception e) {
                    Log.d("Exception: ", e.getMessage());
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }
            return isSuccess;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                if (s != null) {
                    switch (s) {
                        case "true":
                            return;
                        case "socketexception":
                            return;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
