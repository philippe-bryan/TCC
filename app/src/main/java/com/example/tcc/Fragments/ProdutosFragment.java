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
        produtosList.add(new Produtos("Coxas de frango","6 coxas de frango", "Frango", "05/02/2021", 20, "3.50", R.drawable.coxa_de_frango, 0, 0));
        produtosList.add(new Produtos("Ovos","12 ovos de galinha caipira", "Frango", "10/05/2021", 64, "12.00", R.drawable.ovos, 0, 0));
        produtosList.add(new Produtos("Leite de Vaca","500ml de leite de vaca", "Vaca", "22/07/2022", 5, "6.50", R.drawable.leite, 0, 0));
        produtosList.add(new Produtos("Leite de Cabra","350ml de leite de cabra", "Cabra", "02/12/2022", 15, "4.50", R.drawable.leite_de_cabra, 0, 0));
        produtosList.add(new Produtos("Queijo","500gm de queijo", "Vaca", "11/03/2022", 7, "9.90", R.drawable.queijo, 0, 0));
        produtosList.add(new Produtos("Peru","1 peru inteiro cru", "Peru", "28/02/2021", 13, "17.25", R.drawable.carne_de_peru, 0, 0));

        rcvProd = (RecyclerView) root.findViewById(R.id.rcvProd);
        adapter = new ProdAdapter(getActivity(), produtosList);
        rcvProd.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rcvProd.setHasFixedSize(true);
        rcvProd.setAdapter(adapter);
        return root;
    }
}
