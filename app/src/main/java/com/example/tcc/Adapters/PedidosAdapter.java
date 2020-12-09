package com.example.tcc.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc.Models.Compra;
import com.example.tcc.ProdList;
import com.example.tcc.R;

import java.util.List;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.MyViewHolder> {
    private List<Compra> pedidosList;
    private Context context;

    public PedidosAdapter(Context c, List<Compra> pedidosList){
        this.pedidosList = pedidosList;
        this.context = c;
    }

    @NonNull
    @Override
    public PedidosAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(context);
        v = inflater.inflate(R.layout.item_ped, viewGroup, false);
        return new PedidosAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidosAdapter.MyViewHolder myViewHolder, int position) {
        myViewHolder.lblData.setText(pedidosList.get(position).getData());
        myViewHolder.lblPrecoCompra.setText(pedidosList.get(position).getPrecoCompra());
        myViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProdList.class);
                intent.putExtra("id", pedidosList.get(position).getId_Compra());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pedidosList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView lblData;
        TextView lblPrecoCompra;
        View view;

        public MyViewHolder(View itemView){
            super(itemView);
            lblData = (TextView) itemView.findViewById(R.id.lblData);
            lblPrecoCompra = (TextView) itemView.findViewById(R.id.lblPrecoCompra);
            view = (View) itemView.findViewById(R.id.item_car);
        }
    }
}
