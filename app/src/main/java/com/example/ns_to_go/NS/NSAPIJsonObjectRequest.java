package com.example.ns_to_go.NS;

import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class NSAPIJsonObjectRequest extends JsonRequest {
    public NSAPIJsonObjectRequest(int method, String url, @Nullable String requestBody, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Ocp-Apim-Subscription-Key", "af92321e469b43cb85b6f85c08103868");
        return headers;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        Response parsingResult;
        try {
            String responseInText = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            Log.d("INCOMING TEXT", responseInText);
            JSONObject returnObject = new JSONObject(responseInText);
            parsingResult = Response.success(returnObject, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JSONException e) {

            parsingResult = Response.error(new VolleyError("Returned info is not a JsonObject"));
        }
        return parsingResult;
    }
}
