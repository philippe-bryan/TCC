package com.example.tcc.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc.DetalProdCar;
import com.example.tcc.Models.Produtos;
import com.example.tcc.R;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.MyViewHolder> {

    private List<Produtos> carrinhoList;
    private Context context;

    public CarAdapter(Context c, List<Produtos> carrinhoList){
        this.carrinhoList = carrinhoList;
        this.context = c;
    }

    @NonNull
    @Override
    public CarAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(context);
        v = inflater.inflate(R.layout.item_car, viewGroup, false);
        return new CarAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CarAdapter.MyViewHolder myViewHolder, int position) {
        myViewHolder.lblNameItem.setText(carrinhoList.get(position).getNome_Produc());
        myViewHolder.lblPrecoItem.setText(carrinhoList.get(position).getValor_Produc());
        myViewHolder.lblDescitem.setText(carrinhoList.get(position).getDescricao_Produc());
        myViewHolder.imgItem.setImageResource(carrinhoList.get(position).getImagem_Prod());
        myViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetalProdCar.class);
                intent.putExtra("id", carrinhoList.get(position).getId_Prod());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carrinhoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView lblNameItem;
        TextView lblPrecoItem;
        TextView lblDescitem;
        ImageView imgItem;
        View view;

        public MyViewHolder(View itemView){
            super(itemView);
            imgItem = (ImageView) itemView.findViewById(R.id.imgItem2);
            lblNameItem = (TextView) itemView.findViewById(R.id.lblNameItem2);
            lblPrecoItem = (TextView) itemView.findViewById(R.id.lblPrecoItem2);
            lblDescitem = (TextView) itemView.findViewById(R.id.lblDescitem);
            view = (View) itemView.findViewById(R.id.item_car);
        }
    }
}
