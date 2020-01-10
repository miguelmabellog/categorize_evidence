package com.example.rud.createFile;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TimePicker;

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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class BringImage extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private static final String TAG="BringImage";
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private String photoBringPath;
    private Button subirFoto3;
    private ImageView imageViewBring;

    private TextInputEditText postCategoria;
    private TextInputEditText postfecha;
    private TextInputEditText posthora;
    private TextInputEditText postlugar;
    private TextInputEditText postdescripcion;

    private ArrayList<Integer> listDatos=new ArrayList<>();
    private boolean[] marcados;
    private RudAplication app;
    private String photoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bring_image);

        imageViewBring=findViewById(R.id.bringPhoto);
        subirFoto3=findViewById(R.id.subirFoto3);




        postlugar=findViewById(R.id.PostLugar3);
        postCategoria=findViewById(R.id.postCategoria3);
        postfecha=findViewById(R.id.PostFecha3);
        postdescripcion=findViewById(R.id.PostDescripcion3);
        posthora=findViewById(R.id.PostHora3);


        app=(RudAplication) getApplicationContext();

        showToolbar("RUD",true);




        imageViewBring.setImageURI((Uri) getIntent().getExtras().get(Intent.EXTRA_STREAM));
        photoBringPath="file:"+((Uri) getIntent().getExtras().get(Intent.EXTRA_STREAM)).getPath();


        databaseReference= FirebaseDatabase.getInstance().getReference();


        storageReference=app.getStorageReference();




        subirFoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    //Toast.makeText(BringImage.this, photoBringPath, Toast.LENGTH_SHORT).show();
                    upLoadPhoto2();
                }catch (Exception e){
                    e.printStackTrace();
                }


                //createAbuso();
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




        databaseReference= FirebaseDatabase.getInstance().getReference();







    }

    private void dialogo_hora(View v) {
        DialogFragment timerPicket=new TimePickerFragment();
        timerPicket.show(getSupportFragmentManager(),"timer picker");


    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        posthora.setText("Hora: "+hourOfDay+" Minuto: "+minute);
    }

    private void upLoadPhoto2() {
        imageViewBring.setDrawingCacheEnabled(true);
        imageViewBring.buildDrawingCache();
        Bitmap bitmap=imageViewBring.getDrawingCache();
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);

        byte[] photoByte= baos.toByteArray();

        String photoName=photoBringPath.substring(photoBringPath.lastIndexOf("/")+1,photoBringPath.length());

        //Picture picture=new Picture(databaseReference.push().getKey(),photoPath,posthora.getText().toString(),postfecha.getText().toString(),postlugar.getText().toString(),postCategoria.getText().toString(),postdescripcion.getText().toString());
        //databaseReference.child("Abusos").child(picture.getiD()).setValue(picture);

        StorageReference photoreference=storageReference.child("postimages/"+postfecha.getText()+"/"+photoName);
        UploadTask uploadTask=photoreference.putBytes(photoByte);
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
                photoUrl = downloadUrl.toString();
                Log.w(TAG, "URL PHOTO > " + photoUrl);

                Evidence picture=new Evidence(photoUrl,posthora.getText().toString(),postfecha.getText().toString(),postlugar.getText().toString(),postCategoria.getText().toString(),postdescripcion.getText().toString(),databaseReference.push().getKey(), Constants.TYPE_PHOTO);
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




    public void abre_dialogo(View view){
        final String[] violaciones_DDHH = getResources().getStringArray(R.array.violaciones_DDHH);
        marcados=new boolean[violaciones_DDHH.length];
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Tipo de evidencia");
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
    public void showToolbar(String tittle,boolean upButton){
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }



}
