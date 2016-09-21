package com.ewebs.qrscanner.model;

/**
 * Created by PartnerPC on 2016/9/20.
 */
import android.content.Context;
import android.content.res.Resources;

import com.ewebs.qrscanner.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {
    private static final String TAG = "Config";

    public static String getConfigValue(Context context, String name) {
        Resources resources = context.getResources();

        try {
            InputStream rawResource = resources.openRawResource(R.raw.config);
            Properties properties = new Properties();
            properties.load(rawResource);
            return properties.getProperty(name);
        } catch (Resources.NotFoundException e) {
            e.getStackTrace();
        } catch (IOException e) {
            e.getStackTrace();
        }

        return null;
    }
}