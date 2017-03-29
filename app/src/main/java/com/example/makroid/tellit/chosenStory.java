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
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class chosenStory extends AppCompatActivity {

    private static final String TAG = chosenStory.class.getClass().getName();
    @BindView(R.id.app_bar_story)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar_story)
    Toolbar toolbar;
    @BindView(R.id.rvrank)
    RecyclerView recyclerView;
    @BindView(R.id.toolImage)
    ImageView toolImageView;
    private static int  SCROLL =  0 ;

    // RecyclerView variables
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    //For unbind Butterknife
    Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chosen_story);
        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Log.e(TAG, "onCreate: "  + appBarLayout.getHeight() + "   " + appBarLayout.getTop() + "  "+appBarLayout.getTotalScrollRange() );

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

//            boolean a=true;
//            int scroll=-1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//               if (scroll==-1){
//
                   SCROLL=appBarLayout.getTotalScrollRange();
//               }

                if (verticalOffset * -1 >= SCROLL-150){
                   collapsingToolbarLayout.setTitle("Story Title Here");
                   collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
                   collapsingToolbarLayout.setCollapsedTitleTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
               }
               else
                {
                    collapsingToolbarLayout.setTitle("");
                }
//
//                else if (a){
//
////                   TranslateAnimation translateAnimation=new TranslateAnimation(-200,0,0,0);
////                  // Animation a1=new AlphaAnimation(0,1);
////                  // a1.setDuration(500);
////                   translateAnimation.setDuration(500);
////                   AnimationSet anim=new AnimationSet(true);
////                   anim.addAnimation(translateAnimation);
////                   collapsingToolbarLayout.startAnimation(anim);
//                   collapsingToolbarLayout.setTitle(" ");
//
//                    a=false;
//               }


            }
        });


        layoutManager = new LinearLayoutManager(chosenStory.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(chosenStory.this);
        //   recyclerView.findViewHolderForAdapterPosition(45);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
