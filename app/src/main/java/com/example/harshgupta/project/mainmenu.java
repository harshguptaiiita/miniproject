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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.content.Context.TELEPHONY_SERVICE;

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
        Log.e("Kya bolu",sdf.format(Calendar.getInstance().getTime()));
        return sdf.format(Calendar.getInstance().getTime());
    }

    public static String getYear()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(Calendar.getInstance().getTime());
    }
    public static String getMonthName(int month)
    {
        Log.e("Mont", String.valueOf(month));
        String result = "";
        switch (month)
        {
            case 1:
                result = "january";//context.getResources().getString(R.string.january);
                Log.e("res",result);
                break;

            case 2:
                result = "february";//context.getResources().getString(R.string.february);
                break;

            case 3:
                result = "march";//context.getResources().getString(R.string.march);
                break;

            case 4:
                result = "april";
                break;

            case 5:
                result = "may";
                break;

            case 6:
                result = "june";
                break;

            case 7:
                result = "july";
                break;

            case 8:
                result = "august";
                break;

            case 9:
                result = "september";
                break;

            case 10:
                result = "october";
                break;

            case 11:
                result = "november";
                break;

            case 12:
                result = "december";
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
                result = "sunday";
                break;

            case 2:
                result = "monday";
                break;

            case 3:
                result = "tuesday";
                break;

            case 4:
                result = "wednesday";
                break;

            case 5:
                result = "thursday";
                break;

            case 6:
                result = "friday";
                break;

            case 7:
                result = "saturday";
                break;
        }
        return result;
    }
    public String status()
    {
        String year = getYear();
        Log.e("Year",year);

        String month = getMonthName(Integer.valueOf(getMonth()));
        Log.e("Month",month);
        String day = Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        Log.e("Date",day);
        String dayname = getDayName(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        Log.e("Dayname",dayname);
        String hour = Integer.toString(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        Log.e("Hour",hour);
        String minutes = Integer.toString(Calendar.getInstance().get(Calendar.MINUTE));
        Log.e("Minutes",minutes);
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
            Log.e("Device main","aa gya");
            if (((TelephonyManager)context.getSystemService(TELEPHONY_SERVICE)).getPhoneType()== TelephonyManager.PHONE_TYPE_NONE)
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
                "The battery is charged at" +
                String.valueOf(batteryLevel() +
                        "percent");//getResources().getString(R.string.mainPercentAndTime));
            Log.e("pehla t ",t);

        AudioManager audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch(audioManager.getRingerMode())
        {
            case AudioManager.RINGER_MODE_NORMAL:
                t = t + " the profile is normal";///getResources().getString(R.string.mainProfileIsNormal);
                break;

            case AudioManager.RINGER_MODE_SILENT:
                t = t + ", the profile is silent";//getResources().getString(R.string.mainProfileIsSilent);
                break;

            case AudioManager.RINGER_MODE_VIBRATE:
                t = t + ", the profile is vibrate";//getResources().getString(R.string.mainProfileIsVibrate);
                break;

        }
       // Log.e("pehla t2 ",t);
        if (isWifiEnabled())
        {
            String name = getWifiSSID();
            if (name=="")
            {
                t = t + "and the wifi is on and not connected to any wifi network.";//getResources().getString(R.string.mainWifiOnWithoutNetwork);
            }
            else
            {
                t = t + "and the wifi is on and connected to a wifi network named" + name + ".";
            }
        }
        else
        {
            t = t + "and the wifi is off.";//getResources().getString(R.string.mainWifiOff);
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
                    else if(speechtotext.get(0).equals("date")||speechtotext.get(0).equals("dime")||speechtotext.get(0).equals("time")||speechtotext.get(0).equals("date and time"))
                    {
                        String a=status();

                        Log.e("Time",a);

                        talk(a);
                    }


                    else if(speechtotext.get(0).equals("status")||speechtotext.get(0).equals("the status"))
                    {
                        String b= sta();
                        Log.e("Status",b);

                        talk(b);
                    }
                }
                break;
            }

        }

    }
}
