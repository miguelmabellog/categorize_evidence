package com.example.rud.details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rud.R;
import com.example.rud.context.RudAplication;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_photo_detail);
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


        imageHeader=findViewById(R.id.imageHeader);

        showToolbar("RUD",true);


        showData();


    }

    private void showData() {

        userDateCardDetail=findViewById(R.id.userDateCardDetail);
        userCategoriaCardDetail=findViewById(R.id.userCategoriaDetail);
        userHoraCardDetail=findViewById(R.id.userHoraCardDetail);
        userPlaceCardDetail=findViewById(R.id.userPlaceCardDetail);
        userDescripcionCardDetail=findViewById(R.id.userDescripcionCardDetail);


        userDateCardDetail.setText(fecha);
        userDescripcionCardDetail.setText(descripcion);
        userHoraCardDetail.setText(hora);
        userCategoriaCardDetail.setText(categoria);
        userPlaceCardDetail.setText(lugar);



        Picasso.with(PhotoDetailActivity.this).load(PHOTO_NAME).into(imageHeader);



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
                Toast.makeText(this,(categoria) , Toast.LENGTH_LONG).show();
                break;
            case R.id.mEditarArchivo:
                Toast.makeText(this, PHOTO_ID, Toast.LENGTH_LONG).show();
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
