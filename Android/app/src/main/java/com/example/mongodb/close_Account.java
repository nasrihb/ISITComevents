package com.example.mongodb;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mongodb.Retrofit.IMyService;
import com.example.mongodb.Retrofit.RetrofitClient;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class close_Account extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;
    String email = Login.id;
    String pass = Login.pass;
    EditText password;
    TextView txterror;
    Button close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_account);

        //init service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        ImageView retour = (ImageView)findViewById(R.id.retour);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txterror = (TextView) findViewById(R.id.error);
        password = (EditText) findViewById(R.id.password);
        close = (Button)findViewById(R.id.close_account);
        close.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if (!(password.getText().toString().equals(pass)))
                  {
                      txterror.setText("Wrong Password !");
                  }
                  closeAccount();
              }
        });
    }

    private void closeAccount() {
        final AlertDialog.Builder alerte = new AlertDialog.Builder(close_Account.this);
        alerte.setTitle("Close Account");
        alerte.setIcon(R.drawable.warning);
        alerte.setMessage("Do you want to close this account");
        alerte.setPositiveButton("Yes", new DialogInterface.OnClickListener() { // ajouter un bouton pour l'alerte

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Call<Void> call = iMyService.closeAccount(email);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(close_Account.this, "Account successfully deleted", Toast.LENGTH_LONG).show();
                        Intent sup = new Intent(close_Account.this, Login.class);
                        startActivity(sup); }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.i("Msg", t.getMessage());
                    }
                });
            }
        });
        alerte.setNegativeButton("No", new DialogInterface.OnClickListener() { // ajouter un autre bouton dans l'alerte

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
            Intent cancel = new Intent(close_Account.this,close_Account.class);
            startActivity(cancel);
            }
        });
        alerte.show();
    }
}
