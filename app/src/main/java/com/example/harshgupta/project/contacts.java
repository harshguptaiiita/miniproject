package com.example.harshgupta.project;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by MEHUL on 1/24/2018.
 */

public class contacts extends AppCompatActivity {
    final int REQ_CODE_SPEECH_INPUT =100;
    TextToSpeech tts;
    private ArrayList<String> speechtotext=null;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);
        String str="Speak name and mobile number";
        talk(str);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        promptSpeechInput();
    }
    public void talk(final String spk){
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.US);
                    tts.speak(spk,TextToSpeech.QUEUE_FLUSH,null ); //What do I put here?
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Feature not Supported in Your Device",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
    private void promptSpeechInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    speechtotext = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //txtSpeechInput.setText(result.get(0));
                    Log.e("Result",speechtotext.get(0));
                    String res_ult=speechtotext.get(0);
                    save_contacts(res_ult);
                }
                break;
            }

        }

    }
    public void  save_contacts(String name_number){
            int n=name_number.length();
            String name="",number="";
            int i=n-1;
            int index=0;
            while(i>=0){
                if(name_number.charAt(i)<='9'&& name_number.charAt(i)>='0'){
                    number=name_number.charAt(i)+number;
                    index=i;
                }
                i--;
            }
            Log.e(String.valueOf(index),number);
            int whitespace=0;
            for(i=0;i<n;i++){
                if(name_number.charAt(i)==' '){
                    whitespace++;
                }
                if(whitespace>4 && !(name_number.charAt(i)<='9' && name_number.charAt(i)>=0)){
                    name+=name_number.charAt(i);
                }
                if(whitespace>4 && name_number.charAt(i)==' ' &&(!(name_number.charAt(i+1)<='9' && name_number.charAt(i+1)>=0))){
                    name+=name_number.charAt(i);
                }
            }
            Log.e(String.valueOf(index),name);
    }
}
