package anhnht.Component;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.anhnh.sliderdemo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import MyObject.Cinemas;
import MyObject.Film;

public class Booking_Ticket extends AppCompatActivity {

    private Spinner Cinemas, Seats, Times;
    private Button Booking;
    private DatabaseReference mFilmRef, mCineRef, mRoomRef;
    private Film film;
    private ArrayList<String> ListCinemas = new ArrayList<>();
    private ArrayList<String> ListSlots = new ArrayList<>();
    private ArrayList<String> ListSeats = new ArrayList<>();
    private ArrayAdapter<String> CinemaAdater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking__ticket);
        film = (Film) getIntent().getSerializableExtra("Film");
        loadCinemasFromFirebase();
        setupWidget();

    }

    private void loadCinemasFromFirebase(){

        mFilmRef = FirebaseDatabase.getInstance().getReference().child("Films").child(film.getId()).child("Cinemas");
        mFilmRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    String Cinema = data.getKey();
                    ListCinemas.add(Cinema);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void setupWidget(){
        Cinemas = (Spinner) findViewById(R.id.spinCinema);
        Seats = (Spinner)findViewById(R.id.spinSeat);
        Times = (Spinner)findViewById(R.id.spinTime);
        Booking = (Button) findViewById(R.id.btnBookAction);
        CinemaAdater = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ListCinemas);
        CinemaAdater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Cinemas.setAdapter(CinemaAdater);
        Cinemas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),"--------"+adapterView.getItemAtPosition(i).toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }



}
