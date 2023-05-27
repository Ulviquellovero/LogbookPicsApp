package it.volta.ts.pcto.logbookapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import it.volta.ts.pcto.logbookapp.MainActivity;
import it.volta.ts.pcto.logbookapp.R;
import it.volta.ts.pcto.logbookapp.json.JSONTask;
import it.volta.ts.pcto.logbookapp.singleton.QrCodeInfo;

public class PostActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Button post = (Button)findViewById(R.id.dopost);
        Button notpost = (Button)findViewById(R.id.donotpost);

        // Do posting
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post.setEnabled(false);
                QrCodeInfo.jsonTask.uploadJSON(QrCodeInfo.uploadUrl, new JSONTask.JSONCallback() {
                    @Override
                    public void onCallbackSuccessful() {
                        Toast.makeText(PostActivity.this, "Your changes are saved", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCallbackFailed() {
                        post.setEnabled(true);
                        Toast.makeText(PostActivity.this, "An error occured while saving your changes", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Do not
        notpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PostActivity.this, MainActivity.class));
            }
        });

    }
}