package com.example.android.carros_material;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ModificarCarro extends AppCompatActivity implements  RewardedVideoAdListener {
    private EditText txtPlaca, txtPrecio;
    private TextInputLayout cajaPlaca, cajaPrecio;
    private Spinner comboMarca, comboModelo, comboColor;
    private String[] opcMarca, opcModelo, opcColor;
    private ArrayList<Integer> fotos;
    private ArrayAdapter<String> adapter;
    private Resources res;
    private String placa, placaExistente, id,fot;
    private Double precio;
    private int marca, modelo, color;
    private Bundle b;
    private Intent i;
    private ImageView fotoModificar;
    private Uri filePath;
    private StorageReference storageReference;
    private RewardedVideoAd rewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_carro);

        storageReference= FirebaseStorage.getInstance().getReference();

        res = this.getResources();

        txtPlaca = (EditText) findViewById(R.id.txtPlacaM);
        txtPrecio = (EditText) findViewById(R.id.txtPrecioM);
        comboMarca = (Spinner) findViewById(R.id.cmbMarcaM);
        comboModelo = (Spinner) findViewById(R.id.cmbModeloM);
        comboColor = (Spinner) findViewById(R.id.cmbColorM);
        fotoModificar=(ImageView)findViewById(R.id.fotoModificar);

        opcMarca = res.getStringArray(R.array.marca);
        opcModelo = res.getStringArray(R.array.modelo);
        opcColor = res.getStringArray(R.array.color);

        i = getIntent();
        b = i.getBundleExtra("datos");
        id = b.getString("id");
        placa = b.getString("placa");
        marca = b.getInt("marca");
        modelo = b.getInt("modelo");
        color = b.getInt("color");
        precio = b.getDouble("precio");
        fot = b.getString("foto");

        txtPlaca.setText(placa);
        ArrayAdapter<String> adapterMarca = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opcMarca);
        ArrayAdapter<String> adapterModelo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opcModelo);
        ArrayAdapter<String> adapterColor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opcColor);
        comboMarca.setAdapter(adapterMarca);
        comboModelo.setAdapter(adapterModelo);
        comboColor.setAdapter(adapterColor);
        txtPrecio.setText(precio + "");
        cajaPlaca = (TextInputLayout) findViewById(R.id.cajaPlacaM);
        cajaPrecio = (TextInputLayout) findViewById(R.id.cajaPrecioM);
        storageReference.child(fot).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(ModificarCarro.this).load(uri).into(fotoModificar);
            }
        });

        rewardedVideoAd= MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        otroVideo();

    }

    public void otroVideo(){
        rewardedVideoAd.loadAd(res.getString(R.string.id_video),new AdRequest.Builder().build());
    }

    public void Modificar(View v) {
        String plac;
        int mar, mod, col;
        double pre;

        plac = txtPlaca.getText().toString();
        mar = comboMarca.getSelectedItemPosition();
        mod = comboModelo.getSelectedItemPosition();
        col = comboColor.getSelectedItemPosition();
        pre = Double.parseDouble(txtPrecio.getText().toString());
        Carro c = new Carro(id, fot, plac, mar, mod, col, pre);

        if (placa.equals(plac)) {
            c.modificar();
            if (filePath!=null)subirFoto(fot);

            Snackbar.make(v, res.getString(R.string.msj_exito_modificar), Snackbar.LENGTH_LONG).setAction("action", null).show();
            if (rewardedVideoAd.isLoaded()){
                rewardedVideoAd.show();
            }
            Cancelar();
        } else {
            if (Metodos.exitencia_carro(Datos.obtenerCarro(), plac)) {
                txtPlaca.setError(res.getString(R.string.persona_existente_error));
                txtPlaca.requestFocus();
            } else {
                c.modificar();
                Snackbar.make(v, res.getString(R.string.msj_exito_modificar), Snackbar.LENGTH_LONG).setAction("action", null).show();
                Cancelar();
            }

        }
    }

    public void Cancelar(View v){
        Cancelar();
    }

    public void Cancelar(){

       /* String plac=txtPlaca.getText().toString();
        int mar=comboMarca.getSelectedItemPosition();
        int mod=comboModelo.getSelectedItemPosition();
        int col=comboColor.getSelectedItemPosition();
        Double pre=Double.parseDouble(txtPrecio.getText().toString());

        Intent i = new Intent(ModificarCarro.this, DetalleCarro.class);
        Bundle b = new Bundle();
        b.putString("foto", fot);
        b.putString("placa", plac);
        b.putInt("marca", mar);
        b.putInt("modelo", mod);
        b.putInt("color", col);
        b.putDouble("precio", pre);
        i.putExtra("datos", b);*/

        finish();
        Intent i=new Intent(ModificarCarro.this, Principal.class);
        startActivity(i);
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
                fotoModificar.setImageURI(filePath);


            }
        }
    }

    public  void subirFoto(String foto){
        StorageReference chidRef= storageReference.child(foto);
        UploadTask uploadTask = chidRef.putFile(filePath);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Cancelar();
            }
        });
    }


    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }
}
