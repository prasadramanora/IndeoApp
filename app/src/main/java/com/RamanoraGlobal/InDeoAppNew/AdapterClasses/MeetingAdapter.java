package com.RamanoraGlobal.InDeoAppNew.AdapterClasses;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.RamanoraGlobal.InDeoAppNew.Activity.CameraSelection;
import com.RamanoraGlobal.InDeoAppNew.Activity.MeetingSheduleScreen;
import com.RamanoraGlobal.InDeoAppNew.ModelClass.ModelClass;
import com.RamanoraGlobal.InDeoAppNew.R;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.DataHolder> {
    java.util.Map<String, String> outputMap = new HashMap<String, String>();
    private Context mContext;
    public static ArrayList<ModelClass> mArrayListCallRlist;
    public static ArrayList<ModelClass> mFilteredList = null;

    String Platform;

    private ListItemClickListener mOnClickListener;
    java.util.List<Integer> selectedPosition;

    android.view.View view;


    public MeetingAdapter(Context mContext, ArrayList<ModelClass> mArrayListCallRlist, ListItemClickListener listener) {
        this.mContext = mContext;
        this.mArrayListCallRlist = mArrayListCallRlist;
        this.mFilteredList = mArrayListCallRlist;
        selectedPosition = new ArrayList(mFilteredList.size());
        this.Platform = Platform;
        mOnClickListener = listener;

    }

    public interface ListItemClickListener {
        void onListItemClick(android.view.View clickedItemIndex);
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.mertinglist_adpater, parent, false);
        DataHolder dataHolder = new DataHolder(view);
        return dataHolder;
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final DataHolder holder, final int position) {

        final ModelClass callRInfo = mFilteredList.get(position);
        final int pos = position;


        @SuppressLint("WrongConstant") SharedPreferences sh = mContext.getSharedPreferences("MySharedPref", MODE_APPEND);
        String MeetingidCheck = sh.getString("MeetingidCheck", "");

       /* SQLiteDatabase db =  mContext.openOrCreateDatabase("MeetingDb", Context.MODE_PRIVATE, null);
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
        }
        if(checkmeeting.contains(callRInfo.getMeeting_id()))
        {
           holder.btnstart.setVisibility(View.GONE);
           holder.tv_time.setText("Meeting Expired");
        }else {
            holder.btnstart.setVisibility(View.VISIBLE);
            holder.tv_time.setText(callRInfo.getMeetingtime());
        }
       */


        android.util.Log.e("MeetingTime",callRInfo.getMeetingtime());
        SimpleDateFormat dff = new SimpleDateFormat("HH:mm:ss");
        Date dd = null;
        try {
            dd = dff.parse(callRInfo.getMeetingtime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(dd);
        cal.add(Calendar.MINUTE, -5);
        String newTime = dff.format(cal.getTime());
        android.util.Log.e("MeetingTimeMinus", newTime);
        holder.tv_time.setText(newTime);
        holder.tv_meetingid.setText(callRInfo.getMeeting_id());
        holder.shopname.setText(callRInfo.getShopname());
      //  holder.tv_meetingstatus.setText(callRInfo.getMeetingstatus());

        if (!callRInfo.getMeetingstatus().equals("")) {

            holder.rl_layout.setBackgroundResource(R.color.colorAccent);
            holder.btnstart.setBackgroundResource(R.color.white);
            holder.btnstart.setText("join");
            holder.btnstart.setTextColor(Color.parseColor("#000000"));
            holder.tv_date1.setTextColor(Color.parseColor("#ffffff"));
            holder.tv_time1.setTextColor(Color.parseColor("#ffffff"));
            holder.shopname.setTextColor(Color.parseColor("#ffffff"));

        } else {
            holder.rl_layout.setBackgroundResource(R.color.grey);
            holder.btnstart.setBackgroundResource(R.color.colorPrimary);
            holder.btnstart.setText("Start");
            holder.btnstart.setTextColor(Color.parseColor("#ffffff"));
            holder.tv_date1.setTextColor(Color.parseColor("#000000"));
            holder.tv_time1.setTextColor(Color.parseColor("#000000"));
            holder.shopname.setTextColor(Color.parseColor("#000000"));

        }
        holder.tv_date.setText(callRInfo.getMettingdate());
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd MMMM yyyy");
        try {
            Date  oneWayTripDate = input.parse(callRInfo.getMettingdate());  // parse input
            holder.tv_date1.setText(output.format(oneWayTripDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
        Date dt;
        try {
            dt = sdf.parse(callRInfo.getMeetingtime());
            System.out.println("Time Display: " + sdfs.format(dt));
            holder.tv_time1.setText(sdfs.format(dt));
            // <-- I got result here
        } catch (ParseException e) {
            e.printStackTrace();
        }

       holder.tv_enableid.setText(callRInfo.getEnablexid());


        holder.iv_camerachooser.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.customeselectcameramode);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


                Button btn_submit = (Button) dialog.findViewById(R.id.btn_submit);
                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioButtonGroup);

                        RadioGroup radioGroup1 = (RadioGroup) dialog.findViewById(R.id.radioGroup);
                        try {
                            int selectedId = radioGroup1.getCheckedRadioButtonId();
                            RadioButton genderradioButton = (RadioButton) dialog.findViewById(selectedId);
                            String checkcameratype = genderradioButton.getText().toString();
                            Log.e("MyCamera", checkcameratype.length()+"");
                            Log.e("MyCamera", checkcameratype.length() + "");
                            //Toast.makeText(LoginScreen.this,genderradioButton.getText(), Toast.LENGTH_SHORT).show();
                            Log.e("CheckType", genderradioButton.getText() + "");

                            SharedPreferences sharedPreferences
                                    = mContext.getSharedPreferences("MySharedPref",
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


                            // checkcameratype = checkcameratype[0];
// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
                            myEdit.commit();



// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error


                            // name.setText(genderradioButton.getText() + "");
                        } catch (Exception e) {
                            Toast.makeText(mContext, "Select camera Mode", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
//                    Toast.makeText(getApplicationContext(),"Cancel" ,Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });


                dialog.show();


            }
        });
        holder.itemView.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {


            }
        });

    }

    @Override
    public int getItemCount() {

        return mFilteredList.size();


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public class DataHolder extends RecyclerView.ViewHolder {
        android.widget.TextView tv_date,tv_date1,shopname,tv_meetingstatus, tv_time,tv_time1, tv_meetingid, tv_enableid,txt_personName, txt_designation, txt_remainder/*, txt_actual_date_time*/, txt_remainder_date_time;
        Button btnstart;
        RelativeLayout rl_layout;
        ImageView share,iv_camerachooser;

        public DataHolder(android.view.View itemView) {
            super(itemView);
            iv_camerachooser= itemView.findViewById(R.id.iv_camerachooser);
            tv_meetingstatus= itemView.findViewById(R.id.tv_meetingstatus);
            tv_date = itemView.findViewById(R.id.tv_date);
            shopname = itemView.findViewById(R.id.shopname);
            tv_date1 = itemView.findViewById(R.id.tv_date1);
            share= itemView.findViewById(R.id.share);
            tv_enableid= itemView.findViewById(R.id.tv_enableid);
            tv_meetingid = itemView.findViewById(R.id.tv_meetingid);
            btnstart = itemView.findViewById(R.id.btnstart);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_time1 = itemView.findViewById(R.id.tv_time1);
            rl_layout = itemView.findViewById(R.id.rl_layout);
            mOnClickListener.onListItemClick(itemView);
        }
    }


}

