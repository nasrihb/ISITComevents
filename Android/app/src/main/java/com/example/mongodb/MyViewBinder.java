package com.example.mongodb;



        import android.graphics.Bitmap;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.RatingBar;
        import android.widget.TextView;
        import android.widget.SimpleAdapter.ViewBinder;

public class MyViewBinder implements ViewBinder {

    @Override
    public boolean setViewValue(View view, Object mylist,String textRepresentation) {
        if( (view instanceof ImageView) & (mylist instanceof Bitmap) ) {
            ImageView iv = (ImageView) view;
            Bitmap bm = (Bitmap) mylist;
            iv.setImageBitmap(bm);
            return true;
        }

        return false;
    }


}

