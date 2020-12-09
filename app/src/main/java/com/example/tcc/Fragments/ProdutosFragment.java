package com.example.tcc.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tcc.Adapters.ProdAdapter;
import com.example.tcc.Models.Produtos;
import com.example.tcc.R;

import java.util.ArrayList;
import java.util.List;

public class ProdutosFragment extends Fragment {

    private List<Produtos> produtosList;
    View root;
    ProdAdapter adapter;
    private RecyclerView rcvProd;
    private TextView lblInfo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_produtos, container, false);
        lblInfo = (TextView) root.findViewById(R.id.lblInfo);

        produtosList = new ArrayList<>();
        produtosList.add(new Produtos("Carne de vaca", "670g de carne de vaca", "Vaca", "01/12/2020", 12, "23.90", R.drawable.carne_de_vaca, 0, 0));
        produtosList.add(new Produtos("Carne de porco","500g de carne de porco", "Porco", "20/12/2020", 6,"20.00", R.drawable.carne_de_porco, 0, 0));
        produtosList.add(new Produtos("Carne de frango","1 frango cru", "Frango", "12/10/2021", 10, "13.00", R.drawable.carne_de_frango, 0, 0));
        produtosList.add(new Produtos("Coxas de frango","6 coxas de frango", "Forco", "05/02/2021", 20, "3.50", R.drawable.coxa_de_frango, 0, 0));
        produtosList.add(new Produtos("Ovos","12 ovos de galinha caipira", "Porco", "10/05/2021", 64, "12.00", R.drawable.ovos, 0, 0));
        produtosList.add(new Produtos("Caixa de Leite","500ml de leite de vaca", "Porco", "16/06/2022", 5, "6.50", R.drawable.leite, 0, 0));

        rcvProd = (RecyclerView) root.findViewById(R.id.rcvProd);
        adapter = new ProdAdapter(getActivity(), produtosList);
        rcvProd.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rcvProd.setHasFixedSize(true);
        rcvProd.setAdapter(adapter);
        return root;
    }
}
