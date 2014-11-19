package com.explore.exapp.base.util;

import android.os.Environment;
import android.util.Log;

import com.google.code.microlog4android.Logger;
import com.google.code.microlog4android.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by ryan on 14/10/31.
 */
public class LogUtil {

    public static boolean LOGGER_OPEN = true;
    private static final String TAG = "ExApp [log]";
    private static final Logger logger = LoggerFactory.getLogger(TAG);

    public static void debug(String msg) {
        Log.d(TAG, msg);
        if (LOGGER_OPEN) {
            logger.debug("##----[DEBUG]----##");
            logger.debug(msg);
        }
    }

    public static void info(String msg) {
        Log.i(TAG, msg);
        if (LOGGER_OPEN) {
            logger.debug("##----[INFO]-----##");
            logger.debug(msg);
        }
    }

    public static void error(String msg) {
        Log.e(TAG, msg);
        if (LOGGER_OPEN) {
            logger.debug("##----[ERROR]----##");
            logger.debug(msg);
        }
    }

    public static void error(Throwable ex) {
        if (LOGGER_OPEN) {
            if (ex == null) {
                return;
            }
            ex.printStackTrace();
            StringBuffer buffer = new StringBuffer();
            StackTraceElement[] traces = ex.getStackTrace();
            int length = traces.length;
            for (int i = 0; i < length; i++) {
                buffer.append(traces[i].toString());
                buffer.append("\n");
            }
            logger.debug("##----[ERROR]----##");
            logger.debug(buffer.toString());
        }
    }

    public static void createLogFile() {
        String path1 = Environment.getExternalStorageDirectory().getPath()
                + File.separator + "ExApp";
        String path2 = path1 + File.separator + "log";
        String path3 = path2 + File.separator + "log.txt";

        File file1 = new File(path1);
        if (!file1.exists()) {
            file1.mkdir();
        }

        File file2 = new File(path2);
        if (!file2.exists()) {
            file2.mkdir();
        }

        File file3 = new File(path3);
        if (!file3.exists()) {
            try {
                file3.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public static void clearLogFile() {
        String path = Environment.getExternalStorageDirectory().getPath()
                + File.separator + "ExApp"
                + File.separator + "log"
                + File.separator + "log.txt";
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

}
