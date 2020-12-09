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

import com.example.tcc.DetalheProd;
import com.example.tcc.Models.Produtos;
import com.example.tcc.R;

import java.util.List;

public class ProdAdapter extends RecyclerView.Adapter<ProdAdapter.MyViewHolder> {

    private List<Produtos> produtosList;
    private Context context;

    public ProdAdapter(Context c, List<Produtos> produtosList){
        this.produtosList = produtosList;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(context);
        v = inflater.inflate(R.layout.item_prod, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.lblNameItem.setText(produtosList.get(position).getNome_Produc());
        myViewHolder.lblPrecoItem.setText(produtosList.get(position).getValor_Produc());
        myViewHolder.imgItem.setImageResource(produtosList.get(position).getImagem_Prod());
        myViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetalheProd.class);
                intent.putExtra("Nome",produtosList.get(position).getNome_Produc());
                intent.putExtra("Preco",produtosList.get(position).getValor_Produc());
                intent.putExtra("Imagem",produtosList.get(position).getImagem_Prod());
                intent.putExtra("Desc",produtosList.get(position).getDescricao_Produc());
                intent.putExtra("qtd",produtosList.get(position).getQuant_Produc());
                intent.putExtra("valid",produtosList.get(position).getValidade_Produc());
                intent.putExtra("tipoAni",produtosList.get(position).getTipo_Ani_Produc());
                intent.putExtra("tipoAni",produtosList.get(position).getTipo_Ani_Produc());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return produtosList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView lblNameItem;
        TextView lblPrecoItem;
        ImageView imgItem;
        View view;


        public MyViewHolder(View itemView){
            super(itemView);

            imgItem = (ImageView) itemView.findViewById(R.id.imgItem);
            lblNameItem = (TextView) itemView.findViewById(R.id.lblNameItem);
            lblPrecoItem = (TextView) itemView.findViewById(R.id.lblPrecoItem);
            view = (View) itemView.findViewById(R.id.item_prod);
        }
    }
}
