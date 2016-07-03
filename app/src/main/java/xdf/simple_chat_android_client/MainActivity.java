package xdf.simple_chat_android_client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private EditText send_message;
    private String socketRemoteServer = "http://192.168.11.101:8081/app.html?server=ws://192.168.11.101:5678/";
    private static final String TAG = "android-chat-sample";
    private UUID uuid = UUID.randomUUID();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
        webView.loadUrl(socketRemoteServer);

        send_message = (EditText) findViewById(R.id.send_message);

        View.OnKeyListener onKey=new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    Editable editable = send_message.getText();
                    webView.loadUrl("javascript:sendMessage(\"" + uuid.toString() + "\", \"" + editable.toString() + "\")");
                    send_message.setText("");
                    return true;
                }
                return false;
            }
        };

        send_message.setOnKeyListener(onKey);

    }
}