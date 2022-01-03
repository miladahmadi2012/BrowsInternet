package com.sample.sendhistory;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RatingBar;

public class WebviewSearch extends AppCompatActivity
{
    String url="";
    int index=-1;
    int rate=0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        WebView myWebView = findViewById(R.id.webView1);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        Intent i = getIntent();
        url=i.getStringExtra("address");
        index=Utils.getLastIndex();
        myWebView.loadUrl(url);
    }

    public void itemRatingPopup() {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(
                WebviewSearch.this);
        final RatingBar ratingBar = new RatingBar(WebviewSearch.this);
        ratingBar.setNumStars(5);
        ratingBar.setStepSize(1f);
        ratingBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout parent = new LinearLayout(WebviewSearch.this);
        parent.setGravity(Gravity.CENTER);
        parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        parent.addView(ratingBar);
        popDialog.setTitle("please rate");
        popDialog.setView(parent);
        // Button OK
        popDialog.setPositiveButton("rate",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("rating::",ratingBar.getRating()+"");
                        dialog.dismiss();
                        rate= (int) ratingBar.getRating();
                        Utils.saveSearchModel(new SearchModel(++index,url,rate));
                        WebviewSearch.this.finish();
                    }
                }).setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        WebviewSearch.this.finish();
                    }
                });
        popDialog.create();
        popDialog.show();
    }

    @Override
    public void onBackPressed() {
        Log.d("selecteditem","model ");
        itemRatingPopup();
    }
}