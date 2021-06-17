/*
 * Author: Tommaso Apicella
 * Email: tommaso.apicella@edu.unige.it
 */

package com.example.bertapp.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.util.ArrayList;

public class FileUtils {
    public static String[] retrieveFileNames(Context context, String dir) throws IOException {
        // Retrieve all files' names in dir
        AssetManager am = context.getAssets();
        return am.list(dir);
    }

    public static ArrayList<String> convertArrayToList(String imDir, String[] fileNames) {
        ArrayList<String> temp = new ArrayList<String>();
        for (String s : fileNames) {
            temp.add(imDir + "/" + s);
        }
        return temp;
    }
}
