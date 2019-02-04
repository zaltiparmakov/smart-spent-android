package si.smartspent.smartspent;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Class used for APIs and usful methods
 */
public class Utils {
    // Django backend for auth and data
    public static final String API_URL = "http://192.168.1.107:3000";
    private static final String TAG = Utils.class.getName();

    public static void setToken(Context context, String access_token) {
        context.getSharedPreferences("userData", Context.MODE_PRIVATE)
                .edit().putString("token", access_token).apply();
    }

    public static String getToken(Context context) {
        return context.getSharedPreferences("userData", Context.MODE_PRIVATE)
                .getString("token", "");
    }

    public static void logout(Context context) {
        context.getSharedPreferences("userData", Context.MODE_PRIVATE)
                .edit().clear().apply();
    }

    public static Boolean isLoggedIn(Context context) {
        return getToken(context) != "";
    }

    public static boolean isEmailValid(String email) {
        return email.contains("@");
    }

    public static boolean isPasswordValid(String password) {
        return password.length() > 6;
    }

    public static void setUserData(Context context, JSONObject data) throws JSONException {
        for(int i = 1; i < data.length(); i++) {
            SharedPreferences.Editor editor = context.getSharedPreferences("userData", Context.MODE_PRIVATE).edit();
            JSONArray keys = data.names();
            try {
                String key = keys.getString(i);
                editor.putString(key, data.getString(key));
            } catch (Exception e) {
                System.err.print(TAG + "Invalid JSON value format");
            }

            editor.apply();
        }
    }

    public static String getFullName(Context context) {
        StringBuilder fullname = new StringBuilder("");
        fullname.append(getUserData(context, "first_name"));
        fullname.append(" ");
        fullname.append(getUserData(context, "last_name"));

        return fullname.toString();
    }

    public static String getUserData(Context context, String key) {
        return context.getSharedPreferences("userData", Context.MODE_PRIVATE)
                .getString(key, "");
    }

    public static JSONArray getUserTransactions(Context context) throws JSONException{
        //JsonParser parser = new JsonParser();
        //JsonObject result = (JsonObject) parser.parse(getUserData(context, "transactions"));
        JSONArray jsonArray = new JSONArray(getUserData(context, "transactions"));
        return jsonArray;
    }

    public static int convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    public static String locationToString(Location location) {
        return Location.convert(location.getLatitude(), Location.FORMAT_DEGREES) + " " +
                Location.convert(location.getLongitude(), Location.FORMAT_DEGREES);
    }

    public static String latLngToString(LatLng location) {
        return String.valueOf(location.latitude) + " " + String.valueOf(location.longitude);
    }
}
