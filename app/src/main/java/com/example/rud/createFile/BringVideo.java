package com.example.rud.createFile;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.MediaController;
import android.widget.TimePicker;
import android.widget.VideoView;

import com.example.rud.Constants;
import com.example.rud.R;
import com.example.rud.TimePickerFragment;
import com.example.rud.context.RudAplication;
import com.example.rud.model.Evidence;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class BringVideo extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private static final String TAG="BringVideo";
    private String videoUrl;
    private VideoView videoViewBring;
    private String videoBringPath;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private RudAplication app;

    private Button subirFoto5;

    private TextInputEditText postCategoria;
    private TextInputEditText postfecha;
    private TextInputEditText posthora;
    private TextInputEditText postlugar;
    private TextInputEditText postdescripcion;
    private Uri uri;

    private ArrayList<Integer> listDatos=new ArrayList<>();
    private boolean[] marcados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bring_video);


        videoViewBring=findViewById(R.id.bringVideo);
        subirFoto5=findViewById(R.id.subirFoto5);

        postlugar=findViewById(R.id.PostLugar5);
        postCategoria=findViewById(R.id.postCategoria5);
        postfecha=findViewById(R.id.PostFecha5);
        postdescripcion=findViewById(R.id.PostDescripcion5);
        posthora=findViewById(R.id.PostHora5);


        //videoViewBring.seekTo(1);

        uri= (Uri) getIntent().getExtras().get(Intent.EXTRA_STREAM);
        videoViewBring.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);

        videoViewBring.setMediaController(mediaController);
        mediaController.setAnchorView(videoViewBring);

        //videoViewBring.requestFocus();

        videoViewBring.start();

        app=(RudAplication) getApplicationContext();


        videoBringPath="file:"+((Uri) getIntent().getExtras().get(Intent.EXTRA_STREAM)).getPath();


        databaseReference= FirebaseDatabase.getInstance().getReference();


        storageReference=app.getStorageReference();

        subirFoto5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Toast.makeText(BringImage.this, photoBringPath, Toast.LENGTH_SHORT).show();
                    upLoadPhoto3();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });



        postCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abre_dialogo(v);
            }
        });
        postfecha.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                dialogo_fecha(v);
            }
        });
        posthora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo_hora(v);
            }
        });





    }

    private void abre_dialogo(View v) {
        final String[] violaciones_DDHH = getResources().getStringArray(R.array.violaciones_DDHH);
        marcados=new boolean[violaciones_DDHH.length];
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Tipo de violación");
        builder.setMultiChoiceItems(R.array.violaciones_DDHH, marcados, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                //marcados[which]=isChecked;
                if(isChecked){
                    if (! listDatos.contains(which)){
                        listDatos.add(which);
                    }
                    else {
                        listDatos.remove(which);
                    }
                }
            }
        });
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String item="";
                for(int i=0; i < listDatos.size(); i++){
                    item=item+violaciones_DDHH[listDatos.get(i)];
                    if(i !=listDatos.size()-1){
                        item=item+",";
                    }
                }
                postCategoria.setText(item);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("Limpiar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int i=0;i<marcados.length; i++){
                    marcados[i]=false;
                    listDatos.clear();
                    postCategoria.setText("");

                }
            }
        });

        builder.create().show();

    }

    private void upLoadPhoto3() {
        String photoName=videoBringPath.substring(videoBringPath.lastIndexOf("/")+1,videoBringPath.length());
        StorageReference photoreference=storageReference.child("postimages/"+photoName);
        UploadTask uploadTask=photoreference.putFile(uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error al subir la foto" +e.toString());
                e.printStackTrace();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                Uri downloadUrl = uriTask.getResult();
                videoUrl = downloadUrl.toString();
                Log.w(TAG, "URL VIDEO > " + videoUrl);
                Evidence picture=new Evidence(videoUrl,posthora.getText().toString(),postfecha.getText().toString(),postlugar.getText().toString(),postCategoria.getText().toString(),postdescripcion.getText().toString(),databaseReference.push().getKey(), Constants.TYPE_VIDEO);
                databaseReference.child("Abusos").child(picture.getId()).setValue(picture);
            }
        });

        finish();

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void dialogo_fecha(View v) {

        DatePickerDialog datePickerDialog=new DatePickerDialog(this);
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                postfecha.setText(String.valueOf(dayOfMonth)+"-"+String.valueOf(month+1)+"-"+String.valueOf(year));
            }
        });
        datePickerDialog.show();

    }
    private void dialogo_hora(View v) {
        DialogFragment timerPicket=new TimePickerFragment();
        timerPicket.show(getSupportFragmentManager(),"timer picker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        posthora.setText("Hora: "+hourOfDay+" Minuto: "+minute);
    }
}
