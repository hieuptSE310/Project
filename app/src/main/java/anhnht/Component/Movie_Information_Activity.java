package anhnht.Component;

import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anhnh.sliderdemo.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import MyObject.Film;

public class Movie_Information_Activity extends AppCompatActivity {

    ImageView imageView;
    //private TypedArray image;
    TextView movie_des, movieName;
    Button btnTrailer;

    private void setupRef(){
        //mStorageRef = Fireb
    }

    private void setupWidget(){
        imageView= (ImageView) findViewById(R.id.movie_view);
        movie_des = (TextView) findViewById(R.id.txtMovieDes);
        movieName = (TextView) findViewById(R.id.txtMoiveName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie__information_);
        setupWidget();
        final Film film = (Film) getIntent().getSerializableExtra("Film");
        //Set movie poster
        //image = getResources().obtainTypedArray(R.array.movie_poster);
        Picasso.with(this.getApplicationContext()).load(film.getImage()).into(imageView);
        //Set movie description, name
        movie_des.setText(film.getDescrip());
        movieName.setText(film.getName());
        movie_des.setMovementMethod(new ScrollingMovementMethod());
        //Set trailer button
        btnTrailer = (Button) findViewById(R.id.btnTrailer);
        btnTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String url = getResources().getStringArray(R.array.movie_trailer)[pos];
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://")));
            }
        });


    }
}
