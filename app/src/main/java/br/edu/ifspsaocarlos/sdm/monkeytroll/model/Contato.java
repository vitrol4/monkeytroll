package br.edu.ifspsaocarlos.sdm.monkeytroll.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import br.edu.ifspsaocarlos.sdm.monkeytroll.util.MkTrollConstants;

/**
 * Created by victor on 02/07/15.
 */
public class Contato implements Serializable {

    public static String KEY_ID = "id";
    public static String KEY_NOME = "nome";
    public static String KEY_NOME_COMPLETO = "nome_completo";
    public static String KEY_APELIDO = "apelido";

    private String id;
    private String nome;
    private String nomeCompleto;
    private String apelido;

    public Contato() {

    }

    public Contato(String id, String nome, String nomeCompleto, String apelido) {
        this.id = id;
        this.nome = nome;
        this.nomeCompleto = nomeCompleto;
        this.apelido = apelido;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    @Override
    public String toString() {
        return apelido;
    }

    public boolean limitNomeApelido() {
        return this.nomeCompleto.length() >= 100 && this.apelido.length() >= 100;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put(KEY_ID, id);
        map.put(KEY_NOME, nome);
        map.put(KEY_APELIDO, apelido);
        map.put(KEY_NOME_COMPLETO, nomeCompleto);
        return map;
    }

    public boolean isMkt() {
        if (nomeCompleto.contains(MkTrollConstants.SIGLA)) {
            return true;
        }
        return false;
    }
}
