package com.example.harshgupta.project;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Harsh Gupta on 1/26/2018.
 */

public class frontpage extends AppCompatActivity {
    final int REQ_CODE_SPEECH_INPUT=100;
    private ArrayList<String> speechtotext=null;
    TextToSpeech tts;
    private String a[]={"Start","Touch","Back"};
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mai);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.US);
                    tts.speak("welcome", TextToSpeech.QUEUE_FLUSH, null); //What do I put here?
                    SystemClock.sleep(2000);
                    //  for (int j = 0; ; j++) {
                    tts.speak("choose your option", TextToSpeech.QUEUE_FLUSH, null); //What do I put here?
                    SystemClock.sleep(2000);
                    for (int i = 0; i < 3; i++) {
                        tts.speak(a[i], TextToSpeech.QUEUE_FLUSH, null); //What do I put here?
                        SystemClock.sleep(2000);
                    }


                } else {
                    Toast.makeText(getApplicationContext(),
                            "Feature not Supported in Your Device",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        SystemClock.sleep(2000);
        promptSpeechInput();
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
        SystemClock.sleep(10000);
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
                    if(speechtotext.get(0).equals("start")){
                        Intent ab= new Intent(this,mainmenu.class);
                        startActivity(ab);
                    }
                }
                break;
            }

        }

    }
}