package com.mwb.digitalstorage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.mwb.digitalstorage.command_handlers.ToolbarCmdHandler;
import com.mwb.digitalstorage.misc.ImageProcessor;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;


public abstract class BaseActivity extends AppCompatActivity
{
    static int RESULT_LOAD_IMAGE = 1;

    protected Executor executor;
    protected ImageProcessor imgProcessor;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        executor = Executors.newSingleThreadExecutor();
        imgProcessor = new ImageProcessor();
    }

    //
    //  command handler for the toolbar
    //
    public ToolbarCmdHandler getToolbarCmdHandler(String nameOfPrevious, long idOfPrevious)
    {
        return new ToolbarCmdHandler()
        {
            @Override
            public void openSearchActivity()
            {
                Intent intent = new Intent(BaseActivity.this, SearchActivity.class);
                intent.putExtra("previous_activity_id", idOfPrevious);
                intent.putExtra("previous_activity_name", nameOfPrevious);

                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        };
    }

    @BindingAdapter("android:layout_height")
    public static void setLayoutHeight(View view, float height)
    {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = Math.round(height);
        view.setLayoutParams(layoutParams);
    }

    //
    //  loadImg will be called on AS SOON AS
    //  the ViewModel property bound to imgSrc is called on
    //
    @BindingAdapter({"app:customSrc"})
    public static void loadImg(ImageView view, Bitmap imgBitmap)
    {
        view.setImageBitmap(imgBitmap);
    }
}
