package br.com.etecmam.batepapo.dmp;

public class Conversa {
    String numero;
    String texto;

    public Conversa(String numero, String texto) {
        this.numero = numero;
        this.texto = texto;
    }

    public String getNumero() {
        return numero;
    }

    public String getTexto() {
        return texto;
    }
}
