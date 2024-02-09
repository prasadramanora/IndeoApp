package com.RamanoraGlobal.InDeoAppNew.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.RamanoraGlobal.InDeoAppNew.R;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.RamanoraGlobal.InDeoAppNew.ModelClass.ModelClass;
import com.RamanoraGlobal.InDeoAppNew.OtherClasses.AppStatus;
import com.RamanoraGlobal.InDeoAppNew.OtherClasses.VolleySingelton;

import com.RamanoraGlobal.InDeoAppNew.AdapterClasses.MeetingAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MeetingSheduleScreen extends AppCompatActivity implements View.OnClickListener, MeetingAdapter.ListItemClickListener {
    RecyclerView my_recycler_view;
    String usernameserver;
    Button btnshedule, btn_createmeeting;
    String checkcameratype;
    String ServerTime;
    ImageView logo;
    private EditText roomId;
    private String room_Id = "", accountid;
    private String sendsertverid, meetingidnew, checkString = "No";
    ArrayList<ModelClass> meetinglist;
    public static String MeetingTokan, Meetingsession, api_keyApp, CheckCameraMode = "No";
    String CameraType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meetingschedule);
        btn_createmeeting = findViewById(R.id.btn_createmeeting);
        btn_createmeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CraeteMetting.class);
                startActivity(i);

            }
        });
        roomId = findViewById(R.id.roomId);
        logo = findViewById(R.id.logo);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);
        my_recycler_view.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        my_recycler_view.setLayoutManager(manager);
        btnshedule = findViewById(R.id.btnshedule);
        btnshedule.setOnClickListener(this);

        TextView cameramode = findViewById(R.id.cameramode);
        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
        String logo_url = sh.getString("logo_url", "");
        Glide
                .with(this)
                .load(logo_url)
                .centerCrop()
                .fitCenter()
                .into(logo);
        try {
            cameramode.setVisibility(View.VISIBLE);

            String api_key = sh.getString("api_key", "");
            api_keyApp = api_key;
            CameraType = sh.getString("CameraType", "");
            cameramode.setText(CameraType);
            accountid = sh.getString("accountid", "");


            Log.e("cameraMode", CameraType);
            cameramode.setText("You are Logged In With " + CameraType);
        } catch (Exception e) {
            cameramode.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);
        if (menu instanceof MenuBuilder) {
            MenuBuilder menuBuilder = (MenuBuilder) menu;
//            menuBuilder.setOptionalIconsVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            final Dialog dialogselect = new Dialog(MeetingSheduleScreen.this);
            dialogselect.setContentView(R.layout.exitdialog);
            dialogselect.show();
            Button btn_ok = dialogselect.findViewById(R.id.btn_ok);
            Button btn_cancel = dialogselect.findViewById(R.id.btn_cancel);
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences
                            = getSharedPreferences("MySharedPref",
                            MODE_PRIVATE);

// Creating an Editor object
// to edit(write to the file)
                    SharedPreferences.Editor myEdit
                            = sharedPreferences.edit();

// Storing the key and its value
// as the data fetched from edittext
                    myEdit.putString(
                            "FirstTimeLogin",
                            "Yes");


// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
                    myEdit.commit();
                    Intent i = new Intent(getApplicationContext(), LoginScreen.class);
                    startActivity(i);
                    finish();
                }
            });
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogselect.dismiss();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == btnshedule) {
            if (AppStatus.getInstance(MeetingSheduleScreen.this).isOnline()) {
                final ProgressDialog progressDialogMeeting = new ProgressDialog(MeetingSheduleScreen.this);
                //  progressDialog.setMessage("Meeting loaded ..."+countmeeting);
                progressDialogMeeting.setMessage("Load Meetings...");
                progressDialogMeeting.show();
                @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);

                String MeetingId = sh.getString("MeetingId", "");
                String url = "https://indeo.in/api/get/meetings.php?moderator_id=" + MeetingId;
                Log.e("UrlMeetings", url);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("test", "onResponseUpdate: ConnectR response" + response);
                        try {
                            String status = response.getString("status");
                            String message = response.getString("message");
                            ArrayList<String> checkmeeting = new ArrayList<>();
                               /* SQLiteDatabase db = openOrCreateDatabase("MeetingDb", Context.MODE_PRIVATE, null);
                                db.execSQL("CREATE TABLE IF NOT EXISTS RemovemeetingTable(rollno VARCHAR,name VARCHAR);");
                                Cursor c = db.rawQuery("SELECT * FROM RemovemeetingTable WHERE rollno='" + "Meetingtime" + "'", null);
                               try {
                                   if (c.moveToFirst()) {
                                       // editName.setText(c.getString(1));
                                       checkmeeting.add(c.getString(1));

                                   }
                               }catch (Exception e)
                               {
                                   e.printStackTrace();
                               }*/
                            if (status.equalsIgnoreCase("true")) {
                                JSONArray JsonArrayData = null;
                                meetinglist = new ArrayList<>();
                                try {
                                    JsonArrayData = response.getJSONArray("Data");


                                    for (int i = 0; i < JsonArrayData.length(); i++) {

                                        JSONObject jsonObject = JsonArrayData.getJSONObject(i);
                                        ModelClass modelClass = new ModelClass();
                                      /*  String tokenv = jsonObject.getString("tokenv");
                                        modelClass.setToakn(tokenv);*/
                                        String shopname = jsonObject.getString("shopname");
                                        modelClass.setShopname(shopname);
                                        String meetingstatus = jsonObject.getString("meetingstatus");
                                        modelClass.setMeetingstatus(meetingstatus);

                                        String meeting_id = jsonObject.getString("meeting_id");
                                        Log.e("MEttingId", meeting_id);
                                        modelClass.setMeeting_id(meeting_id);
                                        String meeting_date = jsonObject.getString("meeting_date");
                                        modelClass.setMettingdate(meeting_date);
                                        String meeting_time = jsonObject.getString("meeting_time");
                                        String enablex_id = jsonObject.getString("enablex_id");
                                        modelClass.setEnablexid(enablex_id);

                                        modelClass.setMeetingtime(meeting_time);

                                        meetinglist.add(modelClass);


                                        Log.e("enablex_id", enablex_id);
                                    }
                                    MeetingAdapter meetingAdapter = new MeetingAdapter(MeetingSheduleScreen.this, meetinglist, MeetingSheduleScreen.this);
                                    my_recycler_view.setAdapter(meetingAdapter);
                                    progressDialogMeeting.dismiss();
                                } catch (JSONException e) {
                                    progressDialogMeeting.dismiss();
                                    Toast.makeText(MeetingSheduleScreen.this, "No Meetings Scheduled", Toast.LENGTH_SHORT).show();

                                    e.printStackTrace();
                                }

                            } else if (status.equalsIgnoreCase("false")) {
                                progressDialogMeeting.dismiss();
                                Toast.makeText(MeetingSheduleScreen.this, "No Meetings Scheduled", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });


                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        3000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                VolleySingelton.getInstance(MeetingSheduleScreen.this).getRequestQueue()
                        .add(jsonObjectRequest);


                // updateLocalDataBase();


            } else {
                Toast.makeText(MeetingSheduleScreen.this, "No internet Connection", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onListItemClick(View clickedItemIndex) {
        Button btnstart = (Button) clickedItemIndex.findViewById(R.id.btnstart);
        TextView tv_meetingid = (TextView) clickedItemIndex.findViewById(R.id.tv_meetingid);
        TextView tv_date = (TextView) clickedItemIndex.findViewById(R.id.tv_date);
        TextView tv_time = (TextView) clickedItemIndex.findViewById(R.id.tv_time);
        TextView tv_enableid = (TextView) clickedItemIndex.findViewById(R.id.tv_enableid);
        ImageView share = (ImageView) clickedItemIndex.findViewById(R.id.share);
        Log.e("MeetingId", tv_meetingid.getText().toString());
        Log.e("MeetingIdnew", tv_enableid.getText().toString());
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv_meetingid = (TextView) clickedItemIndex.findViewById(R.id.tv_meetingid);

                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide
                // what to do with it.
                share.putExtra(Intent.EXTRA_SUBJECT, "InDeo Share Link");
                share.putExtra(Intent.EXTRA_TEXT, "Hello,\n" +
                        "Please join  InDeo Session using the." + "\n" +
                        "Joining Link:" + "https://indeo.in/client/?meetingid=" + tv_meetingid.getText().toString() +
                        "\n" +
                        "Allow permissions for Audio and Video for seamless experience.\n" +
                        "Thank You!...");

                startActivity(Intent.createChooser(share, "InDeo Share Link"));
               /* Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hello,\n" +
                        "Please join the InDeo using the following details.\n" +
                        "Joining Link: googlechrome://navigate?url=" + "https://indeo.in/connect/" + tv_meetingid.getText().toString() +
                        "\n" +
                        "Thank You...");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Meeting Invite");
                startActivity(Intent.createChooser(sharingIntent, "Share Meeting Invite"));*/
            }
        });

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* final Dialog dialogselect = new Dialog(MeetingSheduleScreen.this);
                dialogselect.setContentView(R.layout.choose);
//                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogselect.setCancelable(false);
                dialogselect.show();
                RadioGroup radioGroup = (RadioGroup) dialogselect.findViewById(R.id.radioButtonGroup);

                RadioGroup radioGroup1 = (RadioGroup) dialogselect.findViewById(R.id.radioGroup);
                radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        try {
                            int selectedId = radioGroup1.getCheckedRadioButtonId();
                            RadioButton genderradioButton = (RadioButton) findViewById(selectedId);
                             checkcameratype=genderradioButton.getText().toString();
                             Log.e("MyCamera",checkcameratype);
                            //Toast.makeText(LoginScreen.this,genderradioButton.getText(), Toast.LENGTH_SHORT).show();
                            Log.e("CheckType", genderradioButton.getText() + "");
                            // name.setText(genderradioButton.getText() + "");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                Button btndone = (Button) dialogselect.findViewById(R.id.done);*/
               /* btndone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int selectedId = radioGroup1.getCheckedRadioButtonId();
                        RadioButton genderradioButton = (RadioButton)dialogselect. findViewById(selectedId);
                        Log.e("CheckLeanght", selectedId + "");
                        if (selectedId == -1) {
                            Toast.makeText(MeetingSheduleScreen.this, "Select camera Type", Toast.LENGTH_SHORT).show();
                        } else {

                            dialogselect.dismiss();

                        }


                    }
                });
*/

                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        "https://indeo.in/admin/gettoken.php",
                        new Response.Listener<String>() {
                            @Override

                            public void onResponse(String response) {

                                RequestQueue queue = Volley.newRequestQueue(MeetingSheduleScreen.this);
                                String status;
                                String token;

                                try {
                                    JSONObject objStatus1 = new JSONObject(response.toString());
                                    Log.d("test", "onResponse: Update Data : ==>>" + response);
                                    Log.d("test", "onResponse: Update Data : ==>>" + response);

                                    status = objStatus1.getString("status");
                                    token = objStatus1.getString("token");
                                    MeetingTokan = token;

                                    if (status.equalsIgnoreCase("true")) {











                                       /* SharedPreferences sharedPreferences
                                                = getSharedPreferences("MySharedPref",
                                                MODE_PRIVATE);

// Creating an Editor object
// to edit(write to the file)
                                        SharedPreferences.Editor myEdit
                                                = sharedPreferences.edit();

// Storing the key and its value
// as the data fetched from edittext
                                        myEdit.putString(
                                                "CameraType",
                                                genderradioButton.getText().toString());

                                        checkcameratype = genderradioButton.getText().toString();
// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
                                        myEdit.commit();*/
                                        Log.e("MeetingId", tv_meetingid.getText().toString());
                                        //Log.e("MeetingIdnew", tv_enableid.getText().toString());
                                        //  MeetingTokan = tv_meetingid.getText().toString();
                                        Meetingsession = tv_enableid.getText().toString();
              /*  MeetingTokan = "T1==cGFydG5lcl9pZD00Njc4NzU4NCZzaWc9ZDEyNDljYWFhNDNjNmMxMzQ5NDkyYzcwOWM2ZDYwNTJiZjI5NDdiNDpzZXNzaW9uX2lkPTJfTVg0ME5qYzROelU0Tkg1LU1UWXhPREl4TURJMk1qTTRPSDVwT1dKdWNrMTVlRzkzY1hOUU5HWXJlbVU1VTNCTWVVWi1mZyZjcmVhdGVfdGltZT0xNjE4MjEwNTA1Jm5vbmNlPTAuMTQ5MDkyMjk2Mzc1MTI2OSZyb2xlPXB1Ymxpc2hlciZleHBpcmVfdGltZT0xNjE4ODE1MzAzJmluaXRpYWxfbGF5b3V0X2NsYXNzX2xpc3Q9";
                Meetingsession = "2_MX40Njc4NzU4NH5-MTYxODIxMDI2MjM4OH5pOWJuck15eG93cXNQNGYremU5U3BMeUZ-fg";*/
                                        Calendar cc = Calendar.getInstance();
                                        SimpleDateFormat sdff = new SimpleDateFormat("HH:mm");
                                        String getCurrentTime = sdff.format(cc.getTime());


                                        if (getCurrentTime.compareTo(tv_time.getText().toString()) < 0) {
                                            // Do your staff
                                            Log.d("Return", "getTestTime less than getCurrentTime ");
                                        } else {
                                            Log.d("Return", "getTestTime older than getCurrentTime ");
                                        }
                                        queue = Volley.newRequestQueue(MeetingSheduleScreen.this);

                                        TextView tv_enableid = (TextView) clickedItemIndex.findViewById(R.id.tv_enableid);
                                        // Toast.makeText(MeetingSheduleScreen.this, ""+tv_enableid.getText().toString(), Toast.LENGTH_SHORT).show();
                                        ServerTime = tv_time.getText().toString();
                                        roomId = findViewById(R.id.roomId);
                                        //   roomId.setText(tv_enableid.getText().toString());

                                        sendsertverid = tv_enableid.getText().toString();
                                        meetingidnew = tv_meetingid.getText().toString();
                                        // new HttpAsyncTask().execute(" https://leadcon.co/conectr/client/api/post/getroomstatus1.php");


                                        Log.e("Sendserverid ", tv_enableid.getText().toString());
                                        SharedPreferences sharedPreferences
                                                = getSharedPreferences("MySharedPref",
                                                MODE_PRIVATE);

// Creating an Editor object
// to edit(write to the file)
                                        SharedPreferences.Editor myEdit
                                                = sharedPreferences.edit();
                                        myEdit.putString(
                                                "MeetingidCheck",
                                                tv_meetingid.getText().toString());
// Storing the key and its value
// as the data fetched from edittext

                                        myEdit.putString(
                                                "Tokan",
                                                MeetingTokan);
                                        myEdit.putString(
                                                "Sessionid",
                                                tv_enableid.getText().toString());

// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
                                        myEdit.commit();
                                        // roomId.setText(tv_enableid.getText().toString());

                                        room_Id = tv_enableid.getText().toString();

                                        Log.e("MeetingCheckId", tv_meetingid.getText().toString());

                                        ProgressDialog progressDialogMeeting = new ProgressDialog(MeetingSheduleScreen.this);
                                        //  progressDialog.setMessage("Meeting loaded ..."+countmeeting);
                                        progressDialogMeeting.setMessage("Meeting  Status...");
                                        progressDialogMeeting.show();
                                        ProgressDialog finalProgressDialogMeeting = progressDialogMeeting;
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                                "https://indeo.in/api/post/getroomstatus1.php",
                                                new com.android.volley.Response.Listener<String>() {
                                                    @Override

                                                    public void onResponse(String response) {
                                                        Log.e("PARAMSEND2", response.toString());
                                                        // meetstatus.setText("");
                                                    }
                                                },
                                                new com.android.volley.Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        // Toast.makeText(context, Utils.VOLLEY_ERROR_MSG, Toast.LENGTH_SHORT).show();
                                                        // System.out.println("error "+error.getMessage());
                                                        Log.d("error", "onErrorResponse: error email" + error.getMessage());
                                                        finalProgressDialogMeeting.dismiss();

                                                    }
                                                }) {
                                            @Override
                                       /* protected Map<String, String> getParams() throws AuthFailureError {

                                            Map<String, String> params = new HashMap<String, String>();

                                            params.put("roomid", tv_enableid.getText().toString());

                                            DashboardActivity.this.runOnUiThread(new Runnable() {
                                                public void run() {
                                                    //  Toast.makeText(DashboardActivity.this, "pass"+tv_enableid.getText().toString(), Toast.LENGTH_SHORT).show();
                                                    Log.d("UI thread", "I am the UI thread");
                                                }
                                            });
                                            Log.e("StatusParam", params.toString());*/
                                            protected Map<String, String> getParams() {
                                                Map<String, String> params = new HashMap<String, String>();
                                                params.put("roomid", tv_enableid.getText().toString());
                                                Log.e("PARAMSEND1", params.toString());
                                                return params;

                                            }

                                        };
                                        queue.add(stringRequest);

                                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                                30000,
                                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                        VolleySingelton.getInstance(MeetingSheduleScreen.this).getRequestQueue()
                                                .add(stringRequest);

                                        room_Id = tv_enableid.getText().toString();

                                        Log.e("checkLog", "1");

                                        Date c = Calendar.getInstance().getTime();
                                        System.out.println("Current time => " + c);

                                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                        String formattedDate = df.format(c);
                                        Log.e("CurrantDate", formattedDate);

                                        Date d = new Date();
                                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                        String currentDateTimeString = sdf.format(d);
                                        Log.e("CurrantDate", currentDateTimeString);
                                        Log.e("MeetingTime", tv_time.getText().toString());

                                        String[] separated1 = tv_time.getText().toString().split(":");
                                        int hors1 = Integer.parseInt(separated1[0]);
                                        int mini1 = Integer.parseInt(separated1[1]);
                                        Log.e("ServerTime", Integer.parseInt(separated1[0]) + "");
                                        Log.e("ServerTime", Integer.parseInt(separated1[1]) + "");
                                        Log.e("ServerTime", Integer.parseInt(separated1[2]) + "");
                                        String[] separated = currentDateTimeString.split(":");
                                        int hors = Integer.parseInt(separated[0]);
                                        int mini = Integer.parseInt(separated[1]);
                                        Log.e("ServerTime1", btnstart.getText().toString() + "");
                                        if (btnstart.getText().toString().equals("join")) {
                                            room_Id = tv_enableid.getText().toString();

                                            Log.e("MeetingCheckId111", tv_meetingid.getText().toString());
                                          /*  final Dialog dialog1 = new Dialog(MeetingSheduleScreen.this);
                                            dialog1.setContentView(R.layout.savebaatry);
                                            //   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            dialog1.setCancelable(false);
                                            if (CameraType.equals("Presenter Camera")) {
                                                dialog1.show();
                                            }else {
                                                Intent i = new Intent(getApplicationContext(), ThreeCamera.class);
                                                startActivity(i);
                                                finish();
                                            }*/
                                            if (CameraType.equals("Presenter Camera")) {
                                                Intent i = new Intent(getApplicationContext(), VedioConfranceScreen.class);
                                                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                                                java.util.Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                                                if (pairedDevices == null || pairedDevices.size() == 0) {
                                                    //showToast("No Paired Devices Found");
                                                } else {
                                                    ArrayList<BluetoothDevice> list = new ArrayList<BluetoothDevice>();

                                                    list.addAll(pairedDevices);


                                                    i.putParcelableArrayListExtra("device.list", list);


                                                }
                                                startActivity(i);
                                                finish();
                                                //dialog1.show();
                                            } else if(CameraType.equals("Screen Sharing")){
                                                Intent i = new Intent(getApplicationContext(), ScreenSharing.class);
                                                startActivity(i);
                                                finish();
                                            }else {
                                                Intent i = new Intent(getApplicationContext(), ThreeCamera.class);
                                                startActivity(i);
                                                finish();
                                            }
                                          /*  Button btn_yes=dialog1.findViewById(R.id.btn_yes);
                                            Button btn_no=dialog1.findViewById(R.id.btn_no);
                                            btn_yes.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if (CameraType.equals("Presenter Camera")) {
                                                        CheckCameraMode="Yes";
                                                        Intent i = new Intent(getApplicationContext(), VedioConfranceScreen.class);
                                                        BluetoothAdapter  mBluetoothAdapter	= BluetoothAdapter.getDefaultAdapter();
                                                        java.util.Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                                                        if (pairedDevices == null || pairedDevices.size() == 0) {
                                                            //showToast("No Paired Devices Found");
                                                        } else {
                                                            ArrayList<BluetoothDevice> list = new ArrayList<BluetoothDevice>();

                                                            list.addAll(pairedDevices);


                                                            i.putParcelableArrayListExtra("device.list", list);


                                                        }
                                                        startActivity(i);
                                                        finish();
                                                    } else {
                                                        Intent i = new Intent(getApplicationContext(), ThreeCamera.class);
                                                        startActivity(i);
                                                        finish();
                                                    }
                                                }
                                            });*/
                                         /*   btn_no.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if (CameraType.equals("Presenter Camera")) {
                                                        CheckCameraMode="No";
                                                        Intent i = new Intent(getApplicationContext(), VedioConfranceScreen.class);
                                                        BluetoothAdapter  mBluetoothAdapter	= BluetoothAdapter.getDefaultAdapter();
                                                        java.util.Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                                                        if (pairedDevices == null || pairedDevices.size() == 0) {
                                                            //showToast("No Paired Devices Found");
                                                        } else {
                                                            ArrayList<BluetoothDevice> list = new ArrayList<BluetoothDevice>();

                                                            list.addAll(pairedDevices);


                                                            i.putParcelableArrayListExtra("device.list", list);


                                                        }
                                                        startActivity(i);
                                                        finish();
                                                    } else {
                                                        Intent i = new Intent(getApplicationContext(), ThreeCamera.class);
                                                        startActivity(i);
                                                        finish();
                                                    }
                                                }
                                            });
*/
                                            Log.e("checkLog", "2");
                                        }
                                        if (tv_date.getText().toString().equals(formattedDate)) {
                                            if (hors >= hors1) {
                                                if (mini >= mini1) {
                                                    if (btnstart.getText().toString().equals("join")) {
                                                        room_Id = tv_enableid.getText().toString();

                                                        Log.e("MeetingCheckId111", tv_meetingid.getText().toString());
                                                        if (CameraType.equals("Presenter Camera")) {
                                                            Intent i = new Intent(getApplicationContext(), VedioConfranceScreen.class);
                                                            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                                                            java.util.Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                                                            if (pairedDevices == null || pairedDevices.size() == 0) {
                                                                //showToast("No Paired Devices Found");
                                                            } else {
                                                                ArrayList<BluetoothDevice> list = new ArrayList<BluetoothDevice>();

                                                                list.addAll(pairedDevices);


                                                                i.putParcelableArrayListExtra("device.list", list);


                                                            }
                                                            startActivity(i);
                                                            finish();
                                                        } else if(CameraType.equals("Screen Sharing")){
                                                            Intent i = new Intent(getApplicationContext(), ScreenSharing.class);
                                                            startActivity(i);
                                                            finish();
                                                        }else {
                                                            Intent i = new Intent(getApplicationContext(), ThreeCamera.class);
                                                            startActivity(i);
                                                            finish();
                                                        }

                                                       /* final Dialog dialog1 = new Dialog(MeetingSheduleScreen.this);
                                                        dialog1.setContentView(R.layout.savebaatry);
                                                        //   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                        dialog1.setCancelable(false);
                                                        if (CameraType.equals("Presenter Camera")) {
                                                            dialog1.show();
                                                        }else {
                                                            Intent i = new Intent(getApplicationContext(), ThreeCamera.class);
                                                            startActivity(i);
                                                            finish();
                                                        }

                                                        Button btn_yes=dialog1.findViewById(R.id.btn_yes);
                                                        Button btn_no=dialog1.findViewById(R.id.btn_no);
                                                        btn_yes.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                if (CameraType.equals("Presenter Camera")) {
                                                                    CheckCameraMode="Yes";
                                                                    Intent i = new Intent(getApplicationContext(), VedioConfranceScreen.class);
                                                                    BluetoothAdapter  mBluetoothAdapter	= BluetoothAdapter.getDefaultAdapter();
                                                                    java.util.Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                                                                    if (pairedDevices == null || pairedDevices.size() == 0) {
                                                                        //showToast("No Paired Devices Found");
                                                                    } else {
                                                                        ArrayList<BluetoothDevice> list = new ArrayList<BluetoothDevice>();

                                                                        list.addAll(pairedDevices);


                                                                        i.putParcelableArrayListExtra("device.list", list);


                                                                    }
                                                                    startActivity(i);
                                                                    finish();
                                                                } else {
                                                                    Intent i = new Intent(getApplicationContext(), ThreeCamera.class);
                                                                    startActivity(i);
                                                                    finish();
                                                                }
                                                            }
                                                        });
                                                        btn_no.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                if (CameraType.equals("Presenter Camera")) {
                                                                    CheckCameraMode="No";
                                                                    Intent i = new Intent(getApplicationContext(), VedioConfranceScreen.class);
                                                                    BluetoothAdapter  mBluetoothAdapter	= BluetoothAdapter.getDefaultAdapter();
                                                                    java.util.Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                                                                    if (pairedDevices == null || pairedDevices.size() == 0) {
                                                                        //showToast("No Paired Devices Found");
                                                                    } else {
                                                                        ArrayList<BluetoothDevice> list = new ArrayList<BluetoothDevice>();

                                                                        list.addAll(pairedDevices);


                                                                        i.putParcelableArrayListExtra("device.list", list);


                                                                    }
                                                                    startActivity(i);
                                                                    finish();
                                                                } else {
                                                                    Intent i = new Intent(getApplicationContext(), ThreeCamera.class);
                                                                    startActivity(i);
                                                                    finish();
                                                                }
                                                            }
                                                        });*/
                                                        Log.e("checkLog", "2");
                                                    } else {

                                                        final Dialog dialog = new Dialog(MeetingSheduleScreen.this);
                                                        dialog.setContentView(R.layout.meetingstartdialog);
                                                        //   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                        dialog.setCancelable(false);
                                                        TextView tv_message = (TextView) dialog.findViewById(R.id.tv_message);
                                                        tv_message.setText("Are you sure that you want to start this meeting?");
                                                        ImageView logo = (ImageView) dialog.findViewById(R.id.logo);
                                                        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);

                                                        String logo_url = sh.getString("logo_url", "");
                                                        Glide
                                                                .with(MeetingSheduleScreen.this)
                                                                .load(logo_url)
                                                                .centerCrop()
                                                                .fitCenter()
                                                                .into(logo);
                                                        Button dialogButton = (Button) dialog.findViewById(R.id.btnok);
                                                        Button btnno = (Button) dialog.findViewById(R.id.btnno);
                                                        btnno.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                dialog.dismiss();
                                                            }
                                                        });

                                                        // if button is clicked, close the custom dialog
                                                        dialogButton.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                room_Id = tv_enableid.getText().toString();

                                                                Log.e("MeetingCheckId", tv_meetingid.getText().toString());

                                                                if (CameraType.equals("Presenter Camera")) {
                                                                    Intent i = new Intent(getApplicationContext(), VedioConfranceScreen.class);
                                                                    CheckCameraMode = "Yes";
                                                                    startActivity(i);
                                                                    finish();
                                                                }
                                                                else if(CameraType.equals("Screen Sharing")){
                                                                    Intent i = new Intent(getApplicationContext(), ScreenSharing.class);
                                                                    startActivity(i);
                                                                    finish();
                                                                }else {
                                                                    Intent i = new Intent(getApplicationContext(), ThreeCamera.class);
                                                                    CheckCameraMode = "No";
                                                                    startActivity(i);
                                                                    finish();
                                                                }

/*                                                                final Dialog dialog1 = new Dialog(MeetingSheduleScreen.this);
                                                                dialog1.setContentView(R.layout.savebaatry);
                                                                //   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                                dialog1.setCancelable(false);
                                                                dialog1.show();
                                                                Button btn_yes=dialog1.findViewById(R.id.btn_yes);
                                                                Button btn_no=dialog1.findViewById(R.id.btn_no);
                                                                btn_yes.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {
                                                                        dialog.dismiss();
                                                                        room_Id = tv_enableid.getText().toString();

                                                                        Log.e("MeetingCheckId", tv_meetingid.getText().toString());

                                                                        if (CameraType.equals("Presenter Camera")) {
                                                                            Intent i = new Intent(getApplicationContext(), VedioConfranceScreen.class);
                                                                            CheckCameraMode="Yes";
                                                                            startActivity(i);
                                                                            finish();
                                                                        } else {
                                                                            Intent i = new Intent(getApplicationContext(), ThreeCamera.class);
                                                                            CheckCameraMode="No";
                                                                            startActivity(i);
                                                                            finish();
                                                                        }

                                                                        Log.e("checkLog", "3");
                                                                    }
                                                                });
                                                                btn_no.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {
                                                                        dialog.dismiss();
                                                                        room_Id = tv_enableid.getText().toString();

                                                                        Log.e("MeetingCheckId", tv_meetingid.getText().toString());

                                                                        if (CameraType.equals("Presenter Camera")) {
                                                                            Intent i = new Intent(getApplicationContext(), VedioConfranceScreen.class);
                                                                            startActivity(i);
                                                                            finish();
                                                                        } else {
                                                                            Intent i = new Intent(getApplicationContext(), ThreeCamera.class);
                                                                            startActivity(i);
                                                                            finish();
                                                                        }

                                                                        Log.e("checkLog", "3");
                                                                    }
                                                                });*/


                                                                // Toast.makeText(getApplicationContext(),"Dismissed..!!",Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                        dialog.show();

                                                    }


                                                } else {
                                                    if (hors >= hors1) {

                                                        // Log.e("MeetingCheckId", tv_meetingid.getText().toString());
                                                        progressDialogMeeting = new ProgressDialog(MeetingSheduleScreen.this);
                                                        //  progressDialog.setMessage("Meeting loaded ..."+countmeeting);
                                                        progressDialogMeeting.setMessage("Meeting  Status...");
//                                    progressDialogMeeting.show();
                                                        // validations();
                                                        Log.e("checkLog", "4");

                                                    } else {
                                                        final Dialog dialog = new Dialog(MeetingSheduleScreen.this);
                                                        dialog.setContentView(R.layout.customtime);
                                                        //   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                        dialog.setCancelable(false);
                                                        TextView tv_message = (TextView) dialog.findViewById(R.id.tv_message);
                                                        tv_message.setText("This meeting is scheduled at:" + " " + tv_time.getText().toString());
                                                        ImageView logo = (ImageView) dialog.findViewById(R.id.logo);
                                                        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);

                                                        String logo_url = sh.getString("logo_url", "");
                                                        Glide
                                                                .with(MeetingSheduleScreen.this)
                                                                .load(logo_url)
                                                                .centerCrop()
                                                                .fitCenter()
                                                                .into(logo);
                                                        Button dialogButton = (Button) dialog.findViewById(R.id.btnok);
                                                        // if button is clicked, close the custom dialog
                                                        dialogButton.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                dialog.dismiss();
                                                                // Toast.makeText(getApplicationContext(),"Dismissed..!!",Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                        dialog.show();
                                                    }

                                                }

                                            } else {
                                                final Dialog dialog = new Dialog(MeetingSheduleScreen.this);
                                                dialog.setContentView(R.layout.customtime);
                                                //  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                dialog.setCancelable(false);
                                                TextView tv_message = (TextView) dialog.findViewById(R.id.tv_message);
                                                tv_message.setText("This meeting is scheduled at:" + tv_time.getText().toString());
                                                ImageView logo = (ImageView) dialog.findViewById(R.id.logo);
                                                @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);

                                                String logo_url = sh.getString("logo_url", "");
                                                Glide
                                                        .with(MeetingSheduleScreen.this)
                                                        .load(logo_url)
                                                        .centerCrop()
                                                        .fitCenter()
                                                        .into(logo);
                                                Button dialogButton = (Button) dialog.findViewById(R.id.btnok);
                                                // if button is clicked, close the custom dialog
                                                dialogButton.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        dialog.dismiss();
                                                        // Toast.makeText(getApplicationContext(),"Dismissed..!!",Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                dialog.show();
                                                // Toast.makeText(MeetingSheduleScreen.this, "Sometime Remaning for meeting Start", Toast.LENGTH_SHORT).show();
                                            }


                                        } else {

                                            final Dialog dialog = new Dialog(MeetingSheduleScreen.this);
                                            dialog.setContentView(R.layout.customtime);
//                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            dialog.setCancelable(false);
                                            TextView tv_message = (TextView) dialog.findViewById(R.id.tv_message);
                                            tv_message.setText("This meeting is scheduled at :" + tv_date.getText().toString() + " " + "on Time:-" + tv_time.getText().toString());
                                            ImageView logo = (ImageView) dialog.findViewById(R.id.logo);
                                            @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);

                                            String logo_url = sh.getString("logo_url", "");
                                            Glide
                                                    .with(MeetingSheduleScreen.this)
                                                    .load(logo_url)
                                                    .centerCrop()
                                                    .fitCenter()
                                                    .into(logo);
                                            Button dialogButton = (Button) dialog.findViewById(R.id.btnok);
                                            // if button is clicked, close the custom dialog
                                            dialogButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialog.dismiss();
                                                    // Toast.makeText(getApplicationContext(),"Dismissed..!!",Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            dialog.show();
                                            //Toast.makeText(MeetingSheduleScreen.this, "This Meeting Scheduled on Date:-"+tv_date.getText().toString(), Toast.LENGTH_SHORT).show();
                                        }


                                    } else {

                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                //   Toast.makeText(MeetingSheduleScreen.this, Utils.VOLLEY_ERROR_MSG, Toast.LENGTH_SHORT).show();
                                error.getMessage();
                                System.out.println(" Response Add new lead  error.getMessage() ==>" + error.getMessage());


                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<String, String>();
                        //  checkcameratype = genderradioButton.getText().toString();
                        params.put("sessionid", tv_enableid.getText().toString());
                        params.put("meetingid", tv_meetingid.getText().toString());
                        params.put("accountid", accountid);


                        params.put("name ", CameraType);

                        Log.d("test", "getParams:  params ==>> " + params);
                        return params;

                    }

                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                VolleySingelton.getInstance(getApplicationContext()).getRequestQueue().add(stringRequest);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }


}
