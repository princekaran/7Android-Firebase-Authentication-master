package org.snowcorp.firebase.auth.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.snowcorp.firebase.auth.Interface.ItemClickListener;
import org.snowcorp.firebase.auth.R;

public class HospitalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView hospital_name;
    public ImageView hospital_image;
    Context context;


    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public HospitalViewHolder(@NonNull View itemView) {
        super(itemView);

        hospital_name = (TextView)itemView.findViewById(R.id.hospital_name);
        hospital_image = (ImageView)itemView.findViewById(R.id.hospital_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}
