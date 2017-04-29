package com.example.makroid.tellit.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makroid.tellit.utils.CircularImage;
import com.example.makroid.tellit.Login;
import com.example.makroid.tellit.R;
import com.example.makroid.tellit.adapters.ViewPagerAdapter;
import com.example.makroid.tellit.fragments.RecentStoryFragment;
import com.example.makroid.tellit.fragments.TopStoryFragment;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TITLE = "Tell It";
    private static final String TAG = MainActivity.class.getClass().getName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    ViewPagerAdapter adapter;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.navView)
    NavigationView navView;

    //Navigation views

    ImageView imageView;
    TextView textView;
    private FirebaseAuth mAuth;
    GoogleApiClient mGoogleApiClient;
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    int a = 0;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    DrawerLayout dl;
    // Variables
    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar();
        initViewPager();
        setRoundImage(0);
        navView.setNavigationItemSelectedListener(this);

        View view = navView.getHeaderView(0);
        imageView = (ImageView) view.findViewById(R.id.profile_image);
        textView = (TextView) view.findViewById(R.id.username);
        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {

                    //  startActivity(new Intent(MainActivity.this,Login.class));
                } else {


                }


            }
        };


    }

    private void setRoundImage(int a) {
        View view = navView.getHeaderView(0);
        imageView = (ImageView) view.findViewById(R.id.profile_image);
        textView = (TextView) view.findViewById(R.id.username);
        //   a=getIntent().getExtras().getInt("link");

        // For demo
        Picasso.with(this).load(R.drawable.image5).transform(new CircularImage()).into(imageView);
        textView.setText("Jackson Parker");

        // demo end


//        if (a==1) {
//            imageView.setVisibility(View.VISIBLE);
//            textView.setText(getIntent().getExtras().getString("name"));
//            Picasso.with(this).load(getIntent().getExtras().getString("photo")).transform(new CircularImage()).into(imageView);
//        }
//        else if (a==0){
//
//            textView.setText("Login");
//            imageView.setVisibility(View.INVISIBLE);
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    startActivity(new Intent(MainActivity.this,Login.class));
//
//                }
//            });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
// Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

    }


    private void initViewPager() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RecentStoryFragment().newInstance("hello"), "Recent Stories");
        adapter.addFragment(new TopStoryFragment().newInstance("hello"), "Top Stories");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(TITLE);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int state = 0;
        switch (item.getItemId()) {
            case R.id.home:
                state = 1;
                break;
            case R.id.log_fragment:
                startActivity(new Intent(MainActivity.this, Login.class));
                state = 2;
                break;
            case R.id.setting_fragment:
                state = 3;
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
                break;
            case R.id.share_fragment:
                state = 4;

                dl.closeDrawers();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        final Dialog d = new Dialog(MainActivity.this, android.R.style.Theme_Translucent_NoTitleBar);// necessary to start animation in dialog box
                        d.setContentView(R.layout.share_dilogue);
                        d.getWindow().setWindowAnimations(R.style.dialog_animation);

                        ImageButton facebook = (ImageButton) d.findViewById(R.id.fb);

                        ImageButton whatsapp = (ImageButton) d.findViewById(R.id.whatsapp);
                        ImageButton other_sharing = (ImageButton) d.findViewById(R.id.other_sharing);
                        TextView close_share = (TextView) d.findViewById(R.id.close_share);

                        facebook.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                FacebookSdk.sdkInitialize(getApplicationContext());

                                callbackManager = CallbackManager.Factory.create();

                                List<String> permissionNeeds = Arrays.asList("publish_actions");


                                LoginManager.getInstance().logInWithPublishPermissions(MainActivity.this, permissionNeeds);

                                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                                    @Override
                                    public void onSuccess(LoginResult loginResult) {
                                        sharePhotoToFacebook();
                                    }

                                    @Override
                                    public void onCancel() {
                                        System.out.println("onCancel");
                                    }

                                    @Override
                                    public void onError(FacebookException exception) {
                                        System.out.println("onError");
                                    }
                                });
                            }

                        });


                        whatsapp.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                                whatsappIntent.setType("text/plain");
                                whatsappIntent.setPackage("com.whatsapp");
                                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Passing Story");
                                MainActivity.this.startActivity(whatsappIntent);


                            }
                        });


                        other_sharing.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                                whatsappIntent.setType("text/plain");
                                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Passing Story");
                                MainActivity.this.startActivity(whatsappIntent);


                            }
                        });


                        close_share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                d.getWindow().getAttributes().windowAnimations = R.anim.share_dialog_animation;   // showing particular animation

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        d.dismiss();
                                    }
                                }, 500);
                            }
                        });//  close share dialog box


                        d.show();
                    }
                }, 800);

                break;
            case R.id.rate_fragment:
                state = 5;
                break;
            case R.id.feedback_fragment:
                state = 6;
                break;
        }
        Toast.makeText(MainActivity.this, state + " clicked ", Toast.LENGTH_SHORT).show();
        return true;
    }
 /*   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Picasso.with(this).load(uri).transform(new CircularImage()).into(imageView);
                // Log.d(TAG, String.valueOf(bitmap));
        }
    }*/


    private void sharePhotoToFacebook() {
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .setCaption("Give me my codez or I will ... you know, do that thing you don't like!")
                .build();

        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        ShareApi.share(content, null);

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && responseCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            CircularImage image = new CircularImage();
            if(image!=null)
            Picasso.with(this).load(uri).transform(image).into(imageView);
        }

        // For  Facebook
//        callbackManager.onActivityResult(requestCode, responseCode, data);
    }


}


