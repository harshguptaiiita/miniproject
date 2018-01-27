package com.example.harshgupta.project;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by MEHUL on 1/26/2018.
 */

public class mainmenu extends AppCompatActivity {
    final int REQ_CODE_SPEECH_INPUT=100;
    public static boolean batteryAt100 = false;
    public static Context context;
    private ArrayList<String> speechtotext=null;
    TextToSpeech tts;
    public static boolean batteryIsCharging = false;
    private String a[]={"Music","Contacts","Date and Time","Status","Alaram"};
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.US);
                    tts.speak("welcome to main menu", TextToSpeech.QUEUE_FLUSH, null); //What do I put here?
                    SystemClock.sleep(1000);
                    tts.speak("choose your option", TextToSpeech.QUEUE_FLUSH, null); //What do I put here?
                    SystemClock.sleep(2000);
                    for (int i = 0; i < 4; i++) {
                        tts.speak(a[i], TextToSpeech.QUEUE_FLUSH, null); //What do I put here?
                        SystemClock.sleep(2000);
                    }

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


                    // SystemClock.sleep(3000);
                    //tts.stop();
                    // }

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Feature not Supported in Your Device",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
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
    public static String getMonth()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        return sdf.format(Calendar.getInstance().getTime());
    }

    public static String getYear()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(Calendar.getInstance().getTime());
    }
    public static String getMonthName(int month)
    {
        String result = "";
        switch (month)
        {
            case 1:
                result = context.getResources().getString(R.string.january);
                break;

            case 2:
                result = context.getResources().getString(R.string.february);
                break;

            case 3:
                result = context.getResources().getString(R.string.march);
                break;

            case 4:
                result = context.getResources().getString(R.string.april);
                break;

            case 5:
                result = context.getResources().getString(R.string.may);
                break;

            case 6:
                result = context.getResources().getString(R.string.june);
                break;

            case 7:
                result = context.getResources().getString(R.string.july);
                break;

            case 8:
                result = context.getResources().getString(R.string.august);
                break;

            case 9:
                result = context.getResources().getString(R.string.september);
                break;

            case 10:
                result = context.getResources().getString(R.string.october);
                break;

            case 11:
                result = context.getResources().getString(R.string.november);
                break;

            case 12:
                result = context.getResources().getString(R.string.december);
                break;
        }
        return result;
    }
    public static String getDayName(int dayname)
    {
        String result = "";
        switch (dayname)
        {
            case 1:
                result = context.getResources().getString(R.string.sunday);
                break;

            case 2:
                result = context.getResources().getString(R.string.monday);
                break;

            case 3:
                result = context.getResources().getString(R.string.tuesday);
                break;

            case 4:
                result = context.getResources().getString(R.string.wednesday);
                break;

            case 5:
                result = context.getResources().getString(R.string.thursday);
                break;

            case 6:
                result = context.getResources().getString(R.string.friday);
                break;

            case 7:
                result = context.getResources().getString(R.string.saturday);
                break;
        }
        return result;
    }
    public String status()
    {
        String year = getYear();
        String month = getMonthName(Integer.valueOf(getMonth()));
        String day = Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        String dayname = getDayName(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        String hour = Integer.toString(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        String minutes = Integer.toString(Calendar.getInstance().get(Calendar.MINUTE));

        String textStatus = "";
        textStatus=textStatus +
                        getResources().getString(R.string.time)+
                hour + getResources().getString(R.string.mainHours) +
                minutes + getResources().getString(R.string.mainMinutesAndDate) +
                dayname + " " + day + getResources().getString(R.string.mainOf) +
                month + getResources().getString(R.string.mainOf) + year;
        return textStatus;
    }
    public int batteryLevel()
    {
        Intent batteryIntent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        if(level == -1 || scale == -1)
        {
            return (int)50.0f;
        }
        return (int)(((float)level / (float)scale) * 100.0f);
    }
    public static boolean deviceIsAPhone()
    {
        try
        {
            if (((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getPhoneType()== TelephonyManager.PHONE_TYPE_NONE)
            {
                return false;
            }
        }
        catch(NullPointerException e)
        {
        }
        catch(Exception e)
        {
        }
        return true;
    }
    public static boolean deviceIsConnectedToMobileNetwork()
    {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (tm.getNetworkType())
        {
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
        }
        return true;
    }
    public String getCarrier()
    {
        try
        {
            TelephonyManager telephonyManager = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE));
            String carrier;
            carrier = telephonyManager.getSimOperatorName();
            if (carrier==null | carrier=="")
            {
                return getResources().getString(R.string.mainCarrierNotAvailable);
            }
            else
            {
                return carrier;
            }
        }
        catch(Exception e)
        {
            return getResources().getString(R.string.mainCarrierNotAvailable);
        }
    }
    public static boolean isWifiEnabled()
    {
        try
        {
            WifiManager wifi = (WifiManager)context.getSystemService(context.WIFI_SERVICE);
            if (wifi.isWifiEnabled())
            {
                return true;
            }
        }
        catch(Exception e)
        {
        }
        return false;
    }
    public static String getWifiSSID()
    {
        try
        {
            WifiManager wifi = (WifiManager)context.getSystemService(context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifi.getConnectionInfo();
            String name = wifiInfo.getSSID();
            name = name.replace("\"","");
            if (name!="")
            {
                return name;
            }
        }
        catch(Exception e)
        {
        }
        return "";
    }
    public String sta(){

        String t="";
        t = t +
                getResources().getString(R.string.mainBatteryChargedAt) +
                String.valueOf(batteryLevel() +
                        getResources().getString(R.string.mainPercentAndTime));

        if (deviceIsAPhone()==true)
        {
            if (deviceIsConnectedToMobileNetwork()==true)
            {
                t = t + getResources().getString(R.string.mainCarrierIs) + getCarrier();
            }
            else
            {
                t = t + getResources().getString(R.string.mainNoSignal);
            }

        }
        AudioManager audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch(audioManager.getRingerMode())
        {
            case AudioManager.RINGER_MODE_NORMAL:
                t = t + getResources().getString(R.string.mainProfileIsNormal);
                break;

            case AudioManager.RINGER_MODE_SILENT:
                t = t + getResources().getString(R.string.mainProfileIsSilent);
                break;

            case AudioManager.RINGER_MODE_VIBRATE:
                t = t + getResources().getString(R.string.mainProfileIsVibrate);
                break;

        }
        if (isWifiEnabled())
        {
            String name = getWifiSSID();
            if (name=="")
            {
                t = t + getResources().getString(R.string.mainWifiOnWithoutNetwork);
            }
            else
            {
                t = t + getResources().getString(R.string.mainWifiOnWithNetwork) + name + ".";
            }
        }
        else
        {
            t = t + getResources().getString(R.string.mainWifiOff);
        }
        return t;




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
                    if(speechtotext.get(0).equals("contacts")){
                        Intent ab= new Intent(this,contacts.class);
                        startActivity(ab);
                    }
                    else if(speechtotext.get(0).equals("date")||speechtotext.get(0).equals("time")||speechtotext.get(0).equals("date and time"))
                    {
                        talk(status());
                    }
                    else if(speechtotext.get(0).equals("status"))
                    {
                        talk(sta());
                    }
                }
                break;
            }

        }

    }
}
