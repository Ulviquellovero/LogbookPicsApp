package eu.elettra.logbookpics.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import eu.elettra.logbookpics.MainActivity;
import eu.elettra.logbookpics.R;
import eu.elettra.logbookpics.component_system.ComponentComposer;
import eu.elettra.logbookpics.singleton.QrCodeInfo;

public class UploadPreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_preview);

        // setting image
        ((ImageView)findViewById(R.id.imagePrev)).setImageBitmap(QrCodeInfo.imageBitmap);

        ((TextView) findViewById(R.id.title_header)).setText("Title: "+QrCodeInfo.postJSON.optString("title"));
        ((TextView) findViewById(R.id.info_header)).setText("Info: "+QrCodeInfo.postJSON.optString("info"));

        // setting components
        ComponentComposer.addPrettyViews(UploadPreviewActivity.this);
        ComponentComposer.addPrettyViewsToExsitingLinearLayout((LinearLayout) findViewById(R.id.comp_container), UploadPreviewActivity.this);


        ((Button) findViewById(R.id.proceed_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadPreviewActivity.this, PostActivity.class));
            }
        });
    }
}