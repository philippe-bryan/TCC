package com.example.tcc.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc.Models.Produtos;
import com.example.tcc.R;

import java.util.List;

public class ProdListAdapter extends RecyclerView.Adapter<ProdListAdapter.MyViewHolder> {

    private List<Produtos> List;
    private Context context;

    public ProdListAdapter(Context c, List<Produtos> List){
        this.List = List;
        this.context = c;
    }

    @NonNull
    @Override
    public ProdListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(context);
        v = inflater.inflate(R.layout.item_car, viewGroup, false);
        return new ProdListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdListAdapter.MyViewHolder myViewHolder, int position) {
        myViewHolder.lblNameItem.setText(List.get(position).getNome_Produc());
        myViewHolder.lblPrecoItem.setText(List.get(position).getValor_Produc());
        myViewHolder.lblDescitem.setText(List.get(position).getDescricao_Produc());
        myViewHolder.imgItem.setImageResource(List.get(position).getImagem_Prod());
    }

    @Override
    public int getItemCount() {
        return List.size();
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
