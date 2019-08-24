package com.example.mongodb;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mongodb.Models.Event;
import com.example.mongodb.Retrofit.IMyService;
import com.example.mongodb.Retrofit.RetrofitClient;

import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Edit_My_Event extends AppCompatActivity {
    EditText titre, heuredeb,date, heurefin, description, lieu;
    Spinner responsable;
    Button editevent;
    private int mYear, mMonth, mDay;
    ProgressDialog progressDialog;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;
    String id = Mevents.id_mevent;
    String email= Login.id;
    ImageView time,dateCal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_event);


        //init service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);


        //init view
        titre = (EditText) findViewById(R.id.edt_titre);
        heuredeb = (EditText) findViewById(R.id.edt_heuredeb);
        heurefin = (EditText) findViewById(R.id.edt_heurefin);
        description = (EditText) findViewById(R.id.edt_des);
        responsable = (Spinner) findViewById(R.id.responsable);
        lieu = (EditText) findViewById(R.id.edt_lieu);
        date = (EditText) findViewById(R.id.edt_date);
        editevent = (Button) findViewById(R.id.addevent);

        ImageView retour = (ImageView) findViewById(R.id.retour);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
            editevent();
    }
    public void editevent(){
        Call<List<Event>> call= iMyService.geteventid(id);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (!response.isSuccessful()){
                    // Log.i("code : ",response.code());
                    Toast.makeText(Edit_My_Event.this, "Code : " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Event> events = response.body();
                for (Event event : events)
                {
                    titre.setText(event.getTitre());
                    date.setText(event.getDate());
                    lieu.setText(event.getLieu());
                    heuredeb.setText(event.getHeuredeb());
                    heurefin.setText(event.getHeurefin());
                    String myValue =  event.getResponsable();
                    responsable.setSelection(getIndex(responsable, myValue));
                    description.setText(event.getDescription());
                }
            }
            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.i("Msg",t.getMessage());
            }
        });

        //liste deroulante statique

        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.choix_responsable, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // ce ligne pour ajouter le radio bouton devant chaque item
        responsable.setAdapter(adapter);// ici charger le spinner par le tableau créer
        editevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editevent(id,email,titre.getText().toString(), date.getText().toString(),
                        heuredeb.getText().toString() ,heurefin.getText().toString(),
                        lieu.getText().toString(),responsable.getSelectedItem().toString(),
                        description.getText().toString());
            }
        });
    }
    private void editevent(String id,String email,String titre, String date, String heuredeb, String heurefin,
                          String lieu, String responsable, String description) {

         compositeDisposable.add(iMyService.editEvent(id,email,titre,date,heuredeb,heurefin,lieu,responsable,description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String response) throws Exception {
                        Toast.makeText(Edit_My_Event.this, "Event successfully updated", Toast.LENGTH_SHORT).show();
                        //finish();

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
    public void TimePicker1(View view) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        // Create a new instance of TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(Edit_My_Event.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                heuredeb.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
    public void TimePicker2(View view) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        // Create a new instance of TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(Edit_My_Event.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                heurefin.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    //private method of your class
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }
    }

