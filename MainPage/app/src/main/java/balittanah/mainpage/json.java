package balittanah.mainpage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class json extends AppCompatActivity {

    EditText edt;
    Button action;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        edt = findViewById(R.id.edt);
        action = findViewById(R.id.action);
        txt = findViewById(R.id.txt);

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendStr = edt.getText().toString();

                tampungStr(sendStr);

//                Intent intent = new Intent(getApplicationContext(), json.class);
//                intent.putExtra("message", sendStr);
//                startActivity(intent);
            }
        });
    }

    public void tampungStr(String sendStr){
        final String tampung = sendStr;
        tampilStr(tampung);
    }

    public void tampilStr(String tampung){
        txt.setText(tampung);
    }

    @Override
    protected void onResume() {
//        Intent i = getIntent();
//        String getStr = i.getStringExtra("message");
//        txt.setText(getStr);
        super.onResume();
    }
}