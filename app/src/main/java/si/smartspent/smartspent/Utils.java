package si.smartspent.smartspent;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Class used for APIs and usful methods
 */
public class Utils {
    // Django backend for auth and data
    public static final String API_URL = "https://smartspent.si/api/";

    public static void setToken(Context context, String access_token) {
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                .edit().putString("token", access_token).apply();
    }

    public static String getToken(Context context) {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                .getString("token", "");
    }

    public static void logout(Context context) {
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
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
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                    .edit().putString(data.names().getString(i), (String) data.get(data.names().getString(i)))
                    .apply();
        }
    }

    public static String getUsername(Context context) {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                .getString("username", "");
    }

    public static String getEmail(Context context) {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                .getString("email", "");
    }

    public static String getFirstname(Context context) {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                .getString("first_name", "");
    }

    public static String getLastname(Context context) {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                .getString("last_name", "");
    }

    public static String getFullName(Context context) {
        StringBuilder fullname = null;
        fullname.append(getFirstname(context));
        fullname.append(" ");
        fullname.append(getLastname(context));

        return fullname.toString();
    }
}
