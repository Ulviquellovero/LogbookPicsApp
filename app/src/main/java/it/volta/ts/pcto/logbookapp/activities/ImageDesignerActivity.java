package it.volta.ts.pcto.logbookapp.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import it.volta.ts.pcto.logbookapp.R;
import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.ViewType;

public class ImageDesignerActivity extends Activity
{
    private PhotoEditor mPhotoEditor;

    //---------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_designer);
        initVariables();
        initView();
    }

    //---------------------------------------------------------------------------------------------

    private void initVariables()
    {
        mPhotoEditor = null;
    }

    //---------------------------------------------------------------------------------------------

    private void initView()
    {
        configDesignBar();
        configPhotoEditor();
    }

    //---------------------------------------------------------------------------------------------

    private void configDesignBar()
    {
        ImageButton imgButton = findViewById(R.id.pencilBtn);
        imgButton.setOnClickListener(e -> onPencilBtnClick());
        imgButton = findViewById(R.id.eraserBtn);
        imgButton.setOnClickListener(e -> onEraserBtnClick());
        imgButton = findViewById(R.id.undoBtn);
        imgButton.setOnClickListener(e -> onUndoBtnClick());
        imgButton = findViewById(R.id.redoBtn);
        imgButton.setOnClickListener(e -> onRedoBtnClick());
        imgButton = findViewById(R.id.deleteBtn);
        imgButton.setOnClickListener(e -> onDeleteBtnClick());
        imgButton = findViewById(R.id.insTextBtn);
        imgButton.setOnClickListener(e -> onInsTextBtnClick());
    }

    //---------------------------------------------------------------------------------------------

    private void configPhotoEditor()
    {
        PhotoEditorView mPhotoEditorView = findViewById(R.id.photoEditorView);
        mPhotoEditorView.getSource().setImageResource(R.drawable.img);
        Typeface mTextRobotoTf = ResourcesCompat.getFont(this, R.font.roboto_medium);
        mPhotoEditor = new PhotoEditor.Builder(this, mPhotoEditorView)
                .setPinchTextScalable(true)
                .setClipSourceImage(true)
                .setDefaultTextTypeface(mTextRobotoTf)
                .build();
        configListeners();
    }

    //---------------------------------------------------------------------------------------------

    private void configListeners()
    {
        mPhotoEditor.setOnPhotoEditorListener(new OnPhotoEditorListener()
        {
            @Override
            public void onEditTextChangeListener(@Nullable View view, @Nullable String s, int i)
            {
                changeText(view, i);
            }
            @Override
            public void onAddViewListener(@Nullable ViewType viewType, int i) {}
            @Override
            public void onRemoveViewListener(@Nullable ViewType viewType, int i) {}
            @Override
            public void onStartViewChangeListener(@Nullable ViewType viewType) {}
            @Override
            public void onStopViewChangeListener(@Nullable ViewType viewType) {}
            @Override
            public void onTouchSourceImage(@Nullable MotionEvent motionEvent) {}
        });
    }

    //---------------------------------------------------------------------------------------------

    private void onPencilBtnClick()
    {
        mPhotoEditor.setBrushDrawingMode(true);
    }

    //---------------------------------------------------------------------------------------------

    private void onEraserBtnClick()
    {
        mPhotoEditor.brushEraser();
    }

    //---------------------------------------------------------------------------------------------

    private void onUndoBtnClick()
    {
        mPhotoEditor.undo();
    }

    //---------------------------------------------------------------------------------------------

    private void onRedoBtnClick()
    {
        mPhotoEditor.redo();
    }

    //---------------------------------------------------------------------------------------------

    private void onDeleteBtnClick()
    {
        mPhotoEditor.clearAllViews();
    }

    //---------------------------------------------------------------------------------------------

    private void onInsTextBtnClick()
    {
        mPhotoEditor.addText("Enter Text", R.color.black);
    }

    //---------------------------------------------------------------------------------------------

    private void changeText(View view, int i)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter text");
        final EditText editText = new EditText(this);
        builder.setView(editText);
        builder.setPositiveButton("OK", (dialog, which) -> {
            String inputText = editText.getText().toString();
            mPhotoEditor.editText(view, inputText, i);
            dialog.dismiss();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
