package eu.elettra.logbookpics.activities;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import eu.elettra.logbookpics.MainActivity;
import eu.elettra.logbookpics.R;

public class AboutActivity extends Activity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_about);

        ((ImageView)findViewById(R.id.git)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open browser with github repo
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(getResources().getString(R.string.git_url)));
                startActivity(browserIntent);
            }
        });

        ((ImageView)findViewById(R.id.docs)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open browser with readthedocs
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(getResources().getString(R.string.readthedocs_url)));
                startActivity(browserIntent);
            }
        });

        ((ImageButton)findViewById(R.id.close_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AboutActivity.this, MainActivity.class));
            }
        });
    }
}