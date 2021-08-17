package com.example.softxpertt;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

public class CustomList extends ArrayAdapter{
    private ArrayList<String> bigTexts;
    private ArrayList<String> smallTexts;
    private ArrayList<Boolean> Text3;
    private ArrayList<String> imageId;
    private Activity context;

    public CustomList(Activity context, ArrayList<String> bigTexts, ArrayList<String> smallTexts, ArrayList<Boolean> Text3, ArrayList<String> imageId) {
        super(context, R.layout.row_item, bigTexts);
        this.context = context;
        this.bigTexts = bigTexts;
        this.smallTexts = smallTexts;
        this.Text3 = Text3;
        this.imageId = imageId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView == null)
            row = inflater.inflate(R.layout.row_item, null, true);

        TextView textView1 = row.findViewById(R.id.bigText);
        TextView textView2 = row.findViewById(R.id.smallText);
        TextView textView3 = row.findViewById(R.id.Text3);
        ImageView imageView = row.findViewById(R.id.imageViewFlag);

        textView1.setText(bigTexts.get(position));
        textView2.setText(smallTexts.get(position));

        if(Text3.get(position))
            textView3.setText("Used");
        else
            textView3.setText("New");


        if(!imageId.get(position).equals(""))
            new DownloadImageFromInternet(imageView).execute(imageId.get(position));

        //imageView.setImageResource(imageId.get(position));
        return  row;
    }


    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView=imageView;
        }
        protected Bitmap doInBackground(String... urls) {
            String imageURL=urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage=BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bimage;
        }
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
