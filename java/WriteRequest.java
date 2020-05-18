package com.example.admin.agency;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kind4 on 2018-06-10.
 */

public class WriteRequest extends StringRequest {

    final static private String URL = "http://1.255.57.7/Write.php";
    private Map<String, String> parameters;

    public WriteRequest(String newTitle, String writerName, String newContents, Response.Listener<String> listener)    {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("title", newTitle);
        parameters.put("writer", writerName);
        parameters.put("contents", newContents);
    }

    public Map<String, String> getParams()  {
        return parameters;
    }
}
