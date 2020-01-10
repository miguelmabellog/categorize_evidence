package com.example.rud.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rud.Constants;
import com.example.rud.R;
import com.example.rud.details.PhotoDetailActivity;
import com.example.rud.details.FolderDetailActivity;
import com.example.rud.details.VideoDetailActivity;
import com.example.rud.model.Evidence;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EvidenceAdapterRecyclerView extends RecyclerView.Adapter<EvidenceAdapterRecyclerView.EvidenceViewHolder>{
    private ArrayList<Evidence> evidences;
    private int  resorcues;
    private Activity activity;

    public EvidenceAdapterRecyclerView(ArrayList<Evidence> evidences, int resorcues, Activity activity) {
        this.evidences = evidences;
        this.resorcues = resorcues;
        this.activity = activity;
    }

    @NonNull
    @Override
    public EvidenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resorcues,parent, false);
        return new EvidenceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EvidenceViewHolder holder, int position) {
        final Evidence evidence=evidences.get(position);
        holder.categoryCard.setText(evidence.getCategory());
        holder.timeCard.setText(evidence.getDate());
        holder.placeCard.setText(evidence.getPlace());

        if(evidence.getType().equals("video")){
            String urlVideo = getURLForResource(R.drawable.film_and_vid);
            Picasso.with(activity).load(urlVideo).into(holder.imageViewCard);
        }else{
            if(evidence.getType().equals("carpeta")){
                String urlVideo = getURLForResource(R.drawable.carpetalogo);
                Picasso.with(activity).load(urlVideo).into(holder.imageViewCard);
            }
            else{

                Picasso.with(activity).load(evidence.getReferenceEvidence()).into(holder.imageViewCard);
            }

        }


        holder.imageViewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(evidence.getType().equals(Constants.TYPE_PHOTO)){
                    Intent intent=new Intent(activity, PhotoDetailActivity.class);
                    intent.putExtra("envia_imagen",evidence.getReferenceEvidence());
                    intent.putExtra("envia_id",evidence.getId());
                    intent.putExtra("envia_fecha",evidence.getDate());
                    intent.putExtra("envia_categoria",evidence.getCategory());
                    intent.putExtra("envia_lugar",evidence.getPlace());
                    intent.putExtra("envia_hora",evidence.getTime());
                    intent.putExtra("envia_descripcion",evidence.getDescription());
                    activity.startActivity(intent);
                }
                else{
                    if(evidence.getType().equals(Constants.TYPE_VIDEO)){
                        Intent intent=new Intent(activity, VideoDetailActivity.class);
                        intent.putExtra("envia_imagen",evidence.getReferenceEvidence());
                        intent.putExtra("envia_id",evidence.getId());
                        intent.putExtra("envia_fecha",evidence.getDate());
                        intent.putExtra("envia_categoria",evidence.getCategory());
                        intent.putExtra("envia_lugar",evidence.getPlace());
                        intent.putExtra("envia_hora",evidence.getTime());
                        intent.putExtra("envia_descripcion",evidence.getDescription());
                        activity.startActivity(intent);

                    }

                    if(evidence.getType().equals(Constants.TYPE_FOLDER)){
                        Intent intent=new Intent(activity, FolderDetailActivity.class);
                        intent.putExtra("envia_imagen",evidence.getReferenceEvidence());
                        intent.putExtra("envia_id",evidence.getId());
                        intent.putExtra("envia_fecha",evidence.getDate());
                        intent.putExtra("envia_categoria",evidence.getCategory());
                        intent.putExtra("envia_lugar",evidence.getPlace());
                        intent.putExtra("envia_hora",evidence.getTime());
                        intent.putExtra("envia_descripcion",evidence.getDescription());
                        activity.startActivity(intent);

                    }


                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return evidences.size() ;
    }

    public class EvidenceViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageViewCard;
        private TextView categoryCard;
        private TextView timeCard;
        private TextView placeCard;


        public EvidenceViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCard =itemView.findViewById(R.id.evidenceCardImage);
            categoryCard =itemView.findViewById(R.id.evidenceCardCategory);
            timeCard=itemView.findViewById(R.id.evidenceCardDate);
            placeCard =itemView.findViewById(R.id.evidenceCardPlace);
        }

    }
    public String getURLForResource (int resourceId) {

        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }

}
