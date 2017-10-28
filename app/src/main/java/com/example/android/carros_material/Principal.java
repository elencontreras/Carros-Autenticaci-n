package com.example.android.carros_material;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Principal extends AppCompatActivity implements  AdaptadorCarro.OnCarroClickListener {
    private RecyclerView listado;
    private ArrayList<Carro> carros;
    private Resources res;
    private AdaptadorCarro adapter;
    private LinearLayoutManager llm;
    private DatabaseReference databaseReference;
    private final String BD= "Carros";
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        res=this.getResources();

        listado=(RecyclerView)findViewById(R.id.lstOpciones);
        carros = new ArrayList<>();

        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        adapter = new AdaptadorCarro(this.getApplicationContext(),carros,this);
        listado.setLayoutManager(llm);
        listado.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(BD).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                carros.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Carro c = snapshot.getValue(Carro.class);
                        carros.add(c);
                    }
                }
                adapter.notifyDataSetChanged();
                Datos.setCarros(carros);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               i=new Intent(Principal.this, CrearCarro.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onCarroClick(Carro c) {
        Intent i = new Intent(Principal.this, DetalleCarro.class);
        Bundle b = new Bundle();
        b.putString("id", c.getId());
        b.putString("foto",c.getFoto());
        b.putString("placa", c.getPlaca());
        b.putInt("marca", c.getMarca());
        b.putInt("modelo", c.getModelo());
        b.putDouble("precio", c.getPrecio());

        i.putExtra("datos", b);
        startActivity(i);
    }
}
