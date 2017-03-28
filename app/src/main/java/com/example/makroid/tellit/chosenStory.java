package com.example.makroid.tellit;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

/**
 * Created by joginderpal on 27-03-2017.
 */
public class chosenStory extends AppCompatActivity {

  //  @BindView(R.id.app_bar_story)        //Gives Error
    AppBarLayout appBarLayout;
   // @BindView(R.id.toolbar_layout)          //Gives Error
    CollapsingToolbarLayout collapsingToolbarLayout;
   // @BindView(R.id.toolbar_story)        //Gives Error
    Toolbar toolbar;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chosen_story);
        recyclerView = (RecyclerView) findViewById(R.id.rvrank);
        appBarLayout= (AppBarLayout) findViewById(R.id.app_bar_story);
        collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbar= (Toolbar) findViewById(R.id.toolbar_story);
        setSupportActionBar(toolbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            boolean a=true;
            int scroll=-1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

               if (scroll==-1){

                   scroll=appBarLayout.getTotalScrollRange();
               }

                else if (scroll+verticalOffset==0){
                   collapsingToolbarLayout.setTitle("Story Title");
                   collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
                   collapsingToolbarLayout.setCollapsedTitleTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
                   a=true;
               }

                else if (a){

                   TranslateAnimation translateAnimation=new TranslateAnimation(-200,0,0,0);
                  // Animation a1=new AlphaAnimation(0,1);
                  // a1.setDuration(500);
                   translateAnimation.setDuration(500);
                   AnimationSet anim=new AnimationSet(true);
                   anim.addAnimation(translateAnimation);
                   collapsingToolbarLayout.startAnimation(anim);
                   collapsingToolbarLayout.setTitle(" ");

                    a=false;
               }


            }
        });


        layoutManager = new LinearLayoutManager(chosenStory.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(chosenStory.this);
        //   recyclerView.findViewHolderForAdapterPosition(45);
        recyclerView.setAdapter(adapter);

    }
}
