package com.example.tcc.NetworkUtils;

import com.example.tcc.Models.Cliente;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    private static String readStream(InputStream in){
        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        StringBuilder total = new StringBuilder();
        String line;

        try {
            while ((line = r.readLine()) != null) {
                total.append(line).append('n');
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return total.toString();
    }

    private static String request(String stringUrl) throws IOException {
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(stringUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);
        }
        finally {
            urlConnection.disconnect();
        }
    }

    public static Cliente localizar(String id) throws JSONException, IOException {
        String resposta = request("https://superheroapi.com/api/1022707078160850/"+id+"/biography");
        JSONObject obj = new JSONObject(resposta);
        String Nome = obj.get("Nome").toString();
        String RazaoSocial = obj.get("RazaoSocial").toString();
        String CNPJ = obj.get("CNPJ").toString();
        String Endereco = obj.get("Endereco").toString();
        String Telefone = obj.get("Telefone").toString();
        String Celular = obj.get("Celular").toString();
        String Email = obj.get("Email").toString();
        String Senha = obj.get("Senha").toString();
        return new Cliente(Nome, RazaoSocial, CNPJ, Endereco, Telefone, Celular, Email, Senha);
    }

    public static void Post(String url, byte [] data, String contentType) throws IOException {
        // url da api
        // data dados a serem enviado em um byte array
        // content type da api se definido

        HttpURLConnection connection = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("Content-Type", contentType);
            //indica que haverá saída de dados
            connection.setDoOutput(true);
            //inicia envio de dados
            out = connection.getOutputStream();
            //transmite os dados
            out.write(data);
            //fecha o envio de dados
            out.close();
            //inicia recebimento da resposta
            in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            //trata a resposta
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
        } finally {
            if (connection != null) connection.disconnect();
            if (out != null) out.close();
            if (in != null) in.close();
        }
    }
}
