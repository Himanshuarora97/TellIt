package com.example.makroid.tellit.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by himanshu on 30/3/17.
 */

public class RecyclerSpace extends RecyclerView.ItemDecoration {
    private int space;
    public RecyclerSpace(int space){
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        Log.e(TAG, "getItemOffsets: " + position );
        outRect.top = space;
        if(position % 2 == 0) {
            outRect.left = space;
        }
        else
        {
            outRect.left = space;
            outRect.right = space;
        }
    }
}
