package br.edu.ifspsaocarlos.sdm.monkeytroll.model;

/**
 * Created by victor on 02/07/15.
 */
public class Mensagem {

    private String id;
    private String origem;
    private String destino;
    private String assunto;
    private String corpo;

    public Mensagem() {
    }

    public Mensagem(String id, String origem, String destino, String assunto, String corpo) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.assunto = assunto;
        this.corpo = corpo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mensagem)) return false;

        Mensagem mensagem = (Mensagem) o;

        if (!id.equals(mensagem.id)) return false;
        if (!origem.equals(mensagem.origem)) return false;
        if (!destino.equals(mensagem.destino)) return false;
        if (!assunto.equals(mensagem.assunto)) return false;
        return corpo.equals(mensagem.corpo);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + origem.hashCode();
        result = 31 * result + destino.hashCode();
        result = 31 * result + assunto.hashCode();
        result = 31 * result + corpo.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Mensagem{");
        sb.append("id='").append(id).append('\'');
        sb.append(", origem='").append(origem).append('\'');
        sb.append(", destino='").append(destino).append('\'');
        sb.append(", assunto='").append(assunto).append('\'');
        sb.append(", corpo='").append(corpo).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public boolean limitAssuntoCorpo() {
        return this.assunto.length() <= 50 && this.corpo.length() <= 150;
    }
}
