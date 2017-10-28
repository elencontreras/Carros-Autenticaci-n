package com.example.android.carros_material;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class CrearCarro extends AppCompatActivity {
    private EditText txtPlaca, txtPrecio;
    private TextInputLayout cajaPlaca, cajaPrecio;
    private Spinner comboMarca, comboModelo, comboColor;
    private String [] opcMarca, opcModelo, opcColor;
    private ArrayList<Integer> fotos;
    private ArrayAdapter<String> adapter;
    private Resources res;
    private Uri filePath;
    private ImageView foto;
    private StorageReference storageReference;
    private AdView adView;
    private InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_carro);
        res=this.getResources();
        storageReference = FirebaseStorage.getInstance().getReference();
        txtPlaca=(EditText)findViewById(R.id.txtPlaca);
        txtPrecio=(EditText)findViewById(R.id.txtPrecio);
        cajaPlaca=(TextInputLayout)findViewById(R.id.cajaPlaca);
        cajaPrecio=(TextInputLayout)findViewById(R.id.cajaPrecio);
        comboMarca=(Spinner)findViewById(R.id.cmbMarca);
        comboModelo=(Spinner)findViewById(R.id.cmbModelo);
        comboColor=(Spinner)findViewById(R.id.cmbColor);
        foto=(ImageView)findViewById(R.id.fotoCrear) ;
        adView=(AdView)findViewById(R.id.adView);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        interstitialAd=new InterstitialAd(this);
        interstitialAd.setAdUnitId(res.getString(R.string.id_inter));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                //super.onAdClosed();
                otroInter();
            }
        });

        opcMarca= res.getStringArray(R.array.marca);
        opcModelo = res.getStringArray(R.array.modelo);
        opcColor = res.getStringArray(R.array.color);

        ArrayAdapter <String> adapterMarca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opcMarca);
        ArrayAdapter <String> adapterModelo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opcModelo);
        ArrayAdapter <String> adapterColor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opcColor);

        comboMarca.setAdapter(adapterMarca);
        comboModelo.setAdapter(adapterModelo);
        comboColor.setAdapter(adapterColor);

    otroInter();

      //  iniciar_fotos();
    }

   /* public void iniciar_fotos(){
        fotos=new ArrayList<>();
        fotos.add(R.drawable.audi);
        fotos.add(R.drawable.chevrolet);
        fotos.add(R.drawable.kia);
    }*/

    public void otroInter(){
        AdRequest adRequest=new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
    }
    public void guardar(View v){
        String id = Datos.getId();
        String foto_id=id+".jpg";
        String placa;
        double precio;
        int marca, modelo, color;

        placa=txtPlaca.getText().toString();
        marca=comboMarca.getSelectedItemPosition();
        modelo=comboModelo.getSelectedItemPosition();
        color=comboColor.getSelectedItemPosition();
        precio= Double.parseDouble(txtPrecio.getText().toString());

        Carro c = new Carro(id,foto_id,placa,marca,modelo,color,precio);
        c.guardar();
        subirFoto(foto_id);
        Snackbar.make(v, res.getString(R.string.msjGuardar), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void onBackPressed(){
        finish();
        Intent i = new Intent(CrearCarro.this,Principal.class);
        startActivity(i);
    }

    public void limpiar(View v){
        txtPlaca.setText("");
        txtPrecio.setText("");
        foto.setImageDrawable(ResourcesCompat.getDrawable(res,android.R.drawable.ic_menu_gallery, null));
        if (interstitialAd.isLoaded()){
            interstitialAd.show();
        }
    }

    public void seleccionarFoto(View v){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, res.getString(R.string.msj_seleccion)),1);
    }

    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==1){
            filePath = data.getData();
            if (filePath!=null){
                foto.setImageURI(filePath);


            }
        }
    }

    public  void subirFoto(String foto){
        StorageReference chidRef= storageReference.child(foto);
        UploadTask uploadTask = chidRef.putFile(filePath);
    }
}
