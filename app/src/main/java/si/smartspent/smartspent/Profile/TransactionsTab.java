package si.smartspent.smartspent.Profile;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

//<<<<<<< HEAD:app/src/main/java/si/smartspent/smartspent/Profile/TransactionsTab.java
//=======
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.ProtocolException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.json.simple.parser.JSONParser;

//>>>>>>> b421c6d0ab82a93413ee109e29ccadce42387287:app/src/main/java/si/smartspent/smartspent/ProfileView/TransactionsTab.java
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import si.smartspent.smartspent.R;
import si.smartspent.smartspent.Transactions.Transaction;
import si.smartspent.smartspent.Utils;

import static org.greenrobot.eventbus.EventBus.TAG;
import static si.smartspent.smartspent.Utils.API_URL;

public class TransactionsTab extends Fragment {
    BarChart bView;
    MultiStateToggleButton btn_Choice;
    private org.json.simple.JSONArray jsonArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transactions_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        bView = (BarChart) getView().findViewById(R.id.barchart_trans);
        btn_Choice = (MultiStateToggleButton) getView().findViewById(R.id.multi_choice);
        CharSequence[] options = new CharSequence[]{"Weeks", "Months", "This Year"};
        new TransactionsTab.TransactionsDataTask().execute((Void) null);
        btn_Choice.setElements(options);
        btn_Choice.setOnValueChangedListener(new org.honorato.multistatetogglebutton.ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                bView.clearValues();
                bView.clear();
                drawGraph(btn_Choice.getValue());
            }
        });
        drawGraph(0);
        btn_Choice.setValue(0);
    }

    private class TransactionsDataTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            final OkHttpClient client;

            try {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                client = builder.build();

                Request request = new Request.Builder()
                        .header("Content-Type", "application/json")
                        .url(API_URL + "/transactions")
                        .get()
                        .build();

                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String jsonString = response.body().string();
                    // parse JSON array string with transactions to a JSONArray
                    jsonArray = (org.json.simple.JSONArray) (new JSONParser()).parse(jsonString);
                    return true;
                }
            } catch (IOException e) {
                Log.e(TAG, "Could not execute HTTP request.");
            } catch (org.json.simple.parser.ParseException e) {
                Log.e(TAG, "Error while parsing JSON string.");
            }
            return false;
        }
    }

    private void drawGraph(int choice) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> TransInterval = new ArrayList<>();
        ArrayList<Float> sumInterval = new ArrayList<>();

        ArrayList<String> dates = new ArrayList<>();
        ArrayList<Double> amounts = new ArrayList<>();

        if(jsonArray != null) {
            for (int i = 0; i < jsonArray.size(); i++) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                    String date = jsonObject.getString("date");
                    dates.add(date);
                    Double amount = jsonObject.getDouble("amount");
                    amounts.add(amount);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if(choice == 0) {
                TransInterval.clear();
                TransInterval.add("Monday");
                TransInterval.add("Tuesday");
                TransInterval.add("Wednesday");
                TransInterval.add("Thursday");
                TransInterval.add("Friday");
                TransInterval.add("Saturday");
                TransInterval.add("Sunday");

                for(int i = 0; i < TransInterval.size(); i++) {
                    sumInterval.add(0.0f);
                }

                Calendar c = Calendar.getInstance();
                try {
                    for (int i = 0; dates.size() > i; i++) {
                        Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(dates.get(i));
                        c.setTime(date);
                        if(c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                            sumInterval.set(0, sumInterval.get(0) + amounts.get(i).floatValue());
                        }
                        else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                            sumInterval.set(1, sumInterval.get(1) + amounts.get(i).floatValue());
                        }
                        else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                            sumInterval.set(2, sumInterval.get(2) + amounts.get(i).floatValue());
                        }
                        else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                            sumInterval.set(3, sumInterval.get(3) + amounts.get(i).floatValue());
                        }
                        else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                            sumInterval.set(4, sumInterval.get(4) + amounts.get(i).floatValue());
                        }
                        else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                            sumInterval.set(5, sumInterval.get(5) + amounts.get(i).floatValue());
                        }
                        else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                            sumInterval.set(6, sumInterval.get(6) + amounts.get(i).floatValue());
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else if(choice == 1)
            {
                TransInterval.clear();
                TransInterval.add("January");
                TransInterval.add("February");
                TransInterval.add("March");
                TransInterval.add("April");
                TransInterval.add("May");
                TransInterval.add("June");
                TransInterval.add("July");
                TransInterval.add("August");
                TransInterval.add("September");
                TransInterval.add("October");
                TransInterval.add("November");
                TransInterval.add("December");

                for (int i = 0; i < TransInterval.size(); i++) {
                    sumInterval.add(0.0f);
                }

                Calendar c = Calendar.getInstance();
                try {
                    for (int i = 0; dates.size() > i; i++) {
                        Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(dates.get(i));
                        c.setTime(date);
                        if (c.get(Calendar.MONTH) == Calendar.JANUARY) {
                            sumInterval.set(0, sumInterval.get(0) + amounts.get(i).floatValue());
                        } else if (c.get(Calendar.MONTH) == Calendar.FEBRUARY) {
                            sumInterval.set(1, sumInterval.get(1) + amounts.get(i).floatValue());
                        } else if (c.get(Calendar.MONTH) == Calendar.MARCH) {
                            sumInterval.set(2, sumInterval.get(2) + amounts.get(i).floatValue());
                        } else if (c.get(Calendar.MONTH) == Calendar.APRIL) {
                            sumInterval.set(3, sumInterval.get(3) + amounts.get(i).floatValue());
                        } else if (c.get(Calendar.MONTH) == Calendar.MAY) {
                            sumInterval.set(4, sumInterval.get(4) + amounts.get(i).floatValue());
                        } else if (c.get(Calendar.MONTH) == Calendar.JUNE) {
                            sumInterval.set(5, sumInterval.get(5) + amounts.get(i).floatValue());
                        } else if (c.get(Calendar.MONTH) == Calendar.JULY) {
                            sumInterval.set(6, sumInterval.get(6) + amounts.get(i).floatValue());
                        } else if (c.get(Calendar.MONTH) == Calendar.AUGUST) {
                            sumInterval.set(7, sumInterval.get(7) + amounts.get(i).floatValue());
                        } else if (c.get(Calendar.MONTH) == Calendar.SEPTEMBER) {
                            sumInterval.set(8, sumInterval.get(8) + amounts.get(i).floatValue());
                        } else if (c.get(Calendar.MONTH) == Calendar.OCTOBER) {
                            sumInterval.set(9, sumInterval.get(9) + amounts.get(i).floatValue());
                        } else if (c.get(Calendar.MONTH) == Calendar.NOVEMBER) {
                            sumInterval.set(10, sumInterval.get(10) + amounts.get(i).floatValue());
                        } else if (c.get(Calendar.MONTH) == Calendar.DECEMBER) {
                            sumInterval.set(11, sumInterval.get(11) + amounts.get(i).floatValue());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(choice == 2)
            {
                TransInterval.clear();
                TransInterval.add("January");
                TransInterval.add("February");
                TransInterval.add("March");
                TransInterval.add("April");
                TransInterval.add("May");
                TransInterval.add("June");
                TransInterval.add("July");
                TransInterval.add("August");
                TransInterval.add("September");
                TransInterval.add("October");
                TransInterval.add("November");
                TransInterval.add("December");

                for (int i = 0; i < TransInterval.size(); i++) {
                    sumInterval.add(0.0f);
                }

                Calendar c = Calendar.getInstance();
                try {
                    for (int i = 0; dates.size() > i; i++) {
                        Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(dates.get(i));
                        c.setTime(date);
                        DateFormat dateFormat = new SimpleDateFormat("yyyy");
                        if (c.get(Calendar.MONTH) == Calendar.JANUARY && c.get(Calendar.YEAR) == Integer.parseInt(dateFormat.format(new Date()))) {
                            sumInterval.set(0, sumInterval.get(0) + amounts.get(i).floatValue());
                        } else if (c.get(Calendar.MONTH) == Calendar.FEBRUARY && c.get(Calendar.YEAR) == Integer.parseInt(dateFormat.format(new Date()))) {
                            sumInterval.set(1, sumInterval.get(1) + amounts.get(i).floatValue());
                        } else if (c.get(Calendar.MONTH) == Calendar.MARCH && c.get(Calendar.YEAR) == Integer.parseInt(dateFormat.format(new Date())))  {
                            sumInterval.set(2, sumInterval.get(2) + amounts.get(i).floatValue());
                        } else if (c.get(Calendar.MONTH) == Calendar.APRIL && c.get(Calendar.YEAR) == Integer.parseInt(dateFormat.format(new Date()))) {
                            sumInterval.set(3, sumInterval.get(3) + amounts.get(i).floatValue());
                        } else if (c.get(Calendar.MONTH) == Calendar.MAY && c.get(Calendar.YEAR) == Integer.parseInt(dateFormat.format(new Date()))) {
                            sumInterval.set(4, sumInterval.get(4) + amounts.get(i).floatValue());
                        } else if (c.get(Calendar.MONTH) == Calendar.JUNE && c.get(Calendar.YEAR) == Integer.parseInt(dateFormat.format(new Date()))) {
                            sumInterval.set(5, sumInterval.get(5) + amounts.get(i).floatValue());
                        } else if (c.get(Calendar.MONTH) == Calendar.JULY && c.get(Calendar.YEAR) == Integer.parseInt(dateFormat.format(new Date()))) {
                            sumInterval.set(6, sumInterval.get(6) + amounts.get(i).floatValue());
                        } else if (c.get(Calendar.MONTH) == Calendar.AUGUST && c.get(Calendar.YEAR) == Integer.parseInt(dateFormat.format(new Date()))) {
                            sumInterval.set(7, sumInterval.get(7) + amounts.get(i).floatValue());
                        } else if (c.get(Calendar.MONTH) == Calendar.SEPTEMBER && c.get(Calendar.YEAR) == Integer.parseInt(dateFormat.format(new Date()))) {
                            sumInterval.set(8, sumInterval.get(8) + amounts.get(i).floatValue());
                        } else if (c.get(Calendar.MONTH) == Calendar.OCTOBER && c.get(Calendar.YEAR) == Integer.parseInt(dateFormat.format(new Date()))) {
                            sumInterval.set(9, sumInterval.get(9) + amounts.get(i).floatValue());
                        } else if (c.get(Calendar.MONTH) == Calendar.NOVEMBER && c.get(Calendar.YEAR) == Integer.parseInt(dateFormat.format(new Date()))) {
                            sumInterval.set(10, sumInterval.get(10) + amounts.get(i).floatValue());
                        } else if (c.get(Calendar.MONTH) == Calendar.DECEMBER && c.get(Calendar.YEAR) == Integer.parseInt(dateFormat.format(new Date()))) {
                            sumInterval.set(11, sumInterval.get(11) + amounts.get(i).floatValue());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        for(int i = 0; i < sumInterval.size(); i++) {
            barEntries.add(new BarEntry(i,sumInterval.get(i)));
        }
        BarDataSet dataSet = new BarDataSet(barEntries, "Transactions");
        BarData data = new BarData(dataSet);
        bView.setData(data);
        bView.getXAxis().setDrawGridLines(false);
        bView.getXAxis().setValueFormatter(new IndexAxisValueFormatter(TransInterval));
        //bView.getXAxis().setGranularity(1f);
        //bView.getXAxis().setGranularityEnabled(true);
        bView.setTouchEnabled(true);
    }
}
