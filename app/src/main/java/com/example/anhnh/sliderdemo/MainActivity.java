package com.example.anhnh.sliderdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import MyObject.Film;
import anhnht.Component.Log_In;
import anhnht.Component.Movie_Information_Activity;

public class MainActivity extends AppCompatActivity {
    private static ViewPager viewPager;
    private SwipeAdapter swipeAdapter;
    private LinearLayout dotsLayout;
    private Context context;
    private TextView[] dots;
    int[] colorsInactive, colorsActive;
    private static String[] array_movies;
    private static boolean loggedIn = false;
    private TextView txtUserWelcome;
    private Button btnLogIn;
    private boolean check;

    private ArrayList<Film> ListofFilms = new ArrayList<>();

    private DatabaseReference mFilmReference;
    private FirebaseUser currentUser;
    private StorageReference mStorageRef;

    private void setupDB(){
        mFilmReference = FirebaseDatabase.getInstance().getReference().child("Films");
        mFilmReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    String id = data.getKey();
                    String name = data.child("Name").getValue().toString();
                    String Image = data.child("Picture").child("image1").getValue().toString();
                    String Descrip = data.child("Description").getValue().toString();
                    String Duration = data.child("Duration").getValue().toString();
                    String Producer = data.child("Producer").getValue().toString();
                    String Rating = data.child("Rating").getValue().toString();
                    Film film = new Film(id, name, Image, Descrip, Duration, Producer, Rating);
                    for (int i = 0; i < ListofFilms.size(); i++) {
                        if (id.toString().equals(ListofFilms.get(i).getId())) {
                                Film film1 = ListofFilms.get(i);
                                film1.setId(id);
                                film1.setName(name);
                                film1.setImage(Image);
                                film1.setDescrip(Descrip);
                                film1.setDuration(Duration);
                                film1.setProducer(Producer);
                                film1.setRating(Rating);
                                check = true;
                                break;
                        }
                    }
                    if (check){
                        check = false;
                    }else {
                        ListofFilms.add(film);
                    }
                    swipeAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupDB();
        init();
        //Set Action for view pager
        setViewPagerAction();
        //Set the log in status
        checkLogin();
        //Set onclick event
        onClickEvent();

    }

    /**
     * Function to add dot to view paper for scroll
     * */
    private void addBottomDots(Context context) {
        if (dotsLayout != null) {
            dotsLayout.removeAllViews();
        }
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(context);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dotsLayout.addView(dots[i]);
        }
    }

    /**
     * Set the color for the dots
     * */
    private void setActive(int position, int length, TextView[] dot) {
        for (int i = 0; i < dots.length; i++) {
            dots[i].setTextColor(colorsInactive[position]);
            if (dots.length > 0)
                dots[position].setTextColor(colorsActive[position]);
        }
    }

    /**
     * Init the txtUserWelcom and btnLogIn
     * Check the status of logged in to show userinfo
     * */
    private void checkLogin(){
        if(!loggedIn){
            txtUserWelcome.setVisibility(View.INVISIBLE);
            btnLogIn.setVisibility(View.VISIBLE);
            btnLogIn.setClickable(true);
            btnLogIn.setFocusable(true);
        }else{
            btnLogIn.setVisibility(View.INVISIBLE);
            btnLogIn.setClickable(false);
            btnLogIn.setFocusable(false);
            txtUserWelcome.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Set button Login on click
     * */
    private void onClickEvent(){
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logFunc = new Intent(MainActivity.this, Log_In.class);
                startActivityForResult(logFunc, 1);
            }
        });
        txtUserWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Code to view user info
            }
        });
    }

    /**
     * Init function, set ID, resources for all component
     * */
    private void init(){
        context = this.getApplicationContext();
        viewPager = (ViewPager) findViewById(R.id.view_paper);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        //Show slider
        array_movies = viewPager.getResources().getStringArray(R.array.movie_eng_name);
        swipeAdapter = new SwipeAdapter(this, ListofFilms);
        //swipeAdapter = new SwipeAdapter(this, array_movies);
        viewPager.setAdapter(swipeAdapter);
        //set color array for dots and dots array size, must be put before call addBottomDots to prevent null
        colorsInactive = viewPager.getResources().getIntArray(R.array.array_dot_inactive);
        colorsActive = viewPager.getResources().getIntArray(R.array.array_dot_active);
        dots = new TextView[colorsActive.length];
        //create dots at bottom
        addBottomDots(context);
        txtUserWelcome = (TextView) findViewById(R.id.txtUserWelcome);
        txtUserWelcome.setFocusable(false);
        btnLogIn = (Button) findViewById(R.id.btnLogIn);

    }

    /**
     * All action related to view paper (slider)
     * */
    private void setViewPagerAction(){

        //set action for dot state change
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setActive(position, 5, dots);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //Set the on click listener for view paper
        final GestureDetector tapGestureDetector = new GestureDetector(this, new TapGestureListener());
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                tapGestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });
    }

    /**
     * Waiting for response from activity
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //request code 1: Login
        if(requestCode==1 && resultCode==RESULT_OK){
            //Return user name for welcome
            Bundle bundle = data.getExtras();
            String username = bundle.getString("USERNAME");
            loggedIn=true;
            txtUserWelcome.setText("Welcome "+username);
            checkLogin();
        }else
            //request code 2: view info
        if(requestCode==2 && requestCode==RESULT_OK){
            //Code to handle in case of rename user
        }
    }

    /**
     * Getter setter
     * */
    public static ViewPager getViewPager() {
        return viewPager;
    }

    public static String[] getArray_movies() {
        return array_movies;
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(boolean loggedIn) {
        MainActivity.loggedIn = loggedIn;
    }

    /**
     * Class help on click on view paper
     * */
    class TapGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            int pos = viewPager.getCurrentItem();
            //System.out.println(pos);//return the current item position
            //System.out.println(array_movies[pos]);
            Intent info = new Intent(MainActivity.this, Movie_Information_Activity.class);
            Film chose = ListofFilms.get(pos);
            info.putExtra("Film", chose);
            startActivity(info);
            return false;
        }
    }
}
