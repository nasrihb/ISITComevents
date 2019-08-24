package com.example.mongodb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.mongodb.Models.Event;
import com.example.mongodb.Models.Session;
import com.example.mongodb.Retrofit.IMyService;
import com.example.mongodb.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Sessions extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;
    ListView liste_session;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);
        //init service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        //init view
        liste_session = (ListView) findViewById(R.id.list_sessions);
        email = Login.id;
        getSessions();


    }

    private void getSessions() {
        Call<List<Session>> call= iMyService.getsession(email);
        call.enqueue(new Callback<List<Session>>() {
            @Override
            public void onResponse(Call<List<Session>> call, Response<List<Session>> response) {
                if (!response.isSuccessful()){
                    // Log.i("code : ",response.code());
                    Toast.makeText(Sessions.this, "Code : " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Session> sessions = response.body();
                ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
                HashMap<String, Object> mapPPPP=null;
                for (Session session : sessions)
                {
                    mapPPPP = new HashMap<String, Object>();
                    mapPPPP.put("ip",session.getIp());
                    mapPPPP.put("localisation",session.getLocalisation());
                    mapPPPP.put("iemi",session.getIemi());

                    listItem.add(mapPPPP);
                }
                ArrayList<HashMap<String, Object>> listItema = listItem;
                SimpleAdapter mSchedule = new SimpleAdapter(Sessions.this, listItema, R.layout.item_sessions,
                        new String[] {"ip", "localisation","iemi"}, new int[] {R.id.ip ,R.id.localisation,R.id.iemi});
                mSchedule.setViewBinder(new MyViewBinder());
                liste_session.setAdapter(mSchedule);

            }

            @Override
            public void onFailure(Call<List<Session>> call, Throwable t) {
                Log.i("Msg",t.getMessage());
            }
        });
    }

}
