package com.example.makroid.tellit.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.makroid.tellit.R;
import com.example.makroid.tellit.activities.chosenStoryActivity;
import com.example.makroid.tellit.models.recent_story;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by himanshu on 25/3/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder>{

    LayoutInflater inflater;
    Context c;
    List<recent_story> recent_storyList;
    Typeface typeface;
    public RecyclerAdapter(Context c, List<recent_story> recent_storyList, Typeface typeface) {
        this.c=c;
        inflater = LayoutInflater.from(c);
        this.recent_storyList=recent_storyList;
        this.typeface=typeface;
    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.story_card_view,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        // Update profile image using Picasso
     //   Picasso.with(c).load(R.drawable.image5).transform(new CircularImage()).into(holder.profileImage);


         holder.title.setText(recent_storyList.get(position).getTitle());
        holder.title.setTypeface(typeface);
      //  Glide.with(c).load("http://122.176.236.211:3333/"+recent_storyList.get(position).getInitiator_image_path()).fit().into(holder.initiatorImage);

        holder.category.setText(recent_storyList.get(position).getCategory());
        // holder.date.setText(recent_storyList.get(position).getDate_created());
        Picasso.with(c).load("http://122.176.236.211:3333/"+recent_storyList.get(position).getImage_path()).fit().into(holder.image_path);

    }

    @Override
    public int getItemCount() {
        return recent_storyList.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        private ImageView initiatorImage;
        private TextView title;
        private TextView category;
        private TextView date;
        private ImageView image_path;
        public MyHolder(final View itemView) {
            super(itemView);
            initiatorImage= (ImageView) itemView.findViewById(R.id.initiator_image);
            title= (TextView) itemView.findViewById(R.id.title_name);
            category= (TextView) itemView.findViewById(R.id.category_name);
            date= (TextView) itemView.findViewById(R.id.time_date);
            image_path= (ImageView) itemView.findViewById(R.id.image_path);
            int width=image_path.getWidth();
            int height=image_path.getHeight();
            final int y=image_path.getTop();
            final int x=image_path.getLeft();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos=getAdapterPosition();
                    Intent i=new Intent(c,chosenStoryActivity.class);
                    i.putExtra("x",x);
                    i.putExtra("y",y);
                    i.putExtra("image_url",recent_storyList.get(pos).getImage_path());
                    ActivityOptionsCompat options= ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) c,image_path,"profile");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        c.startActivity(i,options.toBundle());
                    }
                }
            });

        }
    }

}
