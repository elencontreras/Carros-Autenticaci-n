package com.example.android.carros_material;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
/**
 * Created by android on 21/10/2017.
 */

public class AdaptadorCarro extends RecyclerView.Adapter<AdaptadorCarro.CarroViewHolder> {
    private ArrayList<Carro> carros;
    private Resources res;
    private OnCarroClickListener clickListener;
    String [] opcMarca, opcModelo;
    private StorageReference storageReference;
    private Context contexto;

    public AdaptadorCarro(Context contexto, ArrayList<Carro> carros, OnCarroClickListener clickListener){
        this.carros=carros;
        res=contexto.getResources();
        this.clickListener=clickListener;
        this.contexto=contexto;
    }

    @Override
    public CarroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_carro,parent, false);
        return new CarroViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CarroViewHolder holder, int position) {
        final Carro c = carros.get(position);
        storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.child(c.getFoto()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(contexto).load(uri).into(holder.foto);
            }
        });
        opcMarca = res.getStringArray(R.array.marca);
        opcModelo = res.getStringArray(R.array.modelo);
       // holder.foto.setImageDrawable(ResourcesCompat.getDrawable(res, c.getFoto(), null));
        holder.placa.setText(c.getPlaca());
        holder.marca.setText(opcMarca[c.getMarca()]);
        holder.modelo.setText(opcModelo[c.getMarca()]);
        holder.precio.setText("" + c.getPrecio());

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onCarroClick(c);
            }
        });

    }



    @Override
    public int getItemCount() {
        return carros.size();
    }

    public static class CarroViewHolder extends RecyclerView.ViewHolder{
        private ImageView foto;
        private TextView placa;
        private TextView marca;
        private TextView modelo;
        private TextView precio;
        private View v;

        public CarroViewHolder(View itemView){
            super(itemView);
            v = itemView;
            foto = (ImageView)itemView.findViewById(R.id.imgFoto);
            placa = (TextView)itemView.findViewById(R.id.lblPlaca);
            marca = (TextView)itemView.findViewById(R.id.lblMarca);
            modelo =(TextView)itemView.findViewById(R.id.lblModelo);
            precio = (TextView)itemView.findViewById(R.id.lblPrecio);
        }

    }

    public interface OnCarroClickListener{
        void onCarroClick(Carro c);
    }
}
