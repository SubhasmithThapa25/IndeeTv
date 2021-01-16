package com.subhasmith.indee.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.subhasmith.indee.R;
import com.subhasmith.indee.model.Data;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity context;
    ArrayList<Data> testDataArrayList;

    public RecyclerViewAdapter(Activity context, ArrayList<Data> testDataArrayList) {
        this.context = context;
        this.testDataArrayList = testDataArrayList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new RecyclerViewViewHolder(rootView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int reqWidth = 100, reqHeight = 100;
        Data testData = testDataArrayList.get(position);
        RecyclerViewViewHolder viewHolder = (RecyclerViewViewHolder) holder;


        if (!testData.getName().isEmpty()){
            viewHolder.txtView_title.setText(testData.getName());
        }else{
            viewHolder.txtView_title.setText("Not Available");
        }
        if (!testData.getDescription().isEmpty()){
            viewHolder.txtView_description.setText(testData.getDescription());
        }else{
            viewHolder.txtView_description.setText("Not Available");
        }

        if (!testData.getVideo_duration().isEmpty()){
            viewHolder.txtView_duration.setText(testData.getVideo_duration());
        }else{
            viewHolder.txtView_duration.setText("Not Available");
        }

        if (!testData.getPayment_plan().isEmpty()){
            viewHolder.txtView_plan.setText(testData.getPayment_plan());
        }else{
            viewHolder.txtView_plan.setText("Not Available");
        }


        viewHolder.txtView_release_year.setText(testData.getRelease_year() + "");

        if (!testData.getType().isEmpty()){
            viewHolder.txtView_type.setText(testData.getType());
        }else{
            viewHolder.txtView_type.setText("Not Available");
        }

        if (!testData.getCreated_on().isEmpty()) {
            java.util.Date date = Date.from(Instant.parse(testData.getCreated_on()));
            String createdOn = new SimpleDateFormat("dd.MM.yyyy").format(date);
            viewHolder.txtView_created_on.setText(createdOn);


        }
        if (!testData.getUpdated_on().isEmpty()) {

            java.util.Date date = Date.from(Instant.parse(testData.getUpdated_on()));
            String updatedOn = new SimpleDateFormat("dd.MM.yyyy").format(date);
            viewHolder.txtView_updated_on.setText(updatedOn);


        }

        if (!testData.getShortDescription().isEmpty()){
            viewHolder.txtView_short_desc.setText(testData.getShortDescription());
        }else{
            viewHolder.txtView_short_desc.setText("Not Available");
        }


        String imageName = testData.getPosterLink().substring(0,testData.getPosterLink().indexOf("."));
        MyAsyncTask myAsyncTask = new MyAsyncTask(viewHolder.imgView_icon, context, context.getResources(), imageName);
        myAsyncTask.execute();


    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    @Override
    public int getItemCount() {
        return testDataArrayList.size();
    }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView_icon;
        TextView txtView_title;
        TextView txtView_description;
        TextView txtView_plan;
        TextView txtView_release_year;
        TextView txtView_duration;
        TextView txtView_type;
        TextView txtView_created_on;
        TextView txtView_updated_on;
        TextView txtView_short_desc;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView_icon = itemView.findViewById(R.id.imgView_icon);
            txtView_title = itemView.findViewById(R.id.txtView_title);
            txtView_description = itemView.findViewById(R.id.txtView_description);
            txtView_plan = itemView.findViewById(R.id.plan);
            txtView_duration = itemView.findViewById(R.id.duration);
            txtView_release_year = itemView.findViewById(R.id.year);
            txtView_type = itemView.findViewById(R.id.type);
            txtView_created_on = itemView.findViewById(R.id.created_on);
            txtView_updated_on = itemView.findViewById(R.id.updated_on);
            txtView_short_desc = itemView.findViewById(R.id.short_desc);


        }
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, Bitmap> {

        ImageView imageView;
        Context context;

        Resources res;
        String resId;

        public MyAsyncTask(ImageView imageView, Context context,
                           Resources res, String resId) {
            super();
            this.imageView = imageView;
            this.context = context;

            this.res = res;
            this.resId = resId;
        }


        @Override
        protected Bitmap doInBackground(String... strings) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            int resIds = context.getResources().getIdentifier(context.getPackageName()+":drawable/"+resId, null, null);

            BitmapFactory.decodeResource(res, resIds, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, 100, 100);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeResource(res, resIds, options);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
// doInBackground() et al.
    }
}
