package br.com.etecmam.batepapo.sms;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import br.com.etecmam.batepapo.BatePapoActivity;
import br.com.etecmam.batepapo.R;
import br.com.etecmam.batepapo.dmp.Conversa;

public class ReceptorSMS  extends android.content.BroadcastReceiver {
    public static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    public ReceptorSMS() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();

            if (bundle != null) {
                // get sms objects
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus.length == 0) {
                    return;
                }
                SmsMessage[] messages = new SmsMessage[pdus.length];
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    sb.append(messages[i].getMessageBody());
                }

                final String mensagem = sb.toString();
                String sender = messages[0].getOriginatingAddress();


                for (String numero: Util.getTelefones() ) {

                    if(sender.contains(numero)){

                        Util.getConversas().add( new Conversa(numero, mensagem));
                        ( (BatePapoActivity) Util.getTelaAtual() ).carregarListaDeMensagens();

                    }

                }

            }
        }
    }
}

