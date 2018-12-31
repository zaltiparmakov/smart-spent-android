package si.smartspent.smartspent.Profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

<<<<<<< HEAD:app/src/main/java/si/smartspent/smartspent/Profile/TransactionsTab.java
=======
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

>>>>>>> b421c6d0ab82a93413ee109e29ccadce42387287:app/src/main/java/si/smartspent/smartspent/ProfileView/TransactionsTab.java
import si.smartspent.smartspent.R;
import si.smartspent.smartspent.Utils;

public class TransactionsTab extends Fragment {
    BarChart bView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transactions_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        bView = (BarChart) getView().findViewById(R.id.barchart_trans);
        drawGraph(0);
    }

    private void drawGraph(int choice) {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> TransInterval = new ArrayList<>();
        ArrayList<Float> sumInterval = new ArrayList<>();

        JSONArray trans = new JSONArray();
        try {
            trans = new JSONArray(Utils.getUserTransactions(this.getContext())); // Gets user's Transaction data
        } catch(JSONException e) {
            //Log.wtf(e.getMessage().toString());
        }

        switch(choice) {
            case 0: // Graph for this WEEK
            {
                TransInterval.add("Monday");
                TransInterval.add("Tuesday");
                TransInterval.add("Wednesday");
                TransInterval.add("Thursday");
                TransInterval.add("Friday");
                TransInterval.add("Saturday");
                TransInterval.add("Sunday");
                Calendar c = Calendar.getInstance();
                try {
                    for (int i = 0; trans.length() > i; i++) {
                        Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(trans.get(i).toString());
                        c.setTime(date);
                        if(c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                            if(sumInterval.get(0) != null) {
                                sumInterval.set(0, sumInterval.get(0) + 1.0f);
                            } else {
                                sumInterval.add(1.0f);
                            }

                        } else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                            if(sumInterval.get(1) != null) {
                                sumInterval.set(1, sumInterval.get(1) + 1.0f);
                            } else {
                                sumInterval.add(1.0f);
                            }
                        } else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
                            if(sumInterval.get(2) != null) {
                                sumInterval.set(2, sumInterval.get(2) + 1.0f);
                            } else {
                                sumInterval.add(1.0f);
                            }

                        } else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                            if(sumInterval.get(3) != null) {
                                sumInterval.set(3, sumInterval.get(3) + 1.0f);
                            } else {
                                sumInterval.add(1.0f);
                            }

                        } else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                            if(sumInterval.get(4) != null) {
                                sumInterval.set(4, sumInterval.get(4) + 1.0f);
                            } else {
                                sumInterval.add(1.0f);
                            }

                        } else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                            if(sumInterval.get(5) != null) {
                                sumInterval.set(5, sumInterval.get(5) + 1.0f);
                            } else {
                                sumInterval.add(1.0f);
                            }

                        } else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                            if(sumInterval.get(6) != null) {
                                sumInterval.set(6, sumInterval.get(6) + 1.0f);
                            } else {
                                sumInterval.add(1.0f);
                            }
                        }
                    }
                } catch (Exception ex) {
                    //Log.wtf(ex.getMessage().toString());
                }
            }
            case 1: // Graph for this MONTH
            {
                Calendar c = Calendar.getInstance();
                int days = c.getActualMaximum(Calendar.WEEK_OF_MONTH);
                for(int i = 0; i < days; i++) {

                }
            }
            case 2: // Graph for this YEAR
            {
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
                Calendar c = Calendar.getInstance();
                try {
                    for (int i = 0; trans.length() > i; i++) {
                        Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(trans.get(i).toString());
                        c.setTime(date);
                        if(c.get(Calendar.MONTH) == Calendar.JANUARY) {
                            if(sumInterval.get(0) != null) {
                                sumInterval.set(0, sumInterval.get(0) + 1.0f);
                            } else {
                                sumInterval.add(1.0f);
                            }

                        } else if(c.get(Calendar.MONTH) == Calendar.FEBRUARY) {
                            if(sumInterval.get(1) != null) {
                                sumInterval.set(1, sumInterval.get(1) + 1.0f);
                            } else {
                                sumInterval.add(1.0f);
                            }
                        } else if(c.get(Calendar.MONTH) == Calendar.MARCH) {
                            if(sumInterval.get(2) != null) {
                                sumInterval.set(2, sumInterval.get(2) + 1.0f);
                            } else {
                                sumInterval.add(1.0f);
                            }

                        } else if(c.get(Calendar.MONTH) == Calendar.APRIL) {
                            if(sumInterval.get(3) != null) {
                                sumInterval.set(3, sumInterval.get(3) + 1.0f);
                            } else {
                                sumInterval.add(1.0f);
                            }

                        } else if(c.get(Calendar.MONTH) == Calendar.MAY) {
                            if(sumInterval.get(4) != null) {
                                sumInterval.set(4, sumInterval.get(4) + 1.0f);
                            } else {
                                sumInterval.add(1.0f);
                            }

                        } else if(c.get(Calendar.MONTH) == Calendar.JUNE) {
                            if(sumInterval.get(5) != null) {
                                sumInterval.set(5, sumInterval.get(5) + 1.0f);
                            } else {
                                sumInterval.add(1.0f);
                            }

                        } else if(c.get(Calendar.MONTH) == Calendar.JULY) {
                            if(sumInterval.get(6) != null) {
                                sumInterval.set(6, sumInterval.get(6) + 1.0f);
                            } else {
                                sumInterval.add(1.0f);
                            }

                        } else if(c.get(Calendar.MONTH) == Calendar.AUGUST) {
                            if(sumInterval.get(7) != null) {
                                sumInterval.set(7, sumInterval.get(7) + 1.0f);
                            } else {
                                sumInterval.add(1.0f);
                            }

                        } else if(c.get(Calendar.MONTH) == Calendar.SEPTEMBER) {
                            if(sumInterval.get(8) != null) {
                                sumInterval.set(8, sumInterval.get(8) + 1.0f);
                            } else {
                                sumInterval.add(1.0f);
                            }

                        } else if(c.get(Calendar.MONTH) == Calendar.OCTOBER) {
                            if(sumInterval.get(9) != null) {
                                sumInterval.set(9, sumInterval.get(9) + 1.0f);
                            } else {
                                sumInterval.add(1.0f);
                            }

                        } else if(c.get(Calendar.MONTH) == Calendar.NOVEMBER) {
                            if(sumInterval.get(10) != null) {
                                sumInterval.set(10, sumInterval.get(10) + 1.0f);
                            } else {
                                sumInterval.add(1.0f);
                            }

                        } else if(c.get(Calendar.MONTH) == Calendar.DECEMBER) {
                            if(sumInterval.get(11) != null) {
                                sumInterval.set(11, sumInterval.get(11) + 1.0f);
                            } else {
                                sumInterval.add(1.0f);
                            }
                        }
                    }
                } catch (Exception ex) {
                    //Log.wtf(ex.getMessage().toString());
                }
            }
            case 3: // Graph for ALL
            {
                JSONObject newest, oldest;
                Calendar c = Calendar.getInstance();
                newest = new JSONObject();
                oldest = new JSONObject();
                for(int i = 0; i < trans.length(); i++) {
                    Date date = new Date();
                    try {
                        date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(trans.get(i).toString());
                    }
                    catch (Exception e) {
                        //Log.wtf(ex.getMessage().toString());
                    }
                    c.setTime(date);
                    if(c.after(newest)) {
                       // newest = c.getTime();
                    }
                }
            }
            default:
            {
                Toast.makeText(getContext(), "Error Loading Graph", Toast.LENGTH_SHORT).show();
            }
        }
        for(int i = 0; i < sumInterval.size(); i++) {
            barEntries.add(new BarEntry(sumInterval.get(i),i));
        }
        BarDataSet dataSet = new BarDataSet(barEntries, "DataSet");
        BarData data = new BarData(dataSet);
        bView.setData(data);
        bView.getXAxis().setDrawGridLines(false);
        bView.setTouchEnabled(true);
    }
}
