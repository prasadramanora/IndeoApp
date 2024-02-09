package com.RamanoraGlobal.InDeoAppNew.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.AudioManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.RamanoraGlobal.InDeoAppNew.R;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.opentok.android.BaseVideoRenderer;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;
import com.opentok.android.SubscriberKit;
import com.RamanoraGlobal.InDeoAppNew.OtherClasses.VolleySingelton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import CustomVideoDriverLib.CustomVideoCapturer;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.bluetooth.BluetoothDevice.EXTRA_DEVICE;

public class VedioConfranceScreen extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, Session.SessionListener,
        Publisher.PublisherListener,
        Subscriber.VideoListener {
    LinearLayout participatevieew;
    private TextView mStatusTv, tv_muteall;
    private Button mActivateBtn, btn_reconnctpublisher;
    ImageView iv_startrecord;
    private Session mSession;
    HashMap<String, String> mapparticipate = new HashMap<String, String>();
    ArrayList<Integer> ParticipateList = new ArrayList<>();
    private Button mPairedBtn;
    Dialog dialogselectBluetooth;
    private Publisher mPublisher;
    Button b1, b2, b3, b4;
    SubscriberContainer container;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    ListView lv;
    Dialog dialogselectBluetoothList;
    private Button mScanBtn;
    private ArrayList<BluetoothDevice> mDeviceList = new ArrayList<BluetoothDevice>();
    private WebServiceCoordinator mWebServiceCoordinator;
    public static String AppTokan, AppApiKey, AppSessionId;
    private BluetoothAdapter mBluetoothAdapter;
    private ProgressDialog mProgressDlg;

    RelativeLayout subscriberview1, subscriberview0, subscriberview2, subscriberview3, subscriberview4, subscriberview5, subscriberview6, subscriberview7, subscriberview8, subscriberview9, subscriberview10, subscriberview11, subscriberview12, subscriberview13, subscriberview14, subscriberview15, subscriberview16, subscriberview17, subscriberview18, subscriberview19, subscriberview20, subscriberview21, subscriberview22, subscriberview23, subscriberview24, subscriberview25, subscriberview26, subscriberview27, subscriberview28, subscriberview29;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        mMenu = menu;
        return true;
    }

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
        public Button tv_partcname;

        public SubscriberContainer(RelativeLayout container, Button tv_partcname, ToggleButton toggleAudio, ToggleButton toggleVudio, Subscriber subscriber) {
            this.container = container;
            this.tv_partcname = tv_partcname;

            this.toggleAudio = toggleAudio;
            this.toggleVudio = toggleVudio;

            this.subscriber = subscriber;
        }
    }

    private static final String TAG = VedioConfranceScreen.class.getSimpleName();
    Stream streammm;

    ToggleButton toggleAudioSubscriber0;
    ToggleButton toggleAudioSubscriber1;
    ToggleButton toggleAudioSubscriber2;
    ToggleButton toggleAudioSubscriber3;
    ToggleButton toggleAudioSubscriber4;


    ToggleButton toggleAudioSubscriber5;
    ToggleButton toggleAudioSubscriber6;
    ToggleButton toggleAudioSubscriber7;
    ToggleButton toggleAudioSubscriber8;
    ToggleButton toggleAudioSubscriber9;


    ToggleButton toggleAudioSubscriber10;
    ToggleButton toggleAudioSubscriber11;
    ToggleButton toggleAudioSubscriber12;
    ToggleButton toggleAudioSubscriber13;
    ToggleButton toggleAudioSubscriber14;


    ToggleButton toggleAudioSubscriber15;
    ToggleButton toggleAudioSubscriber16;
    ToggleButton toggleAudioSubscriber17;
    ToggleButton toggleAudioSubscriber18;
    ToggleButton toggleAudioSubscriber19;


    ToggleButton toggleAudioSubscriber20;
    ToggleButton toggleAudioSubscriber21;
    ToggleButton toggleAudioSubscriber22;
    ToggleButton toggleAudioSubscriber23;
    ToggleButton toggleAudioSubscriber24;


    ToggleButton toggleAudioSubscriber25;
    ToggleButton toggleAudioSubscriber26;
    ToggleButton toggleAudioSubscriber27;
    ToggleButton toggleAudioSubscriber28;
    ToggleButton toggleAudioSubscriber29;

    private static int count = 0;
    ToggleButton toggleVudioSubscriber0;
    ToggleButton toggleVudioSubscriber1;
    ToggleButton toggleVudioSubscriber2;
    ToggleButton toggleVudioSubscriber3;
    ToggleButton toggleVudioSubscriber4;
    ToggleButton toggleVudioSubscriber5;
    ToggleButton toggleVudioSubscriber6;
    ToggleButton toggleVudioSubscriber7;
    ToggleButton toggleVudioSubscriber8;
    ToggleButton toggleVudioSubscriber9;
    ToggleButton toggleVudioSubscriber10;
    ToggleButton toggleVudioSubscriber11;
    ToggleButton toggleVudioSubscriber12;
    ToggleButton toggleVudioSubscriber13;
    ToggleButton toggleVudioSubscriber14;
    ToggleButton toggleVudioSubscriber15;
    ToggleButton toggleVudioSubscriber16;
    ToggleButton toggleVudioSubscriber17;
    ToggleButton toggleVudioSubscriber18;
    ToggleButton toggleVudioSubscriber19;

    ToggleButton toggleVudioSubscriber20;
    ToggleButton toggleVudioSubscriber21;
    ToggleButton toggleVudioSubscriber22;
    ToggleButton toggleVudioSubscriber23;
    ToggleButton toggleVudioSubscriber24;


    ToggleButton toggleVudioSubscriber25;
    ToggleButton toggleVudioSubscriber26;
    ToggleButton toggleVudioSubscriber27;
    ToggleButton toggleVudioSubscriber28;
    ToggleButton toggleVudioSubscriber29;


    private Subscriber mSubscriber;
    private final int MAX_NUM_SUBSCRIBERS = 10;
    String CameraType, accountiduser, Sessionid, Tokan, api_key, RecordingStart, archiveid;
    private static final int PERMISSIONS_REQUEST_CODE = 124;
    Button btn_sound, btnrecord;
    Button btnrecordstop;
    Button btn_muteall;
    ArrayList<Subscriber> subscriberslist = new ArrayList<>();
    String CheackRecordingStart = "No";
    private Session session;
    private FrameLayout mPublisherViewContainer;
    private FrameLayout mSubscriberViewContainer;
    private Publisher publisher;
    private static String check = "1";
    private ImageView mArchivingIndicatorView;
    private List<SubscriberContainer> subscribers;
    ImageView disconnect;
    private RelativeLayout publisherViewContainer;
    ArrayList<String> checkparticipate = new ArrayList<>();
    ArrayList<String> checkparticipatenamelist = new ArrayList<>();
    private boolean sessionConnected = false;
    DeviceListAdapter mAdapter;
    private static final String ADAPTER_FRIENDLY_NAME = "My Android Things device";
    private static final int DISCOVERABLE_TIMEOUT_MS = 300;
    private static final int REQUEST_CODE_ENABLE_DISCOVERABLE = 100;
    private String mCurrentArchiveId;
    private String mPlayableArchiveId;
    private static final String UTTERANCE_ID =
            "com.example.androidthings.bluetooth.audio.UTTERANCE_ID";
    private Menu mMenu;
    TextView tv_name1, tv_name2, tv_name3, tv_name4, tv_name5, tv_name6, tv_name7, tv_name8, tv_name9, tv_name10, tv_name11, tv_name12, tv_name13, tv_name14, tv_name15, tv_name16, tv_name17, tv_name18, tv_name19, tv_name20, tv_name21, tv_name22, tv_name23, tv_name24, tv_name25, tv_name26, tv_name27, tv_name28, tv_name29, tv_name30;

    private PublisherKit.PublisherListener publisherListener = new PublisherKit.PublisherListener() {
        @Override
        public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
            Log.e("CheckLOg", "1");

            Log.d(TAG, "onStreamCreated: Own stream " + stream.getStreamId() + " created");
        }

        @Override
        public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
            Log.e("CheckLOg", "2");
            Toast.makeText(VedioConfranceScreen.this, "Stream Destroyed Reconnect Publisher", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onStreamDestroyed: Own stream " + stream.getStreamId() + " destroyed");
        }

        @Override
        public void onError(PublisherKit publisherKit, OpentokError opentokError) {
            Log.e("CheckLOg", "3");
            finishWithMessage("PublisherKit error: " + opentokError.getMessage());
        }
    };
    private SubscriberKit.AudioLevelListener audioStatsListener = new SubscriberKit.AudioLevelListener() {
        @Override
        public void onAudioLevelUpdated(SubscriberKit subscriberKit, float v) {
            Log.e("StramReciveddddddddddddddddddd", subscriberKit.getStream() + "");

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
            publisher = new Publisher.Builder(VedioConfranceScreen.this)
                    .name(CameraType)
                    .capturer(new CustomVideoCapturer(VedioConfranceScreen.this, Publisher.CameraCaptureResolution.HIGH, Publisher.CameraCaptureFrameRate.FPS_30))
                    .build();
            publisher.setPublisherListener(VedioConfranceScreen.this);

            publisher.setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);
            publisherViewContainer.addView(publisher.getView());

            if (publisher.getView() instanceof GLSurfaceView) {
                ((GLSurfaceView) (publisher.getView())).setZOrderOnTop(true);
            }

            session.publish(publisher);


        }

        @Override
        public void onDisconnected(Session session) {
            Log.d(TAG, "onDisconnected: disconnected from session " + session.getSessionId());
            sessionConnected = false;
            VedioConfranceScreen.this.session = null;
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
            final Subscriber subscriber = new Subscriber.Builder(VedioConfranceScreen.this, stream).build();
            checkparticipate.add(stream.getStreamId());
            if (CameraType.equals("Presenter Camera")) {
                try {

                    //  final Subscriber subscriber1 = new Subscriber.Builder(VedioConfranceScreen.this, stream).build();
                    session.subscribe(subscriber);
                    subscriberslist.add(subscriber);
                    subscriber.setAudioLevelListener(audioStatsListener);

                    addSubscriber(subscriber);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

          /*  session.subscribe(subscriber);

            addSubscriber(subscriber);*/
        }

        @Override
        public void onStreamDropped(Session session, Stream stream) {
            Log.d(TAG, "onStreamDropped: Stream " + stream.getStreamId() + " dropped from session " + session.getSessionId());
            Log.e("CheckLOg", "8");

            removeSubscriberWithStream(stream, session);
        }
    };

    AudioManager mAudioMgr;
    Context mContext;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //    VedioConfranceScreen vvv=new VedioConfranceScreen();
        // Log.e("MyHAshCode",vvv.toString());
        // Log.e("MyHAshCode", getClass().getName() + "@" + Integer.toHexString(hashCode()));


        mAudioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        Log.e("Orentation_eeee", "16");
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        disconnect = findViewById(R.id.disconnect);
        btn_sound = findViewById(R.id.btn_sound);
        mContext = getApplicationContext();
        btnrecord = findViewById(R.id.btnrecord);
        toggleVudioSubscriber0 = findViewById(R.id.toggleVudioSubscriber0);
        toggleVudioSubscriber1 = findViewById(R.id.toggleVudioSubscriber1);
        toggleVudioSubscriber2 = findViewById(R.id.toggleVudioSubscriber2);
        toggleVudioSubscriber3 = findViewById(R.id.toggleVudioSubscriber3);
        toggleVudioSubscriber4 = findViewById(R.id.toggleVudioSubscriber4);
        btn_muteall = findViewById(R.id.btn_muteall);

        toggleVudioSubscriber5 = findViewById(R.id.toggleVudioSubscriber5);
        toggleVudioSubscriber6 = findViewById(R.id.toggleVudioSubscriber6);
        toggleVudioSubscriber7 = findViewById(R.id.toggleVudioSubscriber7);
        toggleVudioSubscriber8 = findViewById(R.id.toggleVudioSubscriber8);
        toggleVudioSubscriber9 = findViewById(R.id.toggleVudioSubscriber9);


        toggleVudioSubscriber10 = findViewById(R.id.toggleVudioSubscriber10);
        toggleVudioSubscriber11 = findViewById(R.id.toggleVudioSubscriber11);
        toggleVudioSubscriber12 = findViewById(R.id.toggleVudioSubscriber12);
        toggleVudioSubscriber13 = findViewById(R.id.toggleVudioSubscriber13);
        toggleVudioSubscriber14 = findViewById(R.id.toggleVudioSubscriber14);


        toggleVudioSubscriber15 = findViewById(R.id.toggleVudioSubscriber15);
        toggleVudioSubscriber16 = findViewById(R.id.toggleVudioSubscriber16);
        toggleVudioSubscriber17 = findViewById(R.id.toggleVudioSubscriber17);
        toggleVudioSubscriber18 = findViewById(R.id.toggleVudioSubscriber18);
        toggleVudioSubscriber19 = findViewById(R.id.toggleVudioSubscriber19);


        toggleVudioSubscriber20 = findViewById(R.id.toggleVudioSubscriber20);
        toggleVudioSubscriber21 = findViewById(R.id.toggleVudioSubscriber21);
        toggleVudioSubscriber22 = findViewById(R.id.toggleVudioSubscriber22);
        toggleVudioSubscriber23 = findViewById(R.id.toggleVudioSubscriber23);
        toggleVudioSubscriber24 = findViewById(R.id.toggleVudioSubscriber24);


        toggleVudioSubscriber25 = findViewById(R.id.toggleVudioSubscriber25);
        toggleVudioSubscriber26 = findViewById(R.id.toggleVudioSubscriber26);
        toggleVudioSubscriber27 = findViewById(R.id.toggleVudioSubscriber27);
        toggleVudioSubscriber28 = findViewById(R.id.toggleVudioSubscriber28);
        toggleVudioSubscriber29 = findViewById(R.id.toggleVudioSubscriber29);


        toggleAudioSubscriber0 = findViewById(R.id.toggleAudioSubscriber0);

        toggleAudioSubscriber1 = findViewById(R.id.toggleAudioSubscriber1);
        toggleAudioSubscriber2 = findViewById(R.id.toggleAudioSubscriber2);
        toggleAudioSubscriber3 = findViewById(R.id.toggleAudioSubscriber3);
        toggleAudioSubscriber4 = findViewById(R.id.toggleAudioSubscriber4);

        btn_reconnctpublisher = findViewById(R.id.btn_reconnctpublisher);
        btn_reconnctpublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.unpublish(publisher);
                publisher = new Publisher.Builder(VedioConfranceScreen.this)
                        .name(CameraType)
                        .capturer(new CustomVideoCapturer(VedioConfranceScreen.this, Publisher.CameraCaptureResolution.HIGH, Publisher.CameraCaptureFrameRate.FPS_30))
                        .build();
                publisher.setPublisherListener(VedioConfranceScreen.this);

                publisher.setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);
                publisherViewContainer.addView(publisher.getView());

                if (publisher.getView() instanceof GLSurfaceView) {
                    ((GLSurfaceView) (publisher.getView())).setZOrderOnTop(true);
                }

                session.publish(publisher);
                Toast.makeText(VedioConfranceScreen.this, "Publisher Reconnect Sucessfully", Toast.LENGTH_SHORT).show();
            }
        });
        toggleAudioSubscriber5 = findViewById(R.id.toggleAudioSubscriber5);

        toggleAudioSubscriber6 = findViewById(R.id.toggleAudioSubscriber6);
        toggleAudioSubscriber7 = findViewById(R.id.toggleAudioSubscriber7);
        toggleAudioSubscriber8 = findViewById(R.id.toggleAudioSubscriber8);
        toggleAudioSubscriber9 = findViewById(R.id.toggleAudioSubscriber9);

        toggleAudioSubscriber10 = findViewById(R.id.toggleAudioSubscriber10);
        toggleAudioSubscriber11 = findViewById(R.id.toggleAudioSubscriber11);
        toggleAudioSubscriber12 = findViewById(R.id.toggleAudioSubscriber12);
        toggleAudioSubscriber13 = findViewById(R.id.toggleAudioSubscriber13);
        toggleAudioSubscriber14 = findViewById(R.id.toggleAudioSubscriber14);


        toggleAudioSubscriber15 = findViewById(R.id.toggleAudioSubscriber15);
        toggleAudioSubscriber16 = findViewById(R.id.toggleAudioSubscriber16);
        toggleAudioSubscriber17 = findViewById(R.id.toggleAudioSubscriber17);
        toggleAudioSubscriber18 = findViewById(R.id.toggleAudioSubscriber18);
        toggleAudioSubscriber19 = findViewById(R.id.toggleAudioSubscriber19);


        toggleAudioSubscriber20 = findViewById(R.id.toggleAudioSubscriber20);
        toggleAudioSubscriber21 = findViewById(R.id.toggleAudioSubscriber21);
        toggleAudioSubscriber22 = findViewById(R.id.toggleAudioSubscriber22);
        toggleAudioSubscriber23 = findViewById(R.id.toggleAudioSubscriber23);
        toggleAudioSubscriber24 = findViewById(R.id.toggleAudioSubscriber24);


        toggleAudioSubscriber25 = findViewById(R.id.toggleAudioSubscriber25);
        toggleAudioSubscriber26 = findViewById(R.id.toggleAudioSubscriber26);
        toggleAudioSubscriber27 = findViewById(R.id.toggleAudioSubscriber27);
        toggleAudioSubscriber28 = findViewById(R.id.toggleAudioSubscriber28);
        toggleAudioSubscriber29 = findViewById(R.id.toggleAudioSubscriber29);

        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
        String accountid = sh.getString("accountid", "");
        CameraType = sh.getString("CameraType", "");


        String MeetingidCheck = sh.getString("MeetingidCheck", "");
        accountiduser = sh.getString("accountid", "");

        RecordingStart = sh.getString("RecordingStart", "");
        btnrecordstop = findViewById(R.id.btnrecordstop);
        btnrecordstop.setVisibility(View.INVISIBLE);
        iv_startrecord = findViewById(R.id.iv_startrecord);
        iv_startrecord.setVisibility(View.INVISIBLE);
        if (RecordingStart.equals("Yes")) {
            btnrecord.setVisibility(View.GONE);
            btnrecordstop.setVisibility(View.VISIBLE);
            iv_startrecord.setVisibility(View.VISIBLE);
            Animation myFadeInAnimation = AnimationUtils.loadAnimation(VedioConfranceScreen.this, R.anim.blink);
            iv_startrecord.startAnimation(myFadeInAnimation);

        } else {
            btnrecord.setVisibility(View.VISIBLE);
            btnrecordstop.setVisibility(View.INVISIBLE);
        }
        SharedPreferences finalSh1 = sh;
        btnrecordstop.setOnClickListener(new View.OnClickListener() {
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
                        "RecordingStart",
                        "No");


                // checkcameratype = checkcameratype[0];
// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
                myEdit.commit();
                Toast.makeText(VedioConfranceScreen.this, "Recording Stopped", Toast.LENGTH_SHORT).show();
                Animation myFadeInAnimation = AnimationUtils.loadAnimation(VedioConfranceScreen.this, R.anim.blink);
                iv_startrecord.getAnimation().cancel();
                iv_startrecord.clearAnimation();
                myFadeInAnimation.setAnimationListener(null);
                iv_startrecord.setVisibility(View.GONE);
                btnrecord.setVisibility(View.VISIBLE);
                btnrecordstop.setVisibility(View.INVISIBLE);
                final ProgressDialog progressDialogMeeting = new ProgressDialog(VedioConfranceScreen.this, R.style.StyledDialog);

                progressDialogMeeting.setMessage("Loading  ...");
                progressDialogMeeting.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        "https://indeo.in/admin/stoparchive.php",
                        new com.android.volley.Response.Listener<String>() {
                            @Override

                            public void onResponse(String response) {
                                Log.e("PARAMSEND33", response.toString());

                                Toast.makeText(VedioConfranceScreen.this, "Recording Stop", Toast.LENGTH_SHORT).show();
                                progressDialogMeeting.dismiss();

                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(VedioConfranceScreen.this, "Recording Api Fail", Toast.LENGTH_SHORT).show();

                                Log.d("error", "onErrorResponse: error email" + error.getMessage());
                                progressDialogMeeting.dismiss();

                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        archiveid = finalSh1.getString("archiveid", "");
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("archiveId", archiveid);
                        params.put("accountid", accountiduser);
                        Log.e("PARAMSEND33", params.toString());
                        return params;
                    }

                };
                // mRequestQueue.add(stringRequest);

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        30000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                VolleySingelton.getInstance(VedioConfranceScreen.this).getRequestQueue()
                        .add(stringRequest);


            }
        });
        Sessionid = sh.getString("Sessionid", "");
        btnrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheackRecordingStart = "Yes";

                StartRecordingApi();


                ///secondapi


            }
        });
        btn_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogselect = new Dialog(VedioConfranceScreen.this);
                dialogselect.setContentView(R.layout.sound);
                Button btn_close = dialogselect.findViewById(R.id.btn_close);
                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogselect.dismiss();
                    }
                });
//                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                // dialogselect.setCancelable(false);
                dialogselect.show();

                // RadioGroup radioGroup = (RadioGroup) dialogselect.findViewById(R.id.radioButtonGroup);

                RadioGroup radioGroup1 = (RadioGroup) dialogselect.findViewById(R.id.radioGroup);
                radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        try {
                            int selectedId = radioGroup1.getCheckedRadioButtonId();
                            RadioButton genderradioButton = (RadioButton) dialogselect.findViewById(selectedId);
                            final AudioManager[] mAudioMgr = {(AudioManager) getSystemService(Context.AUDIO_SERVICE)};
                            Toast.makeText(VedioConfranceScreen.this, genderradioButton.getText(), Toast.LENGTH_SHORT).show();
                            if (genderradioButton.getText().toString().equals("Head Phone")) {

                                mAudioMgr[0].setMode(AudioManager.MODE_IN_COMMUNICATION);
                                mAudioMgr[0].setSpeakerphoneOn(false);
                                mAudioMgr[0].setWiredHeadsetOn(true);
                                Toast.makeText(getApplicationContext(), "Wired Headset On", Toast.LENGTH_LONG).show();

                                dialogselect.dismiss();


                            } else if (genderradioButton.getText().toString().equals("Speaker")) {
                                mAudioMgr[0] = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                                mAudioMgr[0].setWiredHeadsetOn(false);
                                mAudioMgr[0].setSpeakerphoneOn(true);
                                mAudioMgr[0].setMode(AudioManager.MODE_IN_COMMUNICATION);

                                Toast.makeText(getApplicationContext(), "SpeakerPhone On", Toast.LENGTH_LONG).show();

                                dialogselect.dismiss();


                            } else {
                                dialogselect.dismiss();
                                dialogselectBluetoothList = new Dialog(VedioConfranceScreen.this);
                                dialogselectBluetoothList.setContentView(R.layout.blutoothlist);
//                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialogselectBluetoothList.setCancelable(false);
                                dialogselectBluetoothList.show();

                                b1 = (Button) dialogselectBluetoothList.findViewById(R.id.button);
                                b2 = (Button) dialogselectBluetoothList.findViewById(R.id.button2);
                                b3 = (Button) dialogselectBluetoothList.findViewById(R.id.button3);
                                b4 = (Button) dialogselectBluetoothList.findViewById(R.id.button4);

                                b3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                                        java.util.Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                                        if (pairedDevices == null || pairedDevices.size() == 0) {
                                            showToast("Please pair atlest One Device");
                                            startActivity(new Intent(
                                                    Settings.ACTION_BLUETOOTH_SETTINGS));
                                            dialogselectBluetoothList.dismiss();
                                        } else {
                                            ArrayList<BluetoothDevice> list = new ArrayList<BluetoothDevice>();
                                            Intent i = new Intent(getApplicationContext(), DeviceListActivityList.class);

                                            list.addAll(pairedDevices);

                                            dialogselectBluetoothList.dismiss();
                                            i.putParcelableArrayListExtra("device.list", list);
                                            startActivity(i);

                                        }


                                    }
                                });


                                BA = BluetoothAdapter.getDefaultAdapter();
                                lv = (ListView) dialogselectBluetoothList.findViewById(R.id.listView);
                                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                                        dialogselectBluetoothList.dismiss();
                                        // Object o = lv.getItemAtPosition(position);
                                        pairedDevices = BA.getBondedDevices();

                                        ArrayList list = new ArrayList();

                                        for (BluetoothDevice bt : pairedDevices)
                                            list.add(bt.getName());

                                        try {
                                            mAudioMgr[0] = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                                            mAudioMgr[0].setBluetoothA2dpOn(true);
                                            mAudioMgr[0].setBluetoothScoOn(true);
                                            mAudioMgr[0].setMode(AudioManager.MODE_IN_COMMUNICATION);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });

                                if (mBluetoothAdapter == null) {
                                    showUnsupported();
                                } else {
                                    mPairedBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialogselectBluetooth.dismiss();
                                            final Set<BluetoothDevice>[] pairedDevices = new Set[]{mBluetoothAdapter.getBondedDevices()};

                                            if (pairedDevices[0] == null || pairedDevices[0].size() == 0) {
                                                showToast("No Paired Devices Found");
                                            } else {
                                                Log.e("TEst", "1");
                                                dialogselectBluetoothList = new Dialog(VedioConfranceScreen.this);
                                                dialogselectBluetoothList.setContentView(R.layout.blutoothlist);
//                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                dialogselectBluetoothList.setCancelable(false);
                                                dialogselectBluetoothList.show();

                                                b1 = (Button) dialogselectBluetoothList.findViewById(R.id.button);
                                                b2 = (Button) dialogselectBluetoothList.findViewById(R.id.button2);
                                                b3 = (Button) dialogselectBluetoothList.findViewById(R.id.button3);
                                                b4 = (Button) dialogselectBluetoothList.findViewById(R.id.button4);

                                                BA = BluetoothAdapter.getDefaultAdapter();
                                                lv = (ListView) dialogselectBluetoothList.findViewById(R.id.listView);
                                                lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                                    @Override
                                                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                                                        dialogselectBluetoothList.dismiss();
                                                        pairedDevices[0] = BA.getBondedDevices();

                                                        ArrayList list = new ArrayList();

                                                        for (BluetoothDevice bt : pairedDevices[0])
                                                            list.add(bt.getName());
                                                        // AudioManager mAudioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

                                                        //  Toast.makeText(getApplicationContext(), "Showing Paired Devices", Toast.LENGTH_SHORT).show();
                                                        //  AudioManager mAudioMgr = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                                                        //  Log.e("Sizelist", list.size() + "");
                                        /*if (list.size() >= 1) {
                                            mAudioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                                            mAudioMgr.setBluetoothA2dpOn(true);
                                            mAudioMgr.setBluetoothScoOn(true);
                                            mAudioMgr.setMode(AudioManager.MODE_IN_COMMUNICATION);
                                        }*/
                                                        try {
                                                            mAudioMgr[0] = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                                                            mAudioMgr[0].setBluetoothA2dpOn(true);
                                                            mAudioMgr[0].setBluetoothScoOn(true);
                                                            mAudioMgr[0].setMode(AudioManager.MODE_IN_COMMUNICATION);
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                        //Toast.makeText(getApplicationContext(), "long clicked", Toast.LENGTH_SHORT).show();
                                                        return true;
                                                    }
                                                });

                                              /*  ArrayList<BluetoothDevice> list = new ArrayList<BluetoothDevice>();

                                                list.addAll(pairedDevices);

                                                Intent intent = new Intent(VedioConfranceScreen.this, TestDemo.class);

                                                intent.putParcelableArrayListExtra("device.list", list);

                                                startActivity(intent);*/
                                                //dialogselectBluetooth.dismiss();
                                            }
                                        }
                                    });

                                    mScanBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View arg0) {
                                            mBluetoothAdapter.startDiscovery();
                                        }
                                    });

                                    mActivateBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (mBluetoothAdapter.isEnabled()) {
                                                mBluetoothAdapter.disable();

                                                showDisabled();
                                            } else {
                                                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

                                                startActivityForResult(intent, 1000);

                                            }
                                        }
                                    });

                                    if (mBluetoothAdapter.isEnabled()) {
                                        showEnabled();
                                    } else {
                                        showDisabled();
                                    }
                                }

                                IntentFilter filter = new IntentFilter();

                                filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
                                filter.addAction(BluetoothDevice.ACTION_FOUND);
                                filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
                                filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

                                registerReceiver(mReceiver, filter);

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
        SharedPreferences finalSh = sh;
        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = null;
                dialog = new Dialog(VedioConfranceScreen.this);
                dialog.setContentView(R.layout.exitrecordingdialog);
                //   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                ImageView logo = dialog.findViewById(R.id.logo);
                String logo_url = finalSh.getString("logo_url", "");
                Glide
                        .with(VedioConfranceScreen.this)
                        .load(logo_url)
                        .centerCrop()
                        .fitCenter()
                        .into(logo);
                Button btnstoprecord = dialog.findViewById(R.id.btnstoprecord);

                Button btnclosesession = dialog.findViewById(R.id.btnclosesession);
                btnclosesession.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), MeetingSheduleScreen.class);
                        startActivity(i);
                        disconnectSession();
                        finish();
                    }
                });

                Button btncancel = dialog.findViewById(R.id.btncancel);

                Dialog finalDialog = dialog;
                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finalDialog.dismiss();
                    }
                });

                //disconnectSession();
                if (CheackRecordingStart.equals("Yes")) {

                    @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);


                    btnstoprecord.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(VedioConfranceScreen.this, "Recording Stopped", Toast.LENGTH_SHORT).show();

                        }
                    });


                } else {

                    dialog.show();
                }

                dialog.show();    // finish();
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
        sh = getSharedPreferences("MySharedPref", MODE_APPEND);
        CameraType = sh.getString("CameraType", "");
        Tokan = sh.getString("Tokan", "");
        Sessionid = sh.getString("Sessionid", "");
        api_key = sh.getString("api_key", "");
        Log.e("Tokan1", Tokan);
        Log.e("Tokan2", Sessionid);
        Log.e("Tokan3", api_key);
        participatevieew = findViewById(R.id.participatevieew);
        subscriberview1 = findViewById(R.id.subscriberview1);
        subscriberview0 = findViewById(R.id.subscriberview0);
        subscriberview2 = findViewById(R.id.subscriberview2);
        subscriberview3 = findViewById(R.id.subscriberview3);
        subscriberview4 = findViewById(R.id.subscriberview4);

        subscriberview5 = findViewById(R.id.subscriberview5);
        subscriberview6 = findViewById(R.id.subscriberview6);

        subscriberview7 = findViewById(R.id.subscriberview7);

        subscriberview8 = findViewById(R.id.subscriberview8);

        subscriberview9 = findViewById(R.id.subscriberview9);

        subscriberview10 = findViewById(R.id.subscriberview10);


        subscriberview11 = findViewById(R.id.subscriberview11);
        subscriberview12 = findViewById(R.id.subscriberview12);

        subscriberview13 = findViewById(R.id.subscriberview13);
        subscriberview14 = findViewById(R.id.subscriberview14);

        subscriberview15 = findViewById(R.id.subscriberview15);


        subscriberview16 = findViewById(R.id.subscriberview16);


        subscriberview17 = findViewById(R.id.subscriberview17);


        subscriberview18 = findViewById(R.id.subscriberview18);

        subscriberview19 = findViewById(R.id.subscriberview19);
        subscriberview20 = findViewById(R.id.subscriberview20);

        subscriberview21 = findViewById(R.id.subscriberview21);


        subscriberview22 = findViewById(R.id.subscriberview22);

        subscriberview23 = findViewById(R.id.subscriberview23);

        subscriberview24 = findViewById(R.id.subscriberview24);

        subscriberview25 = findViewById(R.id.subscriberview25);


        subscriberview26 = findViewById(R.id.subscriberview26);

        subscriberview27 = findViewById(R.id.subscriberview27);

        subscriberview28 = findViewById(R.id.subscriberview28);


        subscriberview29 = findViewById(R.id.subscriberview29);


        ImageView iv_britness = findViewById(R.id.iv_britness);
        iv_britness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // requestPermission();
                Context context = getApplicationContext();
                // Check whether has the write settings permission or not.
                boolean settingsCanWrite = Settings.System.canWrite(context);
                if (!settingsCanWrite) {
                    // If do not have write settings permission then open the Can modify system settings panel.
                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    startActivity(intent);
                } else {
                    // If has permission then show an alert dialog with message.
                    final Dialog dialog = new Dialog(VedioConfranceScreen.this);
                    dialog.setContentView(R.layout.briteness);
                    //   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.show();
                    SeekBar mSeekBar = (SeekBar) dialog.findViewById(R.id.seek_bar);
                    TextView mTextView = (TextView) dialog.findViewById(R.id.tv);

                    // Set the SeekBar initial progress from screen current brightness
                    int brightness = getScreenBrightness();
                    mSeekBar.setProgress(brightness);
                    mTextView.setText("Screen Brightness : " + brightness);

                    // Set a SeekBar change listener
                    mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                            // Display the current progress of SeekBar
                            mTextView.setText("Screen Brightness : " + i);

                            // Change the screen brightness
                            setScreenBrightness(i);
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });


                    // Change the screen brightness


                    Button btnok = dialog.findViewById(R.id.btnok);
                    btnok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }


            }
        });

        ToggleButton toggleAudio = findViewById(R.id.toggleAudio);
        ToggleButton toggleVideo = findViewById(R.id.toggleVideo);

        if (CameraType.equals("Presenter Camera")) {
            participatevieew.setVisibility(View.VISIBLE);
            toggleAudio.setVisibility(View.VISIBLE);
            toggleVideo.setVisibility(View.VISIBLE);
            btn_sound.setVisibility(View.VISIBLE);
        } else {


            participatevieew.setVisibility(View.GONE);
            toggleAudio.setVisibility(View.VISIBLE);
            toggleVideo.setVisibility(View.INVISIBLE);
            btn_sound.setVisibility(View.INVISIBLE);

        }
     /*   if (!OpenTokConfig.isValid()) {
            finishWithMessage("Invalid OpenTokConfig. " + OpenTokConfig.getDescription());
            return;
        }*/

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
            Display display = getWindowManager().getDefaultDisplay();

            int width = display.getWidth(); // ((display.getWidth()*20)/100)
            int height = display.getHeight();// ((display.getHeight()*30)/100)
            RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(width, height);
            publisherViewContainer.setLayoutParams(parms);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 100, 10, 100);
            publisherViewContainer.setLayoutParams(params);
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


                // disconnectSession1();
              /*  RelativeLayout subscriberview0 = findViewById(R.id.subscriberview0);
                subscriberview0.setVisibility(View.GONE);*/


                publisher.cycleCamera();
                //  publisher.autoFocus(myAutoFocusCallback);


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
                int tv_name = getResources().getIdentifier("tv_partname" + (new Integer(i)).toString(),
                        "id", this.getPackageName());
                int toggleAudioId = getResources().getIdentifier("toggleAudioSubscriber" + (new Integer(i)).toString(),
                        "id", this.getPackageName());
                int toggleVudioId = getResources().getIdentifier("toggleVudioSubscriber" + (new Integer(i)).toString(),
                        "id", this.getPackageName());

                Log.e("MyIds_sssss", containerId + "");
                subscribers.add(new SubscriberContainer(
                        findViewById(containerId),
                        findViewById(tv_name),
                        findViewById(toggleAudioId),
                        findViewById(toggleVudioId),

                        null
                ));
            }

        }

        requestPermissions();
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        java.util.Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices == null || pairedDevices.size() == 0) {
            Log.e("PairedDevice", "No");

        } else {
            new AlertDialog.Builder(VedioConfranceScreen.this)
                    .setTitle("InDeo")
                    .setMessage("Are you Sure want to connect with Blutooth Headphone?")
                    //.setIcon(R.drawable.image)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int id) {
                                    ArrayList<BluetoothDevice> list = new ArrayList<BluetoothDevice>();


                                    list.addAll(pairedDevices);
                                    Log.e("PairedDevice", list.size() + "");


                                    AudioManager mAudioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

                                    mAudioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                                    mAudioMgr.setBluetoothA2dpOn(true);
                                    mAudioMgr.setBluetoothScoOn(true);
                                    mAudioMgr.setMode(AudioManager.MODE_IN_COMMUNICATION);
                                    //Toast.makeText(MainActivity.this,"Male Selected",Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }
                            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {
                            //Toast.makeText(MainActivity.this,"Female Selected",Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    }).show();
            Log.e("PairedDevice", "Yes");


        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        // BA.enable();
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mBluetoothAdapter.enable();
        }

    }

    private void StartRecordingApi() {
        // secodapistartrecording();
        final ProgressDialog progressDialogMeeting = new ProgressDialog(VedioConfranceScreen.this, R.style.StyledDialog);
        //  progressDialog.setMessage("Meeting loaded ..."+countmeeting);
        progressDialogMeeting.setMessage("Loading  ...");
        progressDialogMeeting.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://indeo.in/admin/startarchive.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        Log.e("PARAMSEND11", response.toString());
                        try {
                            JSONObject js = new JSONObject(response.toString());

                            String status = js.getString("status");
                            if (status.equals("true")) {
                                //  secodapistartrecording();
                                String archiveid = js.getString("archiveid");
                                //   Log.e("PARAMSEND11", archiveid);

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
                                        "archiveid",
                                        archiveid);
                                myEdit.putString(
                                        "RecordingStart",
                                        "Yes");


                                // checkcameratype = checkcameratype[0];
// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
                                myEdit.commit();


// Storing the key and its value
// as the data fetched from edittext


                                // checkcameratype = checkcameratype[0];
// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error


                            } else {

                                Toast.makeText(VedioConfranceScreen.this, "Server Responce false", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        iv_startrecord.setVisibility(View.VISIBLE);
                        btnrecord.setVisibility(View.INVISIBLE);
                        btnrecordstop.setVisibility(View.VISIBLE);
                        Animation myFadeInAnimation = AnimationUtils.loadAnimation(VedioConfranceScreen.this, R.anim.blink);
                        iv_startrecord.startAnimation(myFadeInAnimation);
                        Toast.makeText(VedioConfranceScreen.this, "Recording Started", Toast.LENGTH_SHORT).show();
                        progressDialogMeeting.dismiss();

                        // secodapistartrecording();


                        // meetstatus.setText("");
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(context, Utils.VOLLEY_ERROR_MSG, Toast.LENGTH_SHORT).show();
                        // System.out.println("error "+error.getMessage());
                        Toast.makeText(VedioConfranceScreen.this, "Server Error", Toast.LENGTH_SHORT).show();

                        Log.d("error", "onErrorResponse: error email" + error.getMessage());
                        progressDialogMeeting.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("sessionid", Sessionid);
                params.put("accountid", accountiduser);
                Log.e("PARAMSEND11", params.toString());
                return params;
            }

        };
        // mRequestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingelton.getInstance(VedioConfranceScreen.this).getRequestQueue()
                .add(stringRequest);

    }



    public void setScreenBrightness(int brightnessValue) {
        /*
            public abstract ContentResolver getContentResolver ()
                Return a ContentResolver instance for your application's package.
        */
        /*
            Settings
                The Settings provider contains global system-level device preferences.

            Settings.System
                System settings, containing miscellaneous system preferences. This table holds
                simple name/value pairs. There are convenience functions for accessing
                individual settings entries.
        */
        /*
            public static final String SCREEN_BRIGHTNESS
                The screen backlight brightness between 0 and 255.
                Constant Value: "screen_brightness"
        */
        /*
            public static boolean putInt (ContentResolver cr, String name, int value)
                Convenience function for updating a single settings value as an integer. This will
                either create a new entry in the table if the given name does not exist, or modify
                the value of the existing row with that name. Note that internally setting values
                are always stored as strings, so this function converts the given value to a
                string before storing it.

            Parameters
                cr : The ContentResolver to access.
                name : The name of the setting to modify.
                value : The new value for the setting.
            Returns
                true : if the value was set, false on database errors
        */

        // Make sure brightness value between 0 to 255
        if (brightnessValue >= 0 && brightnessValue <= 255) {
            Settings.System.putInt(
                    mContext.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS,
                    brightnessValue
            );
        }
    }

    // Get the screen current brightness
    protected int getScreenBrightness() {
        /*
            public static int getInt (ContentResolver cr, String name, int def)
                Convenience function for retrieving a single system settings value as an integer.
                Note that internally setting values are always stored as strings; this function
                converts the string to an integer for you. The default value will be returned
                if the setting is not defined or not an integer.

            Parameters
                cr : The ContentResolver to access.
                name : The name of the setting to retrieve.
                def : Value to return if the setting is not defined.
            Returns
                The setting's current value, or 'def' if it is not defined or not a valid integer.
        */
        int brightnessValue = Settings.System.getInt(
                mContext.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS,
                0
        );
        return brightnessValue;
    }

    private void changeScreenOrientation() {
        int orientation = VedioConfranceScreen.this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (CameraType.equals("Presenter Camera")) {
                //final Subscriber subscriber = new Subscriber.Builder(VedioConfranceScreen.this, stream).build();

                Display display = getWindowManager().getDefaultDisplay();
                @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);

                int width = display.getWidth(); // ((display.getWidth()*20)/100)
                int height = display.getHeight();// ((display.getHeight()*30)/100)
                RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(width, height);
                participatevieew.setLayoutParams(parms);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 20, 10, 50);
                participatevieew.setLayoutParams(params);
                TextView tv_cameramode = findViewById(R.id.tv_cameramode);
                tv_cameramode.setVisibility(View.VISIBLE);
                String CameraType = sh.getString("CameraType", "");
                Log.e("cameraModeeee", CameraType);
                tv_cameramode.setText("You are Logged In With " + CameraType);
                //disconnectSession();
                check = "2";
            } else {
                check = "1";
                // publisher.setPublishAudio(false);
                //publisherViewContainer.setCh(false);

            }
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
        //changeScreenOrientation();
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

    }

    @Override
    protected void onDestroy() {
        Log.e("Orentation_eeee", "4");

        // disconnectSession();

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
            AppApiKey = api_key;
            AppSessionId = Sessionid;
            AppTokan = Tokan;
            Log.e("Check1", api_key);
            Log.e("Check2", Tokan);
            Log.e("Check3", Sessionid);


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
            try {
                if (subscriberContainer.subscriber.getStream().getStreamId().equals(stream.getStreamId())) {


                    return subscriberContainer;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private class TouchHandler implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
            }
            return true;
        }
    }

    private void addSubscriber(Subscriber subscriber) {
        Log.e("CheckLOg", "11");
        Log.e("Checkkkkk", "3");
        Log.d(TAG, "onStreamReceived2: New stream " + streammm.getName());
        checkparticipatenamelist.add(streammm.getName());
        HashSet<String> set = new HashSet<String>(checkparticipate);
        // Convert back to ArrayList
        checkparticipate = new ArrayList<String>(set);

        container = findFirstEmptyContainer(subscriber);
        Log.d(TAG, "onStreamReceived1: New stream " + container.subscriber);

        if (container == null) {
            Toast.makeText(this, "New subscriber ignored. MAX_NUM_SUBSCRIBERS limit reached", Toast.LENGTH_LONG).show();
            return;
        }
        // Log.e("Checkkkkk", subscriber.getView()+"");
        container.subscriber = subscriber;
        container.container.addView(subscriber.getView());
        Log.e("CheckHashCode", container.toString() + "");
        count++;
        ParticipateList.add(count);
        mapparticipate.put(container.container.getId() + "", count + "");

        subscriber.setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);
        if (CameraType.equals("Presenter Camera")) {

        } else {
            subscriber.setSubscribeToAudio(false);
        }


        container.tv_partcname.setVisibility(View.VISIBLE);
        container.tv_partcname.setText(streammm.getName());
        container.toggleAudio.setVisibility(View.VISIBLE);
        container.container.setVisibility(View.VISIBLE);
        container.toggleVudio.setVisibility(View.VISIBLE);
        container.toggleVudio.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                container.subscriber.setSubscribeToVideo(true);
            } else {
                container.subscriber.setSubscribeToVideo(false);
            }

        });

        container.toggleAudio.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                container.subscriber.setSubscribeToAudio(true);
            } else {
                container.subscriber.setSubscribeToAudio(false);
            }

        });
        // subscriber.setSubscribeToVideo(false);
        if (session == null || !sessionConnected) {
            return;
        }

        sessionConnected = false;
        try {
            Log.e("Seesionidcheck", session.getSessionId());
            // Toast.makeText(this, "session Id:-" + session.getSessionId(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (CheackRecordingStart.equals("Yes")) {
            final Dialog dialog = new Dialog(VedioConfranceScreen.this);
            dialog.setContentView(R.layout.exitrecordingdialog);
            //   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);

            ImageView logo = dialog.findViewById(R.id.logo);
            String logo_url = sh.getString("logo_url", "");
            Glide
                    .with(this)
                    .load(logo_url)
                    .centerCrop()
                    .fitCenter()
                    .into(logo);
            Button btnstoprecord = dialog.findViewById(R.id.btnstoprecord);
            btnstoprecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(VedioConfranceScreen.this, "Recording Stopped", Toast.LENGTH_SHORT).show();

                }
            });
            Button btnclosesession = dialog.findViewById(R.id.btnclosesession);
            btnclosesession.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), MeetingSheduleScreen.class);
                    startActivity(i);
                    disconnectSession();
                    finish();
                }
            });
            Button btncancel = dialog.findViewById(R.id.btncancel);
            btncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else {
            Intent i = new Intent(getApplicationContext(), MeetingSheduleScreen.class);
            startActivity(i);
            finish();
            disconnectSession();
        }

        // finish();


    }

    private void removeSubscriberWithStream(Stream stream, Session session1) {

        Log.e("Checkkkkk", "4");
        if (CameraType.equals("Presenter Camera")) {
            SubscriberContainer container = findContainerForStream(stream);

            if (container == null) {
                return;
            }

            container.container.removeView(container.subscriber.getView());


            // subscriberslist.remove(container.subscriber.getView());
            Log.e("RemoveSubscriberName", container.subscriber + "");
            // Log.e("ContainerPossition", container.container.getId()+"");

            //  mapparticipate.get(container.container.getId());
            String val = mapparticipate.get(container.container.getId() + "");
            Log.e("ContainerPossition1", val);
            if (val.equals("1")) {
                subscriberview0.setVisibility(View.GONE);
                Log.e("ContainerPossition2", val);
            } else if (val.equals("2")) {
                subscriberview1.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("3")) {
                subscriberview2.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("4")) {
                subscriberview3.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("5")) {
                subscriberview4.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("6")) {
                subscriberview5.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("7")) {
                subscriberview6.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("8")) {
                subscriberview7.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("9")) {
                subscriberview8.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("10")) {
                subscriberview9.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("11")) {
                subscriberview10.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("12")) {
                subscriberview11.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("13")) {
                subscriberview12.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("14")) {
                subscriberview13.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("15")) {
                subscriberview14.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("16")) {
                subscriberview15.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("17")) {
                subscriberview16.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("18")) {
                subscriberview17.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("19")) {
                subscriberview18.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("20")) {
                subscriberview19.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("21")) {
                subscriberview20.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("22")) {
                subscriberview21.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("23")) {
                subscriberview22.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("24")) {
                subscriberview23.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("25")) {
                subscriberview24.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("26")) {
                subscriberview25.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("27")) {
                subscriberview26.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("28")) {
                subscriberview27.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("29")) {
                subscriberview28.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            } else if (val.equals("30")) {
                subscriberview29.setVisibility(View.GONE);
                Log.e("ContainerPossition3", val);
            }
            //   sessionListener.onConnected(session);
            container.toggleAudio.setOnCheckedChangeListener(null);
            container.container.setVisibility(View.GONE);
            container.toggleAudio.setVisibility(View.INVISIBLE);
            container.container.setVisibility(View.GONE);
            container.toggleVudio.setVisibility(View.INVISIBLE);
            container.subscriber = null;
           /* subscribers.clear();
            disconnectSession1();
            session = new Session.Builder(this, api_key, Sessionid).build();
            session.setSessionListener(sessionListener);
            session.connect(Tokan);
            session.setReconnectionListener(new Session.ReconnectionListener() {
                @Override
                public void onReconnecting(Session session) {
                    Log.e("CheckLOg", "100");
                }

                @Override
                public void onReconnected(Session session) {
                    Log.e("CheckLOg", "101");
                }
            });*/
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

    private void showEnabled() {
        mStatusTv.setText("Bluetooth is On");
        mStatusTv.setTextColor(Color.BLUE);

        mActivateBtn.setText("Disable");
        mActivateBtn.setEnabled(true);

        mPairedBtn.setEnabled(true);
        mScanBtn.setEnabled(true);
    }

    private void showDisabled() {
        mStatusTv.setText("Bluetooth is Off");
        mStatusTv.setTextColor(Color.RED);

        mActivateBtn.setText("Enable");
        mActivateBtn.setEnabled(true);

        mPairedBtn.setEnabled(false);
        mScanBtn.setEnabled(false);
    }

    private void showUnsupported() {
        mStatusTv.setText("Bluetooth is unsupported by this device");

        mActivateBtn.setText("Enable");
        mActivateBtn.setEnabled(false);

        mPairedBtn.setEnabled(false);
        mScanBtn.setEnabled(false);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                if (state == BluetoothAdapter.STATE_ON) {
                    showToast("Enabled");

                    showEnabled();
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                mDeviceList = new ArrayList<BluetoothDevice>();

                mProgressDlg.show();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                mProgressDlg.dismiss();

                Intent newIntent = new Intent(VedioConfranceScreen.this, TestDemo.class);

                newIntent.putParcelableArrayListExtra("device.list", mDeviceList);

                startActivity(newIntent);
                dialogselectBluetooth.dismiss();

            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(EXTRA_DEVICE);

                mDeviceList.add(device);

                showToast("Found device " + device.getName());
            }
        }
    };

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
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        this.finish();
    }

    public void on(View v) {
        if (!BA.isEnabled()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            BA.enable();
            Toast.makeText(getApplicationContext(), "Please Turned on Bluetooth ", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Bluetooth is on", Toast.LENGTH_LONG).show();
        }
    }

    public void off(View v) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        BA.disable();
        Toast.makeText(getApplicationContext(), "Turned off", Toast.LENGTH_LONG).show();
        AudioManager mAudioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        mAudioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mAudioMgr.setBluetoothA2dpOn(false);
        mAudioMgr.setBluetoothScoOn(false);
        mAudioMgr.setMode(AudioManager.MODE_IN_COMMUNICATION);

        dialogselectBluetoothList.dismiss();
    }


    public void visible(View v) {
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivityForResult(getVisible, 0);
    }


    public void list(View v) {
        if (!BA.isEnabled()) {

            Toast.makeText(getApplicationContext(), "Turned on Bluetooth ", Toast.LENGTH_LONG).show();
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            pairedDevices = BA.getBondedDevices();

            ArrayList list = new ArrayList();

            for (BluetoothDevice bt : pairedDevices) list.add(bt.getName());
            Toast.makeText(getApplicationContext(), "Showing Paired Devices", Toast.LENGTH_SHORT).show();
            AudioManager mAudioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            Log.e("Sizelist", list.size() + "");
            if (list.size() >= 1) {
                mAudioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                mAudioMgr.setBluetoothA2dpOn(true);
                mAudioMgr.setBluetoothScoOn(true);
                mAudioMgr.setMode(AudioManager.MODE_IN_COMMUNICATION);
            }
           /* final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);

            lv.setAdapter(adapter);*/

            mAdapter = new DeviceListAdapter(VedioConfranceScreen.this);
            mDeviceList = getIntent().getExtras().getParcelableArrayList("device.list");
            mAdapter.setData(mDeviceList);
            mAdapter.setListener(new DeviceListAdapter.OnPairButtonClickListener() {
                @Override
                public void onPairButtonClick(int position) {
                    BluetoothDevice device = mDeviceList.get(position);

                    if (ActivityCompat.checkSelfPermission(VedioConfranceScreen.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                        unpairDevice(device);
                    } else {
                        showToast("Pairing...");

                        pairDevice(device);
                    }
                }
            });

            lv.setAdapter(mAdapter);

            registerReceiver(mPairReceiver, new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED));

            //Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
        }


        // finish();
    }

    private void pairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("createBond", (Class[]) null);
            method.invoke(device, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unpairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("removeBond", (Class[]) null);
            method.invoke(device, (Object[]) null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final BroadcastReceiver mPairReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
                final int prevState = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);

                if (state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING) {
                    showToast("Paired");
                } else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED) {
                    showToast("Unpaired");
                }

                mAdapter.notifyDataSetChanged();
            }
        }
    };

}
