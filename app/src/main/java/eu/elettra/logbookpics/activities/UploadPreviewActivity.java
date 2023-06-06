package eu.elettra.logbookpics.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

public class UploadPreviewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_upload_preview);

        // setting image
        ((ImageView)findViewById(R.id.image_src_prev)).setImageBitmap(QrCodeInfo.imageBitmap);

        ((TextView) findViewById(R.id.title_title)).setText(QrCodeInfo.postJSON.optString("title"));
        ((TextView) findViewById(R.id.info_title)).setText(QrCodeInfo.postJSON.optString("info"));

        // setting components
        ComponentComposer.addPrettyViews(UploadPreviewActivity.this);
        ComponentComposer.addPrettyViewsToExsitingLinearLayout((LinearLayout) findViewById(R.id.components_container), UploadPreviewActivity.this);


        ((Button) findViewById(R.id.upload_prev_proceed)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadPreviewActivity.this, PostActivity.class));
            }
        });
    }
}