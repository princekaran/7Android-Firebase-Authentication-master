package org.snowcorp.firebase.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.snowcorp.firebase.auth.Interface.ItemClickListener;
import org.snowcorp.firebase.auth.ViewHolder.HospitalViewHolder;

public class HospitalList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference HospitalList;

    String categoryId = "";
    FirebaseRecyclerAdapter<Hospital, HospitalViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);

        //Firebase
        database = FirebaseDatabase.getInstance();
        HospitalList = database.getReference("Hospital");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_hospital);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        //Get Intent here

        if (getIntent() != null)

            categoryId = getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty() && categoryId != null) {
            loadListHospital(categoryId);
        }
    }

    private void loadListHospital(String categoryId) {

        adapter = new FirebaseRecyclerAdapter<Hospital, HospitalViewHolder>(Hospital.class,
                R.layout.hospital_item,
                HospitalViewHolder.class,
                HospitalList.orderByChild("MenuId").equalTo(categoryId)  // like :select  * Hospitals from where Hospitals where MenuId=
        ) {
            @Override
            protected void populateViewHolder(HospitalViewHolder viewHolder, Hospital model, int position) {

                viewHolder.hospital_name.setText(model.getName());
                Picasso.get().load(model.getImage()).into(viewHolder.hospital_image);

                final Hospital local = model;
               viewHolder.setItemClickListener(new ItemClickListener() {
                   @Override
                   public void onClick(View view, int position, boolean isLongClick) {
                       // starting new aactivity

                       Intent HospitalDetail = new Intent(HospitalList.this, HospitalDetail.class);
                       HospitalDetail.putExtra("HospitalId",adapter.getRef(position).getKey()); //send Hospital id to new acttivity
                       startActivity(HospitalDetail);
                   }
               });

            }
        };

        //Set Adapter

        recyclerView.setAdapter(adapter);
    }
}

