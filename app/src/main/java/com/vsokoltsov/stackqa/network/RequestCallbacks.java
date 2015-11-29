package com.vsokoltsov.stackqa.network;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vsokoltsov on 05.11.15.
 */
public interface RequestCallbacks {
    public void successCallback(String requestName, JSONObject object) throws JSONException;
    public void failureCallback(String requestName, VolleyError error);
}
