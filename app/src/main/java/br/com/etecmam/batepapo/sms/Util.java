package br.com.etecmam.batepapo.sms;

import android.content.Context;
import android.telephony.SmsManager;

import java.util.ArrayList;
import java.util.List;

import br.com.etecmam.batepapo.App;
import br.com.etecmam.batepapo.dmp.Conversa;

public class Util {

    private static App app = null;
    private static Context telaAtual;
    private static List<String> telefones;
    private static List<Conversa> conversas;

    static {
        telefones = new ArrayList<>();
        conversas = new ArrayList<>();
    }

    public static void enviarSMS(String numero, String mensagem) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(numero,null, mensagem ,null,null);
    }

    public static List<Conversa> getConversas() {
        return conversas;
    }

    public static List<String> getTelefones() {
        return telefones;
    }

    public static App getApp() {
        return app;
    }

    public static void setApp(App app) {
        Util.app = app;
    }

    public static Context getTelaAtual() {
        return telaAtual;
    }

    public static void setTelaAtual(Context telaAtual) {
        Util.telaAtual = telaAtual;
    }
}

