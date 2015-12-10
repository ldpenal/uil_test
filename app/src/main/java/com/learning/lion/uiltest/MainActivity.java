package com.learning.lion.uiltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.learning.lion.uiltest.processor.ImageWorkerBridge;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button;

    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new OkHttpClient();

        final ImageWorkerBridge imageWorkerBridge = new ImageWorkerBridge(this);
        Log.d("ldpenal", imageWorkerBridge.getSimpleImageLoader().getInstance().getClass().toString());
        Log.d("ldpenal", imageWorkerBridge.getSnapImageLoader().getInstance().getClass().toString());

        Request imagesRequest = new Request.Builder()
                .url("https://2b4c7faa43849aa52f97:4639f7e6952563af3ebcaa0ca9205cdefe3f9e44@api.shutterstock.com/v2/images/search?query=c&per_page=500")
                .header("Authorization", "Basic MmI0YzdmYWE0Mzg0OWFhNTJmOTc6NDYzOWY3ZTY5NTI1NjNhZjNlYmNhYTBjYTkyMDVjZGVmZTNmOWU0NA==")
                .build();

        client.newCall(imagesRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException {
                ResponseBody body = response.body();
                MediaType mediaType = body.contentType();

                if (mediaType.equals(MediaType.parse("application/json; charset=utf8"))) {
                    String bodyString = new String(body.bytes());

                    try {
                        JSONObject data = new JSONObject(bodyString);
                        JSONArray array = data.getJSONArray("data");

                        // final String url = array.getJSONObject(i).getJSONObject("assets").getJSONObject("large_thumb").getString("url");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        imageView = (ImageView) findViewById(R.id.test);
        button = (Button) findViewById(R.id.destroy);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
