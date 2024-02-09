package com.RamanoraGlobal.InDeoAppNew.Activity;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.RamanoraGlobal.InDeoAppNew.Configuration.OpenTokConfig;

import org.json.JSONException;
import org.json.JSONObject;

public class WebServiceCoordinator {

    private static final String LOG_TAG = "WebServiceCoordinator.class";

    private static RequestQueue reqQueue;

    private final Context context;
    private Listener delegate;

    public WebServiceCoordinator(Context context, Listener delegate) {
        this.context = context;
        this.delegate = delegate;
        this.reqQueue = Volley.newRequestQueue(context);
    }

    public void fetchSessionConnectionData() {
        Log.e("Startrecord","yes");
        delegate.onSessionConnectionDataReady(VedioConfranceScreen.AppApiKey, VedioConfranceScreen.AppSessionId, VedioConfranceScreen.AppTokan);

       /* RequestQueue reqQueue = Volley.newRequestQueue(context);
            reqQueue.add(
                    new JsonObjectRequest(
                        Request.Method.GET,
                        sessionInfoUrlEndpoint,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String apiKey = response.getString("46787584");
                                    String sessionId = response.getString("1_MX40Njc4NzU4NH5-MTYyMDEyMjMyMzkxN34wSnRsZm9ObWU1ZVJCRG1jZ09KOTJTVzF-QX4");
                                    String token = response.getString("T1==cGFydG5lcl9pZD00Njc4NzU4NCZzaWc9ZDM1NjYwZmFmNzVlOTQ2OTU2OWQ4YTYxMTFkNmU0MzFhNGVlYmU1MDpzZXNzaW9uX2lkPTFfTVg0ME5qYzROelU0Tkg1LU1UWXlNREV5TWpNeU16a3hOMzR3U25Sc1ptOU9iV1UxWlZKQ1JHMWpaMDlLT1RKVFZ6Ri1RWDQmY3JlYXRlX3RpbWU9MTYyMDEyMjMyMyZyb2xlPXB1Ymxpc2hlciZub25jZT0xNjIwMTIyMzIzLjk1MjMxMjIxMTU0NTg0JmluaXRpYWxfbGF5b3V0X2NsYXNzX2xpc3Q9");

                                    Log.i(LOG_TAG, "WebServiceCoordinator returned session information");

                                    delegate.onSessionConnectionDataReady(apiKey, sessionId, token);
                                } catch (JSONException e) {
                                    delegate.onWebServiceCoordinatorError(e);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                delegate.onWebServiceCoordinatorError(error);
                            }
                        }
                    )
            );*/
    }

    public static interface Listener {
        void onSessionConnectionDataReady(String apiKey, String sessionId, String token);
        void onWebServiceCoordinatorError(Exception error);
    }

    public void startArchive(String sessionId) {
        JSONObject postBody = null;
        try {
            postBody = new JSONObject("{\"sessionId\": \"" + sessionId + "\"}");
        } catch (JSONException e){
            Log.e(LOG_TAG, "Parsing json body failed");
            e.getStackTrace();
        }

        this.reqQueue.add(new JsonObjectRequest(Request.Method.POST, OpenTokConfig.ARCHIVE_START_ENDPOINT,
                postBody,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(LOG_TAG, "archive started");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                delegate.onWebServiceCoordinatorError(error);
            }
        }));
    }

    public void stopArchive(String archiveId) {
        String requestUrl = OpenTokConfig.ARCHIVE_STOP_ENDPOINT.replace(":archiveId", archiveId);
        this.reqQueue.add(new JsonObjectRequest(Request.Method.POST, requestUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(LOG_TAG, "archive stopped");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                delegate.onWebServiceCoordinatorError(error);
            }
        }));
    }

    public Uri archivePlaybackUri(String archiveId) {
        return Uri.parse(OpenTokConfig.ARCHIVE_PLAY_ENDPOINT.replace(":archiveId", archiveId));
    }
}

