package org.snowcorp.firebase.auth;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HospitalDetail extends AppCompatActivity {

    TextView hospital_name,hospital_price,hospital_description;
    ImageView hospital_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;


    String hospitalId="";

    FirebaseDatabase database;
    DatabaseReference hospitals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_detail);

        //Firebase
        database= FirebaseDatabase.getInstance();
        hospitals = database.getReference("hospitals");


        //Init View
        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
        btnCart = (FloatingActionButton)findViewById(R.id.btncart);

        hospital_description= (TextView)findViewById(R.id.hospital_description);
        hospital_name= (TextView)findViewById(R.id.hospital_name);
        hospital_price= (TextView)findViewById(R.id.hospital_price);
        hospital_image= (ImageView)findViewById(R.id.img_hospital);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        if(getIntent() !=null) {
            hospitalId = getIntent().getStringExtra("hospitalId");


        }
        if(!hospitalId.isEmpty())
        {
            getDetailhospital(hospitalId);

        }

    }

    private void getDetailhospital(String hospitalId) {

        hospitals.child(hospitalId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Hospital hospital = dataSnapshot.getValue(Hospital.class);
                try {
                    //Set image
                    Picasso.get().load(hospital.getImage()).into(hospital_image);
                }
                catch(NullPointerException ignored)
                {
                }


                try {
                    collapsingToolbarLayout.setTitle(hospital.getName());
                    hospital_price.setText(hospital.getPrice());

                    hospital_name.setText(hospital.getName());

                    hospital_description.setText(hospital.getDescription());
                }
                catch(NullPointerException ignored)
                {
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
