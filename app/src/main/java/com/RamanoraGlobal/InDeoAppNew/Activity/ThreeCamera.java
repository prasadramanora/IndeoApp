package com.RamanoraGlobal.InDeoAppNew.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.RamanoraGlobal.InDeoAppNew.R;
import com.bumptech.glide.Glide;
import com.opentok.android.BaseVideoRenderer;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;
import com.opentok.android.SubscriberKit;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import CustomVideoDriverLib.CustomVideoCapturer;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ThreeCamera extends AppCompatActivity implements EasyPermissions.PermissionCallbacks,  Session.SessionListener,
        Publisher.PublisherListener,
        Subscriber.VideoListener  {


    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {

    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {

    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {

    }

    @Override
    public void onConnected(Session session) {

    }

    @Override
    public void onDisconnected(Session session) {

    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {

    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {

    }

    @Override
    public void onError(Session session, OpentokError opentokError) {

    }

    @Override
    public void onVideoDataReceived(SubscriberKit subscriberKit) {

    }

    @Override
    public void onVideoDisabled(SubscriberKit subscriberKit, String s) {

    }

    @Override
    public void onVideoEnabled(SubscriberKit subscriberKit, String s) {

    }

    @Override
    public void onVideoDisableWarning(SubscriberKit subscriberKit) {

    }

    @Override
    public void onVideoDisableWarningLifted(SubscriberKit subscriberKit) {

    }

    static class SubscriberContainer {
        public RelativeLayout container;
        public ToggleButton toggleAudio;
        public ToggleButton toggleVudio;
        public ToggleButton remove;
        public Subscriber subscriber;

        public SubscriberContainer(RelativeLayout container, ToggleButton toggleAudio, Subscriber subscriber) {
            this.container = container;
            this.toggleAudio = toggleAudio;

            this.subscriber = subscriber;
        }
    }

    private static final String TAG = ThreeCamera.class.getSimpleName();
    Stream streammm;
    private final int MAX_NUM_SUBSCRIBERS = 10;
    String CameraType, Sessionid, Tokan;
    private static final int PERMISSIONS_REQUEST_CODE = 124;
    Button btn_sound;
    private Session session;
    private Publisher publisher;
    private ScaleGestureDetector scaleGestureDetector;
    private Matrix matrix = new Matrix();
    private static String check = "1";
    private List<SubscriberContainer> subscribers;
    ImageView disconnect;
    RelativeLayout.LayoutParams params;
    public RelativeLayout publisherViewContainer;
    ArrayList<String> checkparticipate = new ArrayList<>();
    private boolean sessionConnected = false;

    private PublisherKit.PublisherListener publisherListener = new PublisherKit.PublisherListener() {
        @Override
        public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
            Log.e("CheckLOg", "1");

            Log.d(TAG, "onStreamCreated: Own stream " + stream.getStreamId() + " created");
        }

        @Override
        public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
            Log.e("CheckLOg", "2");
            Log.d(TAG, "onStreamDestroyed: Own stream " + stream.getStreamId() + " destroyed");
        }

        @Override
        public void onError(PublisherKit publisherKit, OpentokError opentokError) {
            Log.e("CheckLOg", "3");
            finishWithMessage("PublisherKit error: " + opentokError.getMessage());
        }
    };

    private Session.SessionListener sessionListener = new Session.SessionListener() {
        @Override
        public void onConnected(Session session) {
            Log.d(TAG, "onConnected: Connected to session " + session.getSessionId());
            sessionConnected = true;
            Log.e("CheckLOg", "4");
            @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
            CameraType = sh.getString("CameraType", "");
          /*  publisher = new Publisher.Builder(ThreeCamera.this)
                    .name(CameraType)
                    .build();
            publisher.setPublisherListener(publisherListener);
            publisher.setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);

            publisherViewContainer.addView(publisher.getView());
            
          //  publisherViewContainer.setOnTouchListener(new TouchHandler());
            session.publish(publisher);*/

            publisher = new Publisher.Builder(ThreeCamera.this)
                    .name(CameraType)
                    .capturer(new CustomVideoCapturer(ThreeCamera.this, Publisher.CameraCaptureResolution.HIGH, Publisher.CameraCaptureFrameRate.FPS_30))
                    .build();
            publisher.setPublisherListener(ThreeCamera.this);

            publisher.setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);
            publisherViewContainer.addView(publisher.getView());


               ((GLSurfaceView) (publisher.getView())).setZOrderOnTop(true);


            session.publish(publisher);
            //publisher.cycleCamera();
            publisher.setPublishAudio(false);
            /*if (CameraType.equals("Presenter Camera")) {

            } else {
                publisher.setPublishAudio(false);
            }*/


        }

        @Override
        public void onDisconnected(Session session) {
            Log.d(TAG, "onDisconnected: disconnected from session " + session.getSessionId());
            sessionConnected = false;
            ThreeCamera.this.session = null;
            Log.e("CheckLOg", "5");
        }

        @Override
        public void onError(Session session, OpentokError opentokError) {
            Log.e("CheckLOg", "6");
            //finishWithMessage("Session error: " + opentokError.getMessage());
        }

        @Override
        public void onStreamReceived(Session session, Stream stream) {
            Log.d(TAG, "onStreamReceived1: New stream " + stream.getStreamId());
            Log.e("CheckLOg", "7");
            streammm = stream;
            final Subscriber subscriber = new Subscriber.Builder(ThreeCamera.this, stream).build();
            checkparticipate.add(stream.getStreamId());
            if (CameraType.equals("Presenter Camera")) {
                try {

                    //  final Subscriber subscriber1 = new Subscriber.Builder(VedioConfranceScreen.this, stream).build();
                    session.subscribe(subscriber);

                    addSubscriber(subscriber);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }


        }

        @Override
        public void onStreamDropped(Session session, Stream stream) {
            Log.d(TAG, "onStreamDropped: Stream " + stream.getStreamId() + " dropped from session " + session.getSessionId());
            Log.e("CheckLOg", "8");

            removeSubscriberWithStream(stream);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.threecamera);
        Log.e("Orentation_eeee", "16");
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        scaleGestureDetector = new ScaleGestureDetector(this,new ScaleListener());
        Button btnrecord = findViewById(R.id.btnrecord);
        btnrecord.setVisibility(View.GONE);
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        disconnect = findViewById(R.id.disconnect);
        btn_sound = findViewById(R.id.btn_sound);
        btn_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogselect = new Dialog(ThreeCamera.this);
                dialogselect.setContentView(R.layout.sound);
//                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogselect.setCancelable(false);
                dialogselect.show();

                // RadioGroup radioGroup = (RadioGroup) dialogselect.findViewById(R.id.radioButtonGroup);

                RadioGroup radioGroup1 = (RadioGroup) dialogselect.findViewById(R.id.radioGroup);
                radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        try {
                            int selectedId = radioGroup1.getCheckedRadioButtonId();
                            RadioButton genderradioButton = (RadioButton) dialogselect.findViewById(selectedId);
                            AudioManager mAudioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                            Toast.makeText(ThreeCamera.this, genderradioButton.getText(), Toast.LENGTH_SHORT).show();
                            if (genderradioButton.getText().toString().equals("Head Phone")) {

                                mAudioMgr.setMode(AudioManager.MODE_IN_COMMUNICATION);
                                mAudioMgr.setSpeakerphoneOn(false);
                                mAudioMgr.setWiredHeadsetOn(true);
                                Toast.makeText(getApplicationContext(), "Wired Headset On", Toast.LENGTH_LONG).show();
                                dialogselect.dismiss();

                            } else if (genderradioButton.getText().toString().equals("Speaker")) {
                                mAudioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                                mAudioMgr.setWiredHeadsetOn(false);
                                mAudioMgr.setSpeakerphoneOn(true);
                                mAudioMgr.setMode(AudioManager.MODE_IN_COMMUNICATION);
                                Toast.makeText(getApplicationContext(), "SpeakerPhone On", Toast.LENGTH_LONG).show();
                                dialogselect.dismiss();
                            }
                            // Log.e("ccheckType", genderradioButton.getText() + "");
                            // name.setText(genderradioButton.getText() + "");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        Log.e("CheckLOg", "9");
        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(ThreeCamera.this);
                dialog.setContentView(R.layout.exitrecordingdialogcamera);
                //   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.show();
                @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);

                Button btncancel = dialog.findViewById(R.id.btncancel);
                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Button btnclosesession = dialog.findViewById(R.id.btnclosesession);
                btnclosesession.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent i = new Intent(getApplicationContext(), MeetingSheduleScreen.class);
                        startActivity(i);
                        disconnectSession();
                        finish();
                    }
                });
                ImageView logo = dialog.findViewById(R.id.logo);
                String logo_url = sh.getString("logo_url", "");
                Glide
                        .with(ThreeCamera.this)
                        .load(logo_url)
                        .centerCrop()
                        .fitCenter()
                        .into(logo);

                // finish();
            }
        });
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
                "No");


// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
        myEdit.commit();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
        CameraType = sh.getString("CameraType", "");
        Tokan = sh.getString("Tokan", "");
        Sessionid = sh.getString("Sessionid", "");
        Log.e("Tokan", Tokan);
        Log.e("Sessionid", Sessionid);

        ToggleButton toggleAudio = findViewById(R.id.toggleAudio);
        ToggleButton toggleVideo = findViewById(R.id.toggleVideo);

            toggleAudio.setVisibility(View.VISIBLE);
            toggleVideo.setVisibility(View.INVISIBLE);
            btn_sound.setVisibility(View.INVISIBLE);


       /* if (!OpenTokConfig.isValid()) {
            finishWithMessage("Invalid OpenTokConfig. " + OpenTokConfig.getDescription());
            return;
        }
*/
        publisherViewContainer = findViewById(R.id.publisherview);

        if (CameraType.equals("Presenter Camera")) {
            TextView tv_cameramode = findViewById(R.id.tv_cameramode);
            tv_cameramode.setVisibility(View.VISIBLE);
            String CameraType = sh.getString("CameraType", "");
            Log.e("cameraModeeee", CameraType);

            tv_cameramode.setText("You are Logged In With " + CameraType);

        } else {

            // publisher.setPublishAudio(false);
            //publisherViewContainer.setCh(false);
           /* Display display = getWindowManager().getDefaultDisplay();

            int width = display.getWidth(); // ((display.getWidth()*20)/100)
            int height = display.getHeight();// ((display.getHeight()*30)/100)
            RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(width, height);
            publisherViewContainer.setLayoutParams(parms);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 5, 10, 110);
            publisherViewContainer.setLayoutParams(params);*/
            TextView tv_cameramode = findViewById(R.id.tv_cameramode);
            tv_cameramode.setVisibility(View.VISIBLE);
            String CameraType = sh.getString("CameraType", "");
            Log.e("cameraModeeee", CameraType);
            tv_cameramode.setText("You are Logged In With " + CameraType);
        }

        final Button swapCamera = findViewById(R.id.swapCamera);
        swapCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (publisher == null) {
                    return;
                }



              /*  RelativeLayout subscriberview0 = findViewById(R.id.subscriberview0);
                subscriberview0.setVisibility(View.GONE);*/
                publisher.cycleCamera();
            }
        });

        toggleAudio = findViewById(R.id.toggleAudio);
//        publisher.setPublishAudio(false);
        toggleAudio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e("Checkkkkk", isChecked + "");

                if (publisher == null) {
                    return;
                }

                if (isChecked) {
                    publisher.setPublishAudio(true);
                } else {
                    publisher.setPublishAudio(false);
                }


            }
        });


        toggleVideo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (publisher == null) {
                    return;
                }

                if (isChecked) {
                    publisher.setPublishVideo(true);
                } else {
                    publisher.setPublishVideo(false);
                }
            }
        });

        subscribers = new ArrayList<>();
        for (int i = 0; i < MAX_NUM_SUBSCRIBERS; i++) {
            if (CameraType.equals("Presenter Camera")) {

                int containerId = getResources().getIdentifier("subscriberview" + (new Integer(i)).toString(),
                        "id", this.getPackageName());
                int toggleAudioId = getResources().getIdentifier("toggleAudioSubscriber" + (new Integer(i)).toString(),
                        "id", this.getPackageName());
               /* int toggleVudioId = getResources().getIdentifier("toggleVudioSubscriber" + (new Integer(i)).toString(),
                        "id", this.getPackageName());
                int remove = getResources().getIdentifier("remove" + (new Integer(i)).toString(),
                        "id", this.getPackageName());
                Log.e("MyIds", containerId + "");
                Log.e("MyIds1", toggleAudioId + "");*/
                Log.e("MyIds_sssss", containerId + "");
                subscribers.add(new ThreeCamera.SubscriberContainer(
                        findViewById(containerId),

                        findViewById(toggleAudioId),

                        null
                ));
            }

        }

        requestPermissions();
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        scaleGestureDetector.onTouchEvent(ev);
        return true;
    }
    private class ScaleListener extends ScaleGestureDetector.
            SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
            matrix.setScale(scaleFactor, scaleFactor);
            //publisherViewContainer.setImageMatrix(matrix);
            return true;
        }
    }
    private void changeScreenOrientation() {
        int orientation = ThreeCamera.this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

                check = "1";
                // publisher.setPublishAudio(false);
                //publisherViewContainer.setCh(false);


            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            Log.e("Orentation", "1");
            //showMediaDescription();
        } else {
            Log.e("Orentation", "2");
            //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            //  hideMediaDescription();
           /* if (CameraType.equals("Presenter Camera")) {
                Display display = getWindowManager().getDefaultDisplay();

                int width = display.getWidth(); // ((display.getWidth()*20)/100)
                int height = display.getHeight();// ((display.getHeight()*30)/100)
                RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(width, height);
                publisherViewContainer.setLayoutParams(parms);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 100, 10, 100);
                publisherViewContainer.setLayoutParams(params);
            } else {

                // publisher.setPublishAudio(false);
                //publisherViewContainer.setCh(false);


            }*/

        }
        if (Settings.System.getInt(getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1) {
            Log.e("Orentation", "3");
            Handler handler = new Handler();
           /* handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }
            }, 4000);*/
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Orentation_eeee", "1");
        changeScreenOrientation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Orentation_eeee", "2");
        if (session == null) {
            return;
        }

        session.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Orentation_eeee", "3");
        /*if (session == null) {
            return;
        }

        session.onPause();

        if (isFinishing()) {

        }*/
    }

    @Override
    protected void onDestroy() {
        Log.e("Orentation_eeee", "4");



        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        finishWithMessage("onPermissionsDenied: " + requestCode + ":" + perms.size());
    }

    @AfterPermissionGranted(PERMISSIONS_REQUEST_CODE)
    private void requestPermissions() {
        Log.e("CheckLOg", "10");
        String[] perms = {Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};

        if (EasyPermissions.hasPermissions(this, perms)) {
            @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);


                String api_key = sh.getString("api_key", "");
            session = new Session.Builder(this, api_key, Sessionid).build();
            session.setSessionListener(sessionListener);
            session.connect(Tokan);

//            publisher.cycleCamera();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_video_app), PERMISSIONS_REQUEST_CODE, perms);
        }
    }

    private SubscriberContainer findFirstEmptyContainer(Subscriber subscriber) {
        Log.e("Checkkkkk", "1");
        for (SubscriberContainer subscriberContainer : subscribers) {
            if (subscriberContainer.subscriber == null) {
                return subscriberContainer;
            }
        }
        return null;
    }

    private SubscriberContainer findContainerForStream(Stream stream) {
        Log.e("Checkkkkk", "2");
        for (SubscriberContainer subscriberContainer : subscribers) {
            if (subscriberContainer.subscriber.getStream().getStreamId().equals(stream.getStreamId())) {
                return subscriberContainer;
            }
        }
        return null;
    }

    private void addSubscriber(Subscriber subscriber) {
        Log.e("CheckLOg", "11");
        Log.e("Checkkkkk", "3");
        Log.d(TAG, "onStreamReceived2: New stream " + streammm.getStreamId());

        HashSet<String> set = new HashSet<String>(checkparticipate);
        // Convert back to ArrayList
        checkparticipate = new ArrayList<String>(set);
        if (checkparticipate.contains(streammm.getStreamId())) {
            SubscriberContainer container = findFirstEmptyContainer(subscriber);
            if (container == null) {
                Toast.makeText(this, "New subscriber ignored. MAX_NUM_SUBSCRIBERS limit reached", Toast.LENGTH_LONG).show();
                return;
            }
            // Log.e("Checkkkkk", subscriber.getView()+"");
            container.subscriber = subscriber;
            container.container.addView(subscriber.getView());

            Log.e("CheckHashCode", subscriber.hashCode() + "");

            subscriber.setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);

            container.toggleAudio.setVisibility(View.VISIBLE);
            if (session == null || !sessionConnected) {
                return;
            }

            sessionConnected = false;
            try {
                Log.e("Seesionidcheck", session.getSessionId());
                //  Toast.makeText(this, "session Id:-" + session.getSessionId(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //subscriber.setSubscribeToAudio(false);
            container.toggleAudio.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    subscriber.setSubscribeToAudio(true);
                } else {
                    subscriber.setSubscribeToAudio(false);
                }
            });
        }


        //container.remove.setText("Camera Name");

       /* container.remove.setVisibility(View.GONE);
        container.toggleVudio.setVisibility(View.GONE);
        container.toggleAudio.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                subscriber.setSubscribeToAudio(true);
            } else {
                subscriber.setSubscribeToAudio(false);
            }
        });
        container.toggleVudio.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                subscriber.setSubscribeToVideo(true);
            } else {
                subscriber.setSubscribeToVideo(false);
            }
        });*/


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        final Dialog dialog = new Dialog(ThreeCamera.this);
        dialog.setContentView(R.layout.exitrecordingdialogcamera);
        //   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.show();
        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);

        Button btncancel = dialog.findViewById(R.id.btncancel);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button btnclosesession = dialog.findViewById(R.id.btnclosesession);
        btnclosesession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent i = new Intent(getApplicationContext(), MeetingSheduleScreen.class);
                startActivity(i);
                disconnectSession();
                finish();
            }
        });
        ImageView logo = dialog.findViewById(R.id.logo);
        String logo_url = sh.getString("logo_url", "");
        Glide
                .with(ThreeCamera.this)
                .load(logo_url)
                .centerCrop()
                .fitCenter()
                .into(logo);

    }

    private void removeSubscriberWithStream(Stream stream) {

        Log.e("Checkkkkk", "4");
        if (CameraType.equals("Presenter Camera")) {
            SubscriberContainer container = findContainerForStream(stream);

            if (container == null) {
                return;
            }

            container.container.removeView(container.subscriber.getView());
            container.toggleAudio.setOnCheckedChangeListener(null);
            //container.container.setVisibility(View.GONE);
            container.toggleAudio.setVisibility(View.INVISIBLE);
          /*  container.toggleVudio.setOnCheckedChangeListener(null);
            container.toggleVudio.setVisibility(View.INVISIBLE);*/
            container.subscriber = null;
        } else {
            SubscriberContainer container = findContainerForStream(stream);

            if (container == null) {
                return;
            }

            container.container.removeView(container.subscriber.getView());
            container.toggleAudio.setOnCheckedChangeListener(null);
            // container.container.setVisibility(View.GONE);
            container.toggleAudio.setVisibility(View.INVISIBLE);
          /*  container.toggleVudio.setOnCheckedChangeListener(null);
            container.toggleVudio.setVisibility(View.INVISIBLE);*/
            container.subscriber = null;
        }

    }

    private void disconnectSession() {
        Log.e("Removesubs1", subscribers.size() + "");


        try {
            for (SubscriberContainer subscriberContainer : subscribers) {

                session.unsubscribe(subscriberContainer.subscriber);
                Log.e("CheckLog", "30");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (publisher != null) {
            publisherViewContainer.removeView(publisher.getView());
            session.unpublish(publisher);
            publisher = null;
        }

        session.disconnect();
        Log.e("CheckLog", "20");


    }


    private void finishWithMessage(String message) {
        Log.e(TAG, message);
        // Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        this.finish();
    }
}

