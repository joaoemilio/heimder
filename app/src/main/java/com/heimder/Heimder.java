package com.heimder;

import android.content.Context;

import com.heimder.dao.ConfigDAO;

/**
 * Created by JoaoEmilio on 28/12/2015.
 */
public class Heimder {

    private static Heimder me;
    private Integer interval = 5;
    private Context context;
    private Boolean running = false;

    public static Heimder getInstance() {
        if(me == null) {
            me = new Heimder();
        }
        return me;
    }

    public void setContext(Context context) {
        this.context = context;
        ConfigDAO dao = new ConfigDAO();
        me.interval = dao.getIntervalo();
    }

    public static String APP_NAME = "Heimder";


    public Context getContext() {
        return this.context;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public void setRunning(boolean b) {
        this.running = b;
    }

    public Boolean isRunning() {
        return this.running;
    }
}
