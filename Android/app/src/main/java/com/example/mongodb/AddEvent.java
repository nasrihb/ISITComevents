package com.example.mongodb;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mongodb.Retrofit.IMyService;
import com.example.mongodb.Retrofit.RetrofitClient;

import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AddEvent extends AppCompatActivity {

    EditText titre, heuredeb,date, heurefin, description, lieu;
    Spinner responsable;
    Button addevent;
    private int mYear, mMonth, mDay;
    ProgressDialog progressDialog;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        //init service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        String email = Login.id;

        //init view
        titre = (EditText) findViewById(R.id.edt_titre);
        heuredeb = (EditText) findViewById(R.id.edt_heuredeb);
        heurefin = (EditText) findViewById(R.id.edt_heurefin);
        description = (EditText) findViewById(R.id.edt_des);
        responsable = (Spinner) findViewById(R.id.responsable);
        lieu = (EditText) findViewById(R.id.edt_lieu);
        date = (EditText) findViewById(R.id.edt_date);
        addevent = (Button) findViewById(R.id.addevent);

        ImageView retour = (ImageView)findViewById(R.id.retour);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //liste deroulante statique

        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.choix_responsable, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // ce ligne pour ajouter le radio bouton devant chaque item
        responsable.setAdapter(adapter);// ici charger le spinner par le tableau créer


        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEvent(email,titre.getText().toString(), date.getText().toString(),
                        heuredeb.getText().toString() ,heurefin.getText().toString(),
                        lieu.getText().toString(),responsable.getSelectedItem().toString(),
                        description.getText().toString());
            }
        });
    }

    private void addEvent(String email,String titre, String date, String heuredeb, String heurefin,
                          String lieu, String responsable, String description) {

        if(titre.equals("")|| date.equals("")|| heuredeb.equals("")||heurefin.equals("")||
                lieu.equals("")||responsable.equals("")||description.equals("")){
            Toast.makeText(AddEvent.this, " \n" +
                    "please enter the required fields", Toast.LENGTH_SHORT).show();
            return;
        }
        compositeDisposable.add(iMyService.addEvent(email,titre,date,heuredeb,heurefin,lieu,responsable,description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String response) throws Exception {
                        Toast.makeText(AddEvent.this, "event successfully registered", Toast.LENGTH_SHORT).show();
                        Intent add = new Intent(AddEvent.this,Accueil.class);
                        startActivity(add);
                    }
                })
        );

    }
    public void DatePicker(View view) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}