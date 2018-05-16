package com.example.lado.webview;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button search;
    EditText enterURL;
    WebView webView;
    private static final String TAG = "Main";
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = (Button) findViewById(R.id.Go);
        enterURL = (EditText) findViewById(R.id.enterURL);
        webView = (WebView) findViewById(R.id.webView);

        webView.setWebViewClient(new MyBrowser());

        //progressBar = ProgressDialog.show(MainActivity.this, "WebView Example", "Loading...");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = enterURL.getText().toString();
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(url);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if(webView.canGoBack()) {
                        webView.goBack();
                    }
                    else {
                        finish();
                    }
                    return true;
            }
        }
            return super.onKeyDown(keyCode, event);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progressBar = ProgressDialog.show(MainActivity.this, "WebView Example", "Loading...");
            Log.i(TAG, "Finished loading URL: " +url);
            enterURL.setText(url);
            if (progressBar.isShowing()) {
                progressBar.dismiss();
            }
        }
    }
}
