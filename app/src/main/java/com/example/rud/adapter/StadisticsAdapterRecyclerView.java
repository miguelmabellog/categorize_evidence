package com.example.rud.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rud.R;
import com.example.rud.model.Stadistics;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StadisticsAdapterRecyclerView extends RecyclerView.Adapter<StadisticsAdapterRecyclerView.StadisticsviewHolder>{
    private ArrayList<Stadistics> estadisticas;
    private int  resorcues;
    private Activity activity;

    public StadisticsAdapterRecyclerView(ArrayList<Stadistics> estadisticas, int resorcues, Activity activity) {
        this.estadisticas = estadisticas;
        this.resorcues = resorcues;
        this.activity = activity;
    }

    @NonNull
    @Override
    public StadisticsviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resorcues,parent, false);
        return new StadisticsviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StadisticsviewHolder holder, int position) {
        final Stadistics estadistica = estadisticas.get(position);
        holder.categoriaE.setText(estadistica.getCategoriaE());
        holder.totalE.setText(estadistica.getTotalE());


        Picasso.with(activity).load(estadistica.getPictureE()).into(holder.pictureE);

    }

    @Override
    public int getItemCount() {
        return estadisticas.size() ;
    }

    public class StadisticsviewHolder extends RecyclerView.ViewHolder {
        private ImageView pictureE;
        private TextView categoriaE;
        private TextView totalE;


        public StadisticsviewHolder(@NonNull View itemView) {
            super(itemView);
            pictureE=itemView.findViewById(R.id.estadisticaCardImage);
            categoriaE=itemView.findViewById(R.id.estadisticaCardCategoria);
            totalE=itemView.findViewById(R.id.estadisticaCardTotal);

        }
    }
}
