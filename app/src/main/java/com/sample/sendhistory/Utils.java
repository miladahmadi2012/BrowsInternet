package com.sample.sendhistory;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;

public class Utils
{
    public static String Historykey = "Historykey";
    public static final String EMAIL = "milad.ahmadi2012@gmail.com";
    public static final String PASSWORD = "Present6145!";
    public static SharedPreferences prefs = null;
    public static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 41;

    public static void init(Context context)
    {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void saveSearchModel(SearchModel model)
    {
        SharedPreferences.Editor editor = prefs.edit();
        List<SearchModel> list = getListOfSearchModel();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getAddress().equals(model.getAddress())) {
                list.remove(i);
            }
        }
        list.add(model);
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(Historykey, json);
        editor.apply();
    }

    public static ArrayList<SearchModel> getListOfSearchModel()
    {
        Gson gson = new Gson();
        String json = prefs.getString(Historykey, null);
        Type type = new TypeToken<ArrayList<SearchModel>>() {}.getType();
        ArrayList<SearchModel> result = gson.fromJson(json, type);
        if (result == null)
        {
            return new ArrayList<>();
        }
        else
        {
            return result;
        }
    }

    public static int getLastIndex()
    {
        int lastIndex = -1;
        List<SearchModel> list = getListOfSearchModel();
        if (list.size() > 0)
        {
            lastIndex = list.get(list.size() - 1).index;
        }
        return lastIndex;
    }

    public static void requestPermissionForReadExtertalStorage(Activity activity) throws Exception {
        try {
            ActivityCompat.requestPermissions(activity
                    , new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    Utils.READ_STORAGE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static  boolean checkPermissionForReadExtertalStorage(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = context.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }


//    File getFileString(Context context)
//    {
//        File tempFile = null;
//        String dirPath = context.getFilesDir().getAbsolutePath() + File.separator + "HistoryFile";
//
//        File tempFolder = new File(dirPath);
//        if (!tempFolder.exists())
//        {
//            tempFolder.mkdirs();
//        }
//        try
//        {
//            tempFile = new File(tempFolder, "history.txt");
//            FileWriter writer = new FileWriter(tempFile);
//            String text = "string send to  user by name ";
//            writer.append(text);
//            writer.flush();
//            writer.close();
//
//        }
//        catch (UnsupportedEncodingException e)
//        {
//            e.printStackTrace();
//        }
//        catch (FileNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        finally
//        {
//            return tempFile;
//        }
//    }
}
