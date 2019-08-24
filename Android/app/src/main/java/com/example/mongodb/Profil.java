package com.example.mongodb;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mongodb.Models.Event;
import com.example.mongodb.Models.User;
import com.example.mongodb.Retrofit.IMyService;
import com.example.mongodb.Retrofit.RetrofitClient;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Profil extends AppCompatActivity {
     EditText name,email,phone,linkdin,address,info;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;
    ImageView retour;
    String id_user= Login.id;
    ImageView edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        //init service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        //init view
        name = (EditText)findViewById(R.id.edt_name);
        email = (EditText)findViewById(R.id.edt_email);
        phone = (EditText)findViewById(R.id.edt_phone);
        address = (EditText)findViewById(R.id.edt_adress);
        linkdin = (EditText)findViewById(R.id.edt_linkdin);
        info = (EditText)findViewById(R.id.edt_info);

        retour = (ImageView)findViewById(R.id.retour) ;
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profil.this,Accueil.class);
                startActivity(i);
            }
        });

        edit = (ImageView)findViewById(R.id.editprofil);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(Profil.this,edit_My_Profil.class);
                startActivity(edit);
            }
        });

        //getuser
        Call<List<User>> call= iMyService.getuser(id_user);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(Profil.this, "Code : " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<User> users = response.body();
                for (User user : users)
                {
                    name.setText(user.getName());
                    email.setText(user.getEmail()+"\n" + user.getEmail2());//+ "   Primary email" +"\n" + user.getEmail2()
                    phone.setText(user.getTel());
                    address.setText(user.getAdress());
                    info.setText(user.getInfo());
                    linkdin.setText(user.getLinkdin());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.i("Msg",t.getMessage());
            }
        });
    }
}
