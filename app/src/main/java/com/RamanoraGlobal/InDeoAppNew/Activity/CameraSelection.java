package com.RamanoraGlobal.InDeoAppNew.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.RamanoraGlobal.InDeoAppNew.R;
import com.bumptech.glide.Glide;


public class CameraSelection extends AppCompatActivity implements View.OnClickListener {
    Button btn_submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cameramode);
        ImageView iv_load = findViewById(R.id.iv_load);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle("InDeo");
        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
        String logo_url = sh.getString("logo_url", "");
        Glide
                .with(this)
                .load(logo_url)
                .centerCrop()
                .fitCenter()
                .into(iv_load);
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btn_submit) {
            RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioButtonGroup);

            RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup);
            try {
                int selectedId = radioGroup1.getCheckedRadioButtonId();
                RadioButton genderradioButton = (RadioButton) findViewById(selectedId);
                String checkcameratype = genderradioButton.getText().toString();
                Log.e("MyCamera", checkcameratype.length() + "");
                Log.e("MyCamera", checkcameratype.length() + "");
                //Toast.makeText(LoginScreen.this,genderradioButton.getText(), Toast.LENGTH_SHORT).show();
                Log.e("CheckType", genderradioButton.getText() + "");

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
                        "CameraType",
                        checkcameratype);
                myEdit.putString(
                        "FirstTimeLogin",
                        "No");

                // checkcameratype = checkcameratype[0];
// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
                myEdit.commit();


// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error

                Intent ii = new Intent(getApplicationContext(), MeetingSheduleScreen.class);
                startActivity(ii);
                finish();
                // name.setText(genderradioButton.getText() + "");
            } catch (Exception e) {
                Toast.makeText(CameraSelection.this, "Select camera Mode", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}
