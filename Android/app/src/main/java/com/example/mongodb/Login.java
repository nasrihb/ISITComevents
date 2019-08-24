package com.example.mongodb;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mongodb.Models.IP;
import com.example.mongodb.Retrofit.IMyService;
import com.example.mongodb.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {
    EditText edt_mail, edt_password;
    Button sign_in, create_account;
    public static String id;
    public static String pass;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;

    IMyService iIpService;
    String ip,localisation,iemi;

    static  final  int Per=500;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //init service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        iIpService= Common.getIpService();

        //init view
        edt_mail = (EditText) findViewById(R.id.edt_email);
        edt_password = (EditText) findViewById(R.id.edt_password);
        sign_in = (Button) findViewById(R.id.btn_login);
        create_account = (Button) findViewById(R.id.btn_register);

        //permission
        int permissioncheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        //Pour vérifier si une autorisation est déjà accordée(READ_PHONE_STATE )
        if (permissioncheck == PackageManager.PERMISSION_GRANTED) {
            TelePhone();
            getIpAdress();
            localisation="Tunis, Tunisie";
        }
        else {
            //Pour demander l'autorisation au moment de l'exécution.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, Per);
        }

        //lien vers inscrit
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Login.this, Register.class);
                startActivity(j);
                finish();
            }
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(edt_mail.getText().toString(), edt_password.getText().toString());
                id = edt_mail.getText().toString();
                pass = edt_password.getText().toString();
                Log.i("id",id );
            }
        });
    }



    private void getIpAdress() {
        iMyService.getIP().enqueue(new Callback<IP>() {
            @Override
            public void onResponse(Call<IP> call, Response<IP> response) {
              ip=response.body().getIp();
            }
            @Override
            public void onFailure(Call<IP> call, Throwable t) {
                Log.e("ERROR",t.getMessage());
            }
        });
    }
    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Per: {
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    TelePhone();
                }
                else {
                    Toast.makeText(this, "Permission Required", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void TelePhone() {

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //Get the phone type
        String strphoneType = "";
        int phoneType = tm.getPhoneType();
        switch (phoneType) {
            case (TelephonyManager.PHONE_TYPE_CDMA): strphoneType = "CDMA"; break;
            case (TelephonyManager.PHONE_TYPE_GSM): strphoneType = "GSM";break;
            case (TelephonyManager.PHONE_TYPE_NONE):strphoneType = "NONE";break;
        }
        boolean isRoaming = tm.isNetworkRoaming();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        String IMEINumber = tm.getDeviceId();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        iemi=IMEINumber;
    }
    private void loginUser(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(Login.this, "Email cannont be null or empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(Login.this, "Password cannont be null or empty", Toast.LENGTH_SHORT).show();
            return;
        }
        compositeDisposable.add(iMyService.loginUser(email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String response) throws Exception {
/*
                        if (response.equals("\"Login Sucess\"")) {
                            addSession(id,ip,localisation,iemi);
                            Intent i = new Intent(Login.this, Accueil.class);
                            startActivity(i);

                        }
                        else {
                            Toast.makeText(Login.this, "Wrong Email or Password ", Toast.LENGTH_SHORT).show();
                            return;
                        }
*/
                       final AlertDialog.Builder alerte = new AlertDialog.Builder(Login.this);
                        alerte.setTitle("Login");
                        alerte.setMessage(response);
                        alerte.setPositiveButton("OK", new DialogInterface.OnClickListener() { // ajouter un bouton pour l'alerte

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                edt_mail.setText("");
                                edt_password.setText("");
                                if (response.equals("\"Login Sucess\"")) {
                                   // addSession(id,ip,localisation,iemi);
                                    Intent i = new Intent(Login.this, Accueil.class);
                                    startActivity(i);
                                }
                            }
                        });
                        alerte.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // ajouter un autre bouton dans l'alerte

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                finish();
                            }
                        });
                        alerte.show();
                    }
                })
        );
    }

    private void addSession(String email,String ip, String localisation, String iemi) {
        compositeDisposable.add(iMyService.addSession(email,ip, localisation, iemi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i("add session",id );
                    }
                })
        );

    }


}
