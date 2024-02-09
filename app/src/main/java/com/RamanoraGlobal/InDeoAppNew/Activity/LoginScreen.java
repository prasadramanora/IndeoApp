package com.RamanoraGlobal.InDeoAppNew.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.RamanoraGlobal.InDeoAppNew.R;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.RamanoraGlobal.InDeoAppNew.OtherClasses.ApplicationController;
import com.RamanoraGlobal.InDeoAppNew.OtherClasses.VolleySingelton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginScreen extends AppCompatActivity implements View.OnClickListener {
    private EditText name;
    private EditText roomId;
    private Button joinRoom;
    EditText edtemail, edtpass;
    String checkcameratype = "";
    SharedPreferences sh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);
        FindViewByIds();
        LoadLogoUsingUrl();
        CheckLogin();
        setActionBarName();

    }

    private void setActionBarName() {
        getSupportActionBar().setTitle("InDeo");
    }

    private void LoadLogoUsingUrl() {
        try {
            ImageView logo = findViewById(R.id.logo);
            String logo_url = sh.getString("logo_url", "");
            Glide
                    .with(this)
                    .load(logo_url)
                    .centerCrop()
                    .fitCenter()
                    .into(logo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CheckLogin() {
        try {
            String FirstTimeLogin = sh.getString("FirstTimeLogin", "");
            if (FirstTimeLogin.equals("No")) {
                Intent i = new Intent(getApplicationContext(), MeetingSheduleScreen.class);
                startActivity(i);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void FindViewByIds() {
        name = (EditText) findViewById(R.id.name);
        roomId = (EditText) findViewById(R.id.roomId);
        joinRoom = (Button) findViewById(R.id.joinRoom);
        joinRoom.setOnClickListener(this);
        edtemail = findViewById(R.id.edtemail);
        edtpass = findViewById(R.id.edtpass);
        sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
    }

    private void LoginApi(final String emailid, String password) {

        final ProgressDialog progressDialogMeeting = new ProgressDialog(this, R.style.StyledDialog);

        progressDialogMeeting.setMessage("Loading  ...");
        progressDialogMeeting.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://indeo.in/api/post/login.php",
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

                            String Message = js.getString("status");
                            if (Message.equals("true")) {
                                progressDialogMeeting.dismiss();

                            } else {
                                progressDialogMeeting.dismiss();
                                Toast.makeText(LoginScreen.this, "Invalid Credential", Toast.LENGTH_SHORT).show();
                            }
                            JsonArrayData = js.getJSONArray("Data");
                            JsonArrayDataApikey = js.getJSONArray("Apidata");

                            for (int i = 0; i < JsonArrayDataApikey.length(); i++) {

                                JSONObject jsonObject = JsonArrayDataApikey.getJSONObject(i);

                                api_key = jsonObject.getString("api_key");
                                logo_url = jsonObject.getString("logo_url");
                                Log.e("logo_url", logo_url);
                                SharedPreferences sharedPreferences
                                        = getSharedPreferences("MySharedPref",
                                        MODE_PRIVATE);


                                SharedPreferences.Editor myEdit
                                        = sharedPreferences.edit();


                                myEdit.putString(
                                        "logo_url",
                                        logo_url);


                                myEdit.commit();

                            }

                            for (int i = 0; i < JsonArrayData.length(); i++) {

                                JSONObject jsonObject = JsonArrayData.getJSONObject(i);

                                String mid = jsonObject.getString("mid");
                                String accountid = jsonObject.getString("accountid");

                                Log.e("MIDCHECK", mid);
                                SharedPreferences sharedPreferences
                                        = getSharedPreferences("MySharedPref",
                                        MODE_PRIVATE);


                                SharedPreferences.Editor myEdit
                                        = sharedPreferences.edit();


                                myEdit.putString(
                                        "MeetingId",
                                        mid);
                                myEdit.putString(
                                        "api_key",
                                        api_key);

                                myEdit.putString(
                                        "accountid",
                                        accountid);
                                myEdit.putString(
                                        "logo_url",
                                        logo_url);


                                myEdit.commit();
                                Intent ii = new Intent(getApplicationContext(), CameraSelection.class);
                                startActivity(ii);
                                finish();
                            }


                            progressDialogMeeting.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("error", "onErrorResponse: error email" + error.getMessage());


                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("emailid", emailid);
                params.put("password", password);
                Log.e("PARAM", params.toString());
                return params;
            }

        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingelton.getInstance(LoginScreen.this).getRequestQueue()
                .add(stringRequest);


    }

    @Override
    public void onClick(View v) {
        if (v == joinRoom) {
            RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioButtonGroup);

            RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup);
            radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    try {
                        int selectedId = radioGroup1.getCheckedRadioButtonId();
                        RadioButton genderradioButton = (RadioButton) findViewById(selectedId);
                        checkcameratype = genderradioButton.getText().toString();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            if (edtemail.getText().toString().length() == 0) {
                edtemail.setError("Enter Email");
                edtemail.requestFocus();
            } else if (edtpass.getText().toString().length() == 0) {
                edtpass.setError("Enter Email");
                edtpass.requestFocus();
            } else {
                LoginApi(edtemail.getText().toString(), edtpass.getText().toString());

            }


        }
    }


}
