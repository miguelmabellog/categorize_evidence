package com.example.rud.details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rud.R;
import com.example.rud.context.RudAplication;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class FolderDetailActivity extends AppCompatActivity {
    private ImageView imageHeader;
    private RudAplication app;
    private String PHOTO_ID;
    private String PHOTO_NAME;
    private String fecha;
    private String categoria;
    private String lugar;
    private String hora;
    private String descripcion;


    private TextView userDateCardDetail;
    private TextView userCategoriaCardDetail;
    private TextView userHoraCardDetail;
    private TextView userPlaceCardDetail;
    private TextView userDescripcionCardDetail;


    StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_detail);

        Bundle datos=this.getIntent().getExtras();
        PHOTO_ID=datos.getString("envia_id");
        PHOTO_NAME=getIntent().getStringExtra("envia_imagen");
        fecha=getIntent().getStringExtra("envia_fecha");
        hora=getIntent().getStringExtra("envia_hora");
        lugar=getIntent().getStringExtra("envia_lugar");
        descripcion=getIntent().getStringExtra("envia_descripcion");
        categoria=getIntent().getStringExtra("envia_categoria");





        app=(RudAplication) getApplicationContext();
        storageReference=app.getStorageReference();

        //databaseReference=FirebaseDatabase.getInstance().getReference();




        showToolbar("RUD",true);


        showData();
    }


    private void showData() {

        userDateCardDetail=findViewById(R.id.CarpetaFecha);
        userPlaceCardDetail=findViewById(R.id.CarpetaLugar);
        userDescripcionCardDetail=findViewById(R.id.CarpetaDescripcion);


        userDateCardDetail.setText(fecha);
        userDescripcionCardDetail.setText(descripcion);
        userPlaceCardDetail.setText(lugar);



        //Picasso.with(CarpetaDetail.this).load(PHOTO_NAME).into(imageHeader);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_detail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.mEliminarArchivo:
                Toast.makeText(this,"No se puede eliminar ningun archivo desde la aplicación", Toast.LENGTH_LONG).show();
                break;
            case R.id.mEditarArchivo:
                Toast.makeText(this, "Falta poner esto", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void showToolbar(String tittle,boolean upButton){
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
}

