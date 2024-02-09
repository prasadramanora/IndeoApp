package com.RamanoraGlobal.InDeoAppNew.Configuration;

import android.webkit.URLUtil;

public class OpenTokConfig {
    /*
    Fill the following variables using your own Project info from the OpenTok dashboard
    https://dashboard.tokbox.com/projects
    */

    // Replace with a API key
 //   public static final String API_KEY = MeetingSheduleScreen.api_keyApp;

    // Replace with a generated Session ID
   // public static final String SESSION_ID = "2_MX40Njc4NzU4NH5-MTYxODg5OTM0MTQ4Mn5ZZFdqL3NoeXNMUnNwaWoyemJYN3g0UHB-fg";

    // Replace with a generated token (from the dashboard or using an OpenTok server SDK)
    //public static final String TOKEN = "T1==cGFydG5lcl9pZD00Njc4NzU4NCZzaWc9NzAyYTg1MmM0Y2EzZDAzNzk3N2U3NmQzMDdhN2JkNDUzMDcxNzRhZjpzZXNzaW9uX2lkPTJfTVg0ME5qYzROelU0Tkg1LU1UWXhPRGc1T1RNME1UUTRNbjVaWkZkcUwzTm9lWE5NVW5Od2FXb3llbUpZTjNnMFVIQi1mZyZjcmVhdGVfdGltZT0xNjE4ODk5Mzc1Jm5vbmNlPTAuMzA2NDQyMDg3MzUxMjkzNCZyb2xlPXB1Ymxpc2hlciZleHBpcmVfdGltZT0xNjE4OTg1NzczJmluaXRpYWxfbGF5b3V0X2NsYXNzX2xpc3Q9";

   /* public static boolean isValid() {
        if (TextUtils.isEmpty(OpenTokConfig.API_KEY)
                || TextUtils.isEmpty(MeetingSheduleScreen.Meetingsession)
                || TextUtils.isEmpty(MeetingSheduleScreen.MeetingTokan)) {
            return false;
        }

        return true;
    }*/

  /*  @NonNull
    public static String getDescription() {
        return "OpenTokConfig:" + "\n"
                + "API_KEY: " + OpenTokConfig.API_KEY + "\n"
                + "SESSION_ID: " + MeetingSheduleScreen.Meetingsession + "\n"
                + "TOKEN: " + MeetingSheduleScreen.MeetingTokan + "\n";
    }
*/
    public static final String CHAT_SERVER_URL = "https://indeo.in/api/admin/";
    public static final String SESSION_INFO_ENDPOINT = CHAT_SERVER_URL + "/session";
    public static final String ARCHIVE_START_ENDPOINT = CHAT_SERVER_URL + "/archive/start";
    public static final String ARCHIVE_STOP_ENDPOINT = CHAT_SERVER_URL + "/archive/:archiveId/stop";
    public static final String ARCHIVE_PLAY_ENDPOINT = CHAT_SERVER_URL + "/archive/:archiveId/view";

    public static String configErrorMessage;

    public static boolean isConfigUrlValid(){
        if (OpenTokConfig.CHAT_SERVER_URL == null) {
            configErrorMessage = "CHAT_SERVER_URL in OpenTokConfig.java must not be null";
            return false;
        } else if ( !( URLUtil.isHttpsUrl(OpenTokConfig.CHAT_SERVER_URL) || URLUtil.isHttpUrl(OpenTokConfig.CHAT_SERVER_URL)) ) {
            configErrorMessage = "CHAT_SERVER_URL in OpenTokConfig.java must be specified as either  http or https";
            return false;
        } else if ( !URLUtil.isValidUrl(OpenTokConfig.CHAT_SERVER_URL) ) {
            configErrorMessage = "CHAT_SERVER_URL in OpenTokConfig.java is not a valid URL";
            return false;
        } else {
            return true;
        }
    }
}
