package com.RamanoraGlobal.InDeoAppNew.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.RamanoraGlobal.InDeoAppNew.OtherClasses.AppStatus;
import com.RamanoraGlobal.InDeoAppNew.OtherClasses.VolleySingelton;
import com.RamanoraGlobal.InDeoAppNew.R;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CraeteMetting extends AppCompatActivity {
    Button btn_date, btn_time, btn_createmmeting;
    EditText edt_agenda, edt_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createmeeting);
        btn_date = findViewById(R.id.btn_date);
        btn_time = findViewById(R.id.btn_time);
        edt_agenda = findViewById(R.id.edt_agenda);
        edt_title = findViewById(R.id.edt_title);
        // accountid
        btn_createmmeting = findViewById(R.id.btn_createmmeting);
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(CraeteMetting.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        btn_time.setText(hourOfDay + ":" + minutes + ":" + "00");
                    }
                }, currentHour, currentMinute, true);
                timePickerDialog.show();
            }

        });
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CraeteMetting.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                int monthcheck=month+1;
                                btn_date.setText(year + "-" + monthcheck + "-" + day);
                            }
                        }, 0, 0, 0);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        btn_createmmeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(CraeteMetting.this).isOnline()) {
                    if(edt_title.getText().toString().length()==0)
                    {
                        edt_title.setError("enter this feld");
                        edt_title.requestFocus();
                    }else if(edt_agenda.getText().toString().length()==0)
                    {
                        edt_agenda.setError("enter this feld");
                        edt_agenda.requestFocus();
                    }
                    else  if(btn_date.getText().toString().equals("Select date"))
                    {
                        Toast.makeText(CraeteMetting.this, "Select Date", Toast.LENGTH_SHORT).show();
                    }else  if(btn_time.getText().toString().equals("Select Time"))
                    {
                        Toast.makeText(CraeteMetting.this, "Select Time", Toast.LENGTH_SHORT).show();

                    }else {
                        final ProgressDialog progressDialogMeeting = new ProgressDialog(CraeteMetting.this, R.style.StyledDialog);
                        //  progressDialog.setMessage("Meeting loaded ..."+countmeeting);
                        progressDialogMeeting.setMessage("Loading  ...");
                        progressDialogMeeting.show();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                "https://indeo.in/api/post/create-token.php",
                                new com.android.volley.Response.Listener<String>() {
                                    @Override

                                    public void onResponse(String response) {
                                        Log.e("Responce", response.toLowerCase());
                                        try {
                                            JSONObject js = new JSONObject(response.toString());
                                            JSONArray JsonArrayData = null;
                                            JSONArray JsonArrayDataApikey = null;
                                            String api_key = null;
                                            String logo_url = null;

                                            // JSONObject js1 = new JSONObject(response.toString());
                                            String Message = js.getString("status");
                                            if (Message.equals("insert")) {
                                                progressDialogMeeting.dismiss();
                                                Toast.makeText(CraeteMetting.this, "Meeting Created Sucessfully", Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                progressDialogMeeting.dismiss();
                                                Toast.makeText(CraeteMetting.this, "responce errore" + Message, Toast.LENGTH_SHORT).show();
                                            }


                                            progressDialogMeeting.dismiss();

// Storing the key and its value
// as the data fetched from edittext


// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        // meetstatus.setText("");
                                    }
                                },
                                new com.android.volley.Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // Toast.makeText(context, Utils.VOLLEY_ERROR_MSG, Toast.LENGTH_SHORT).show();
                                        // System.out.println("error "+error.getMessage());
                                        Log.d("error", "onErrorResponse: error email" + error.getMessage());


                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {


                                Map<String, String> params = new HashMap<String, String>();
                                @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
                                String accountid = sh.getString("accountid", "");
                                String MeetingId = sh.getString("MeetingId", "");


                                params.put("account_id", accountid);
                                params.put("moderator_id", MeetingId);
                                params.put("meeting_date", btn_date.getText().toString());
                                params.put("meeting_time", btn_time.getText().toString());
                                params.put("title", edt_title.getText().toString());
                                params.put("agenda", edt_agenda.getText().toString());
                                Log.e("Responce", params.toString());
                                return params;
                            }

                        };
                        // mRequestQueue.add(stringRequest);

                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                30000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        VolleySingelton.getInstance(CraeteMetting.this).getRequestQueue()
                                .add(stringRequest);
                    }
                }else {
                    Toast.makeText(CraeteMetting.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}