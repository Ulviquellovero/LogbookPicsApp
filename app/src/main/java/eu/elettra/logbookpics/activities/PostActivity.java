package eu.elettra.logbookpics.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import eu.elettra.logbookpics.MainActivity;
import eu.elettra.logbookpics.R;
import eu.elettra.logbookpics.json.JSONTask;
import eu.elettra.logbookpics.singleton.QrCodeInfo;

public class PostActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        AppCompatButton post = (AppCompatButton)findViewById(R.id.dopost);
        AppCompatButton notpost = (AppCompatButton)findViewById(R.id.donotpost);

        // Do posting
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post.setEnabled(false);
                QrCodeInfo.jsonTask.uploadJSON(QrCodeInfo.uploadUrl, new JSONTask.JSONCallback() {
                    @Override
                    public void onCallbackSuccessful() {
                        Toast.makeText(PostActivity.this, "Your changes are saved", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PostActivity.this, AboutActivity.class));
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