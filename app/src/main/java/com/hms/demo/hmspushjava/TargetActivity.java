package com.hms.demo.hmspushjava;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;

import java.util.Set;

public class TargetActivity extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        editText=findViewById(R.id.editTextTextMultiLine);
        Bundle b=getIntent().getExtras();
        for(String key:b.keySet()){
            String entry=key+":"+b.getString(key)+"\n";
            editText.append(entry);
        }
        //}
    }
}