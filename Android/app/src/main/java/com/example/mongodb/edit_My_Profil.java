package com.example.mongodb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mongodb.Models.User;
import com.example.mongodb.Retrofit.IMyService;
import com.example.mongodb.Retrofit.RetrofitClient;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class edit_My_Profil extends AppCompatActivity {
    EditText name,email,phone,linkdin,address,info;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;
    String id_user= Login.id;
    ImageView save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_profil);

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
        save = (ImageView)findViewById(R.id.save_profil);



        Call<List< User >> call= iMyService.getuser(id_user);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(edit_My_Profil.this, "Code : " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<User> users = response.body();
                for (User user : users)
                {
                    name.setText(user.getName());
                    email.setText(user.getEmail());
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateuser(id_user,name.getText().toString(),info.getText().toString(),
                        address.getText().toString() ,phone.getText().toString(),
                        linkdin.getText().toString());
            }
        });

    }

    private void updateuser(String id_user, String name, String info, String address, String phone, String linkdin) {

        compositeDisposable.add(iMyService.updateUser(id_user,name,info,address,phone,linkdin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String response) throws Exception {
                        Intent i  = new Intent(edit_My_Profil.this,Profil.class);
                        startActivity(i);
                    }
                })
        );
    }
}

