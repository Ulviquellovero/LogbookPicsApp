package eu.elettra.logbookpics.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import eu.elettra.logbookpics.R;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ((ImageButton)findViewById(R.id.git)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open browser with github repo
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(getResources().getString(R.string.git_url)));
                startActivity(browserIntent);
            }
        });

        ((ImageButton)findViewById(R.id.docs)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open browser with readthedocs
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(getResources().getString(R.string.readthedocs_url)));
                startActivity(browserIntent);
            }
        });
    }
}