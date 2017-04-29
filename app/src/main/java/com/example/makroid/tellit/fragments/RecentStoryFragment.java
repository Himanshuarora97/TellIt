package com.example.makroid.tellit.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.makroid.tellit.R;
import com.example.makroid.tellit.adapters.RecyclerAdapter;
import com.example.makroid.tellit.utils.RecyclerSpace;
import com.example.makroid.tellit.models.recent_story;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

public class RecentStoryFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    List<recent_story> recentStory;
    static boolean isPortrait = false;
    RequestQueue rq;

    Typeface typeface;

    @Nullable
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @Nullable
    @BindView(R.id.drecycler)
    RecyclerView mRecyclerView;

    RecyclerAdapter adapter;
    View view;

    public RecentStoryFragment() {

    }

    public Fragment newInstance(String param1) {
        RecentStoryFragment fragment = new RecentStoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rq= Volley.newRequestQueue(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.story_list,container,false);
        ButterKnife.bind(this,view);
        typeface=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Pacifico.ttf");
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest("http://122.176.236.211:3333/noReqLgn/stories/getRecTopVwdTopVtdRanStories?rec_topVwd_topVtd_ran=rec&category=all&offset=0&count=10",

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        recentStory=new ArrayList<recent_story>();

                        for (int i=0;i<response.length();i++){

                            try {
                                JSONObject jsonObject=response.getJSONObject(i);
                                recent_story rec=new recent_story();
                                rec.setId(jsonObject.getInt("id"));
                                rec.setTitle(jsonObject.getString("title"));
                                rec.setCategoryId(jsonObject.getInt("categoryId"));
                                rec.setImage_path(jsonObject.getString("imagePath"));
                                rec.setInitiator_id(jsonObject.getInt("initiatorId"));
                                rec.setDate_created(jsonObject.getString("dateCreated"));
                                rec.setViews(jsonObject.getInt("views"));
                                rec.setVotes(jsonObject.getInt("votes"));
                                rec.setCategory(jsonObject.getString("category"));
                                rec.setInitiator_full_name("initiatorFullName");
                                rec.setInitiator_image_path("initiatorImagePath");
                                recentStory.add(rec);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        setRecyclerView(isPortrait);

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        );

        rq.add(jsonArrayRequest);

    //
        return view;
    }

    private void setRecyclerView(boolean is_Portrait) {
        Log.e(TAG, "setRecyclerView: " + is_Portrait );
        adapter = new RecyclerAdapter(getActivity(),recentStory,typeface);
        if(mRecyclerView == null) {
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        else {
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            mRecyclerView.addItemDecoration(new RecyclerSpace(20));
        }
    }
}
