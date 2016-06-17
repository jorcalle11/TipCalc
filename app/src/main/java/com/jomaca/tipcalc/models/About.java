package com.jomaca.tipcalc.models;

import android.app.Application;

/**
 * Created by jorge-calle on 15/06/16.
 */

public class About extends Application {
    private final static String ABOUT_RUL = "http://jorcalle11.github.io/";

    public String getAboutRul() {
        return ABOUT_RUL;
    }
}
