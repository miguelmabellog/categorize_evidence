package com.example.rud.createFile;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.example.rud.R;
import com.example.rud.context.RudAplication;
import com.example.rud.model.Evidence;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewFolderActivity extends AppCompatActivity {
    private static final String TAG="NewPostActivity";
    private TextInputEditText postCategoria;
    private TextInputEditText folderDate;
    private TextInputEditText posthora;
    private TextInputEditText folderPlace;
    private TextInputEditText folderDescription;

    private ArrayList<Integer> listDatos=new ArrayList<>();
    private boolean[] marcados;
    RecyclerView recycler;
    private Button folderButton;

    private String photoPath;
    private RudAplication app;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    private ImageView folderImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_folder);
        app=(RudAplication) getApplicationContext();

        folderImages =findViewById(R.id.newFolderImage);
        folderPlace =findViewById(R.id.newFolderPlace);
        folderDate=findViewById(R.id.newFolderDate);
        folderDescription=findViewById(R.id.newFolderDescription);
        folderButton =findViewById(R.id.newFolderButton);

        databaseReference=FirebaseDatabase.getInstance().getReference();
        storageReference=app.getStorageReference();

        showToolbar("RUD",true);



        if(getIntent().getExtras() !=null){
            photoPath=getIntent().getExtras().getString("PHOTO_PATH_TEMP");
            showPhoto();
        }
        folderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAbuso();

            }
        });

        folderDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                dialogo_fecha(v);
            }
        });


    }



    public void showToolbar(String tittle,boolean upButton){
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    private void showPhoto(){
        Picasso.with(this).load(photoPath).into(folderImages);
    }

    public void createAbuso(){
        Evidence evidence=new Evidence("","", folderDate.getText().toString(), folderPlace.getText().toString(),"", folderDescription.getText().toString(),databaseReference.push().getKey(),"carpeta");
        databaseReference.child("Abusos").child(evidence.getId()).setValue(evidence);
        finish();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void dialogo_fecha(View v) {

        DatePickerDialog datePickerDialog=new DatePickerDialog(this);
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                folderDate.setText(String.valueOf(dayOfMonth)+"-"+String.valueOf(month+1)+"-"+String.valueOf(year));
            }
        });
        datePickerDialog.show();

    }


}
