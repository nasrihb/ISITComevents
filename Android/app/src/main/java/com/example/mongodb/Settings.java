package com.example.mongodb;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {
  ListView liste_setting;
  String[] titre;
  String[] stitre;
    public Settings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_settings, container, false);
        liste_setting = (ListView) view.findViewById(R.id.list_setting);
        titre = new String[]{"Email addresses","Phone numbers","Change password","Language","Where you're signed in","Search history","Close account"};
        stitre = new String[]{"Add or remove email addresses on your account",
                              "Add a phone number to help keep your acount secure",
                              "Choose a unique password to protect your account",
                              "Let us know your lnguage and translation preferences",
                               "See your active session,and sign out if you'dlike",
                               "Clear all previous searches performed on ISITCom Events",
                              "Learn about your options, and close your account if you wish"};
        CustomAdapter adapter= new CustomAdapter();
        liste_setting.setAdapter(adapter);
        liste_setting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                    if(i == 0){
                        Intent email = new Intent(getActivity(),changeEmail.class);
                        startActivity(email);
                    }
                     if(i == 1){
                         Intent phone = new Intent(getActivity(),change_phone.class);
                         startActivity(phone);
                     }
                    if (i == 2 ) {
                        Intent pass = new Intent(getActivity(),ChangePassword.class);
                         startActivity(pass);
                    }
                    if(i == 4){
                        Intent session = new Intent(getActivity(),Sessions.class);
                        startActivity(session);
                    }
                    if(i == 6){
                         Intent close = new Intent(getActivity(),close_Account.class);
                         startActivity(close);
                    }


                }

        });
        return view;
    }
    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return titre.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.item_setting, null);
            TextView Titre =(TextView)view.findViewById(R.id.titre);
            TextView Stitre =(TextView)view.findViewById(R.id.soustitre);
            Titre.setText(titre[i]);
            Stitre.setText(stitre[i]);
            return view;
        }
}

}
