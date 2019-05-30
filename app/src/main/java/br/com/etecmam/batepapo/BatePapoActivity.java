package br.com.etecmam.batepapo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import br.com.etecmam.batepapo.adapter.FoneAdapter;
import br.com.etecmam.batepapo.adapter.MensagemAdapter;
import br.com.etecmam.batepapo.dmp.Conversa;
import br.com.etecmam.batepapo.sms.Util;

public class BatePapoActivity extends AppCompatActivity {

    private ListView telefones;


    private static final int SMS_PERMISSION_CODE = 0;
    private static final String TAG = "BATE_PAPO";


    @Override
    protected void onResume() {
        super.onResume();
        Util.setTelaAtual(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bate_papo);

        setTitle("BATE-PAPO SMS");

        if(! hasReadSmsPermission()){
            requestReadAndSendSmsPermission();
        }

        Util.getApp().iniciarServicoEmBackGround();

        telefones = findViewById(R.id.bate_papo_fones);

        telefones.setOnItemClickListener( (AdapterView<?> adapterView, View view, int pos, long l) ->{

            String numeroSelecionado = (String) telefones.getItemAtPosition(pos);

            new AlertDialog.Builder(BatePapoActivity.this)
                    .setTitle("FONE")
                    .setMessage("APAGAR " + numeroSelecionado + " ?")
                    .setPositiveButton("OK", (DialogInterface dialogInterface, int i) ->{
                        Util.getTelefones().remove(pos);
                        carregarListaDeFones();
                    })
                    .setNegativeButton("CANCELAR",null)
                    .show();


        });

        carregarListaDeFones();

        findViewById(R.id.bate_papo_novo_numero).setOnClickListener( (View v) ->{
            View view = LayoutInflater
                    .from(BatePapoActivity.this)
                    .inflate(R.layout.layout_formulario_fone, null);

            new AlertDialog.Builder(BatePapoActivity.this)
                .setTitle("NOVO NÚMERO")
                .setView(view)
                .setPositiveButton("OK",(DialogInterface dialogInterface, int i) ->{
                    EditText fone = view.findViewById(R.id.formulario_campo_telefone);
                    Util.getTelefones().add( fone.getText().toString() );
                    carregarListaDeFones();
            })
            .setNegativeButton("CANCELAR", null)
            .setIcon(R.drawable.mensagem)
            .show();
        });

        findViewById(R.id.bate_papo_mensagem)
            .setOnClickListener((View v) ->{
            View view = LayoutInflater
                    .from(BatePapoActivity.this)
                    .inflate(R.layout.layout_formulario_mensagem, null);

            new AlertDialog.Builder(BatePapoActivity.this)
                    .setTitle("NOVA MENSAGEM")
                    .setView(view)
                    .setPositiveButton("OK",(DialogInterface dialogInterface, int i) ->{
                        EditText mensagem = view.findViewById(R.id.formulario_mensagem_texto);
                        Util.getConversas().add( new Conversa("EU", mensagem.getText().toString() ) );
                        carregarListaDeMensagens();

                        for (String numero: Util.getTelefones() ) {
                            Util.enviarSMS(numero, mensagem.getText().toString() );
                        }

                        Toast.makeText(BatePapoActivity.this,"MENSAGEM ENVIADA...",Toast.LENGTH_SHORT).show();

                    })
                    .setNegativeButton("CANCELAR", null)
                    .setIcon(R.drawable.mensagem)
                    .show();

        });

    }

    public void carregarListaDeMensagens() {
        ListView chat = findViewById(R.id.bate_papo_mensagens);
        MensagemAdapter adapter = new MensagemAdapter(this, Util.getConversas());
        chat.setAdapter(adapter);
    }

    private void carregarListaDeFones() {
        FoneAdapter adapter = new FoneAdapter(this, Util.getTelefones() );
        telefones.setAdapter(adapter);
    }

    private boolean hasReadSmsPermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadAndSendSmsPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
            Log.i(TAG,"shouldShowRequestPermissionRationale(), no permission requested");
            return;
        }

        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.SEND_SMS
                },
                SMS_PERMISSION_CODE);
    }
}
