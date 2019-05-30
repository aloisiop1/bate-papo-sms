package br.com.etecmam.batepapo.sms;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import java.io.Serializable;

public class Servico extends Service implements Serializable {

    private ReceptorSMS receptorSMS = null;
    public static boolean rodandoSozinho = true;

    public Servico(boolean soh) {
        rodandoSozinho = soh;
    }

    public Servico() {
        Log.i("Servico", "construtor vazio");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {

        super.onCreate();

        // Create an IntentFilter instance.
        IntentFilter intentFilter = new IntentFilter();

        // Add network connectivity change action.
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");

        // Set broadcast receiver priority.
        intentFilter.setPriority(100);

        // Create a network change broadcast receiver.
        receptorSMS = new ReceptorSMS();

        // Register the broadcast receiver with the intent filter object.
        registerReceiver(receptorSMS, intentFilter);

        Log.d(receptorSMS.SMS_RECEIVED.toString() , "Service onCreate: ReceptorSMS is registered.");
    }

    @Override
    public void onDestroy() {

        // Unregister screenOnOffReceiver when destroy.
        if(receptorSMS!=null)        {
            unregisterReceiver(receptorSMS);
            Log.i(ReceptorSMS.SMS_RECEIVED.toString(), "Service onDestroy: ReceptorSMS is unregistered.");
        }

        super.onDestroy();
    }

}
