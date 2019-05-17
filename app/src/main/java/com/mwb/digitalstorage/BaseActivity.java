package com.mwb.digitalstorage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.mwb.digitalstorage.command_handlers.ToolbarCmdHandler;
import com.mwb.digitalstorage.database.BaseRepository;
import com.mwb.digitalstorage.database.DAO;
import com.mwb.digitalstorage.database.RoomDB;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;


public abstract class BaseActivity extends AppCompatActivity
{
    static int RESULT_LOAD_IMAGE = 1;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        BaseRepository.setDao(getApplication());
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
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        };
    }

    //
    //  creates the possibility of setting variable heights
    //
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
