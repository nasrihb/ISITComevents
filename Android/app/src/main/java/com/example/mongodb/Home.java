package com.example.mongodb;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    private ImageView previousbtn, nextbtn;
    private ImageSwitcher imgsw;
    private int[] images = {R.drawable.img1, R.drawable.img3, R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7};
    private int position = 0;


    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ImageView  google = (ImageView)view.findViewById(R.id.google);
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View google = LayoutInflater.from(getActivity())
                        .inflate(R.layout.activity_google_club,null);
                new AlertDialog.Builder(getActivity())
                        .setView(google)
                        .setCancelable(true)
                        .show();
            }
        });
        ImageView  securinet = (ImageView)view.findViewById(R.id.securinet);
        securinet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View securinet = LayoutInflater.from(getActivity())
                        .inflate(R.layout.activity_securinet,null);
                new AlertDialog.Builder(getActivity())
                        .setView(securinet)
                        .setCancelable(true)
                        .show();
            }
        });
        ImageView  tunivision = (ImageView)view.findViewById(R.id.tunivision);
        tunivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View tunivision = LayoutInflater.from(getActivity())
                        .inflate(R.layout.activity_tunivision,null);
                new AlertDialog.Builder(getActivity())
                        .setView(tunivision)
                        .setCancelable(true)
                        .show();
            }
        });
        ImageView  gaming = (ImageView)view.findViewById(R.id.gaming);
        gaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View gaming = LayoutInflater.from(getActivity())
                        .inflate(R.layout.activity_gaming,null);
                new AlertDialog.Builder(getActivity())
                        .setView(gaming)
                        .setCancelable(true)
                        .show();
            }
        });
        ImageView  music = (ImageView)view.findViewById(R.id.music);
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View music = LayoutInflater.from(getActivity())
                        .inflate(R.layout.activity_music,null);
                new AlertDialog.Builder(getActivity())
                        .setView(music)
                        .setCancelable(true)
                        .show();
            }
        });
        previousbtn = (ImageView) view.findViewById(R.id.btnPrevious);
        nextbtn = (ImageView)view.findViewById(R.id.btnNext);
        imgsw = (ImageSwitcher) view.findViewById(R.id.imgSw);
        imgsw.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imgVw= new ImageView(getActivity());
                imgVw.setImageResource(images[position]);
                return imgVw;
            }
        });
        imgsw.setInAnimation(getActivity(), android.R.anim.slide_in_left);
        imgsw.setOutAnimation(getActivity(), android.R.anim.slide_out_right);
        previousbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position>0) {
                    position--;
                    imgsw.setImageResource(images[position]);}
                else {
                    Toast.makeText(getContext(), "No Previous Image", Toast.LENGTH_SHORT).show();
                    return;
                }  }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position<images.length) {
                    imgsw.setImageResource(images[position]);
                    position++; }
                else if(position>=images.length){
                    Toast.makeText(getContext(), "No Next Image", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        return view;
    }

}
