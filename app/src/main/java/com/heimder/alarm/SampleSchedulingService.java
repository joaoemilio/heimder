package com.heimder.alarm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.heimder.Heimder;
import com.heimder.domain.Contact;
import com.heimder.service.CampanhaService;
import com.heimder.service.ContactService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import heimder.com.heimder.R;

/**
 * This {@code IntentService} does the app's actual work.
 * {@code SampleAlarmReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class SampleSchedulingService extends IntentService {
    public SampleSchedulingService() {
        super("SchedulingService");
    }

    public static final String TAG = "Scheduling Demo";
    // An ID used to post the notification.
    public static final int NOTIFICATION_ID = 1;
    // The string the app searches for in the Google home page content. If the app finds 
    // the string, it indicates the presence of a doodle.  
    public static final String SEARCH_STRING = "doodle";
    // The Google home page URL from which the app fetches content.
    // You can find a list of other Google domains with possible doodles here:
    // http://en.wikipedia.org/wiki/List_of_Google_domains
    public static final String URL = "http://www.google.com";
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    @Override
    protected void onHandleIntent(Intent intent) {

        Calendar saoPauloCal = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));

        Date date = new Date();
        Integer hour = saoPauloCal.get(Calendar.HOUR_OF_DAY);
        Log.i("heimder", "hour: " + hour);
        if(hour < 9 || hour > 23 ) {
            Log.i("heimder", "horario não permitido");
            return;
        }else {
            Log.i("heimder", "hora:" + hour + " . horário dentro do período 9 as 21");
        }


        Log.i("heimder", "onHandleIntent");
        CampanhaService service = new CampanhaService();
        boolean hasNext = service.sendNextSMS();
        service.atualizarStatusCampanhas();
        if(!hasNext) {
            SampleAlarmReceiver receiver = new SampleAlarmReceiver();
            //receiver.cancelAlarm(Heimder.getInstance().getContext());
        }
    }
}
