package com.example.mongodb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mongodb.Models.Event;
import com.example.mongodb.Retrofit.IMyService;
import com.example.mongodb.Retrofit.RetrofitClient;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Details_Event extends AppCompatActivity {
    TextView titre, responsable, heuredeb,date, heurefin, description, lieu;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;
    String id_tevent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_event);

        //init service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        id_tevent=Tevents.id_tevent;

        //init view
        titre = (TextView) findViewById(R.id.titre);
        heuredeb = (TextView) findViewById(R.id.heuredeb);
        heurefin = (TextView) findViewById(R.id.heurefin);
        description = (TextView) findViewById(R.id.description);
        responsable = (TextView) findViewById(R.id.responsable);
        lieu = (TextView) findViewById(R.id.lieu);
        date = (TextView) findViewById(R.id.date);

        ImageView retour = (ImageView)findViewById(R.id.retour);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Call<List<Event>> call= iMyService.getdetailsevent(id_tevent);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (!response.isSuccessful()){
                    // Log.i("code : ",response.code());
                    Toast.makeText(Details_Event.this, "Code : " + response.code(), Toast.LENGTH_SHORT).show();
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
                 responsable.setText(event.getResponsable());
                 description.setText(event.getDescription());
                }
                }
            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.i("Msg",t.getMessage());
            }
        });


 }
}
