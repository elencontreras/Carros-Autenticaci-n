package com.example.android.carros_material;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class DetalleCarro extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Carro c;
    private String placa,id,fot;
    private Double precio;
    private int marca,modelo;
    private Bundle bundle;
    private Intent i;
    private ImageView foto;
    private Resources res;
    private TextView txtPlaca, txtPrecio, txtMarca, txtModelo;
    private String [] opcMarca, opcModelo;
    private StorageReference storageReference;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_carro);
        res=this.getResources();

        txtPlaca=(TextView)findViewById(R.id.lblPlacaD);
        txtMarca=(TextView)findViewById(R.id.lblMarcaD);
        txtModelo=(TextView)findViewById(R.id.lblModeloD);
        txtPrecio=(TextView)findViewById(R.id.lblPrecioD);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        foto = (ImageView)findViewById(R.id.fotoCarro);
        i=getIntent();
        bundle=i.getBundleExtra("datos");

        id = bundle.getString("id");
        fot = bundle.getString("foto");
        placa = bundle.getString("placa");
        marca = bundle.getInt("marca");
        modelo = bundle.getInt("modelo");
        precio = bundle.getDouble("precio");

        opcMarca = res.getStringArray(R.array.marca);
        opcModelo = res.getStringArray(R.array.modelo);

        collapsingToolbarLayout.setTitle(placa);
        storageReference= FirebaseStorage.getInstance().getReference();
        storageReference.child(fot).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(DetalleCarro.this).load(uri).into(foto);
            }
        });
       // foto.setImageDrawable(ResourcesCompat.getDrawable(res,fot,null));

        txtPlaca.setText(placa);
        txtMarca.setText(opcMarca[marca]);
        txtModelo.setText(opcModelo[modelo]);
        txtPrecio.setText(precio+"");
    }

    public void eliminar(View v){
        String positivo,negativo;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(res.getString(R.string.titulo_eliminar_mensaje));
        builder.setMessage(res.getString(R.string.eliminar_mensaje));
        positivo = res.getString(R.string.si_eliminar_mensaje);
        negativo = res.getString(R.string.no_eliminar_mensaje);



        builder.setPositiveButton(positivo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Carro c = new Carro(id);
                Datos.eliminarCarro(c);
                onBackPressed();

            }
        });
        builder.setNegativeButton(negativo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();



    }
    public void onBackPressed(){
        finish();
        Intent i = new Intent(DetalleCarro.this,Principal.class);
        startActivity(i);
    }

    public void ModificarCarro(View v) {
        Intent i = new Intent(DetalleCarro.this,ModificarCarro.class);
        i.putExtra("datos",bundle);
        startActivity(i);

    }
}
