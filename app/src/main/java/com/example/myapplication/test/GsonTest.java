package com.example.myapplication.test;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.IOException;
import java.io.InputStream;

public class GsonTest {
    public static String TAG = "GsonTest";
    public static String run(Context context) {

        String monitoringString = parseJsonFileToString(context, "monitoring.json");
        String snapshotString = parseJsonFileToString(context, "snapshot.json");

        JsonObject monitoringObject = new Gson().fromJson(monitoringString, JsonObject.class);
        JsonObject snapshotObject = new Gson().fromJson(snapshotString, JsonObject.class);


        startMatterMerge(monitoringObject, snapshotObject);

        String result = snapshotObject.toString();
        Log.d(TAG, "result: " + result);

        return result;
    }

    // parsing start point
    public static void startMatterMerge(JsonObject monitoringObject, JsonObject snapshotObject) {
        handleJsonObject(monitoringObject, snapshotObject);
    }

    public static void handleJsonObject(JsonObject monitoringObject, JsonObject snapshotObject) {
        monitoringObject.keySet().forEach(key -> {
            JsonElement monitoring = monitoringObject.get(key);
            JsonElement snapshot = snapshotObject.get(key);
            if (isJsonObject(monitoring, snapshot)) {
                snapshotObject.add(key, monitoring);
            }
        });
    }

    public static boolean isJsonObject(JsonElement monitoringObject, JsonElement snapshotObject) {
        if (monitoringObject instanceof JsonObject) {
            handleJsonObject((JsonObject) monitoringObject, (JsonObject) snapshotObject);
            return false;
        } else {
            return true;
        }
    }

    public static String parseJsonFileToString(Context context, String jsonFile) {
        AssetManager assetManager = context.getAssets();
        String json = "";

        if (TextUtils.isEmpty(jsonFile)) {
            return json;
        }

        try {
            InputStream is = assetManager.open(jsonFile);
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return json;
    }

}

