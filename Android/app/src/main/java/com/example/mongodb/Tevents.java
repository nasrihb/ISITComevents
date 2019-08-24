package com.example.mongodb;


import android.arch.lifecycle.LifecycleOwner;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.mongodb.Models.Event;
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


public class Tevents extends Fragment {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;
    ListView liste_tevent;
    public static String id_tevent;

    public Tevents() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //init service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        String email = Login.id;

        View view = inflater.inflate(R.layout.fragment_tevents, container, false);
        FloatingActionButton add = (FloatingActionButton) view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(getActivity(), AddEvent.class);
                startActivity(add);
            }
        });

        liste_tevent = (ListView) view.findViewById(R.id.liste_tevent);
        getEvents();
        return view;
    }

    public void getEvents() {
         Call<List<Event>> call = iMyService.getEvents();
         call.enqueue(new Callback<List<Event>>() {
             @Override
             public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                 if (!response.isSuccessful()) {
                     // Log.i("code : ",response.code());
                     Toast.makeText(getActivity(), "Code : " + response.code(), Toast.LENGTH_SHORT).show();
                     return;
                 }
                 List<Event> events = response.body();
                 ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
                 HashMap<String, Object> mapPPPP = null;
                 for (Event event : events) {
                     mapPPPP = new HashMap<String, Object>();
                     mapPPPP.put("titre", event.getTitre());
                     mapPPPP.put("date", event.getDate());
                     mapPPPP.put("heuredeb", event.getHeuredeb());
                     mapPPPP.put("heurefin", event.getHeurefin());
                     mapPPPP.put("id", event.get_id());

                     listItem.add(mapPPPP);
                 }
                 ArrayList<HashMap<String, Object>> listItema = listItem;
                 SimpleAdapter mSchedule = new SimpleAdapter(getContext(), listItema, R.layout.item_event,
                         new String[]{"date", "titre", "heuredeb", "heurefin"}, new int[]{R.id.date, R.id.titre_event, R.id.date_deb, R.id.date_fin});
                 mSchedule.setViewBinder(new MyViewBinder());
                 liste_tevent.setAdapter(mSchedule);

                 liste_tevent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                     @Override
                     @SuppressWarnings("unchecked")
                     public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                         final HashMap<String, Object> map = (HashMap<String, Object>) liste_tevent.getItemAtPosition(position);
                         String item = liste_tevent.getItemAtPosition(position).toString();
                         id_tevent = (String) map.get("id");
                         AlertDialog.Builder alerte = new AlertDialog.Builder(getActivity());
                         alerte.setItems(R.array.choix_tevent, new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int arg) {
                                 // TODO Auto-generated method stub
                                 switch (arg) {
                                     case 0:
                                         startActivity(new Intent(getContext(), Details_Event.class));
                                         break;
                                     default:
                                         Toast.makeText(getActivity(), "Erreur", Toast.LENGTH_LONG).show();
                                 }
                             }
                         });
                         alerte.show();
                     }
                 });
             }

             @Override
             public void onFailure(Call<List<Event>> call, Throwable t) {
                 Log.i("Msg", t.getMessage());
             }
         });

     }
}

