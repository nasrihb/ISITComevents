package com.example.mongodb;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Mevents extends Fragment {


    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;
    ListView liste_mevent;
    String email;
    public static String id_mevent;

    public Mevents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //init service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        //init view
        View view = inflater.inflate(R.layout.fragment_mevents, container, false);
        FloatingActionButton add = (FloatingActionButton) view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(getActivity(), AddEvent.class);
                startActivity(add);
            }
        });
        liste_mevent = (ListView) view.findViewById(R.id.liste_mevent);
        email = Login.id;
        getMyEvents();
        getActivity();
       // getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        return view;
    }
    private void getMyEvents(){

        Call<List<Event>> call= iMyService.geteventemail(email);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (!response.isSuccessful()){
                    // Log.i("code : ",response.code());
                    Toast.makeText(getActivity(), "Code : " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Event> events = response.body();
                ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
                HashMap<String, Object> mapPPPP=null;
                for (Event event : events)
                {
                    mapPPPP = new HashMap<String, Object>();
                    mapPPPP.put("titre",event.getTitre());
                    mapPPPP.put("date",event.getDate());
                    mapPPPP.put("heuredeb",event.getHeuredeb());
                    mapPPPP.put("heurefin",event.getHeurefin());
                    mapPPPP.put("id",event.get_id());

                    listItem.add(mapPPPP);
                }
                ArrayList<HashMap<String, Object>> listItema = listItem;
                SimpleAdapter mSchedule = new SimpleAdapter(getContext(), listItema, R.layout.item_event,
                        new String[] {"date", "titre","heuredeb","heurefin"}, new int[] {R.id.date ,R.id.titre_event,R.id.date_deb,R.id.date_fin});
                mSchedule.setViewBinder(new MyViewBinder());
                liste_mevent.setAdapter(mSchedule);

                liste_mevent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                        final HashMap<String, Object> map = (HashMap<String, Object>) liste_mevent.getItemAtPosition(position);
                        String item = liste_mevent.getItemAtPosition(position).toString();
                        id_mevent = (String) map.get("id");
                        //  startActivity(i);
                        AlertDialog.Builder alerte = new AlertDialog.Builder(getActivity());

                        alerte.setItems(R.array.choix_mevent, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int arg) {
                                // TODO Auto-generated method stub
                                switch (arg) {
                                    case 0: startActivity(new Intent(getContext(), Details_My_Event.class)); break;
                                    case 1: RemoveEvent(); break;
                                    case 2: startActivity(new Intent(getContext(), Edit_My_Event.class)); break;
                                    default:Toast.makeText(getActivity(), "Erreur", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        alerte.show();

                    }

                });

            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.i("Msg",t.getMessage());
            }
        });

    }

    private void RemoveEvent() {
       final AlertDialog.Builder alerte = new AlertDialog.Builder(getActivity());
        alerte.setTitle("Remove Event");
        alerte.setIcon(R.drawable.warning);
        alerte.setMessage("Do you want to delete this event");
        alerte.setPositiveButton("Yes", new DialogInterface.OnClickListener() { // ajouter un bouton pour l'alerte

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Call<Void> call= iMyService.deleteEvent(id_mevent);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(getActivity().getApplicationContext(), "Event successfully deleted", Toast.LENGTH_LONG).show();
                        Intent sup = new Intent(getActivity(),Accueil.class);
                        startActivity(sup);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.i("Msg",t.getMessage());
                    }
                });
            }
        });
        alerte.setNegativeButton("No", new DialogInterface.OnClickListener() { // ajouter un autre bouton dans l'alerte

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
               getActivity();
            }
        });
        alerte.show();
    }

}




