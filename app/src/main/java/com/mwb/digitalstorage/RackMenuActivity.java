package com.mwb.digitalstorage;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import com.mwb.digitalstorage.command_handlers.entity.EntityMenuCmdHandler;
import com.mwb.digitalstorage.command_handlers.entity.PhotoCmdHandler;
import com.mwb.digitalstorage.database.RackRepository;
import com.mwb.digitalstorage.databinding.ActivityRackMenuBinding;
import com.mwb.digitalstorage.misc.ImageProcessor;
import com.mwb.digitalstorage.viewmodel.RackMenuViewModel;
import com.mwb.digitalstorage.viewmodel.RackOverViewViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


public class RackMenuActivity extends AppCompatActivity
{
    private RackMenuViewModel rackMenuVM;
    private ImageProcessor imgProcessor;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityRackMenuBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_rack_menu);

        imgProcessor = new ImageProcessor();

        rackMenuVM = ViewModelProviders.of(this).get(RackMenuViewModel.class);
        rackMenuVM.setViewModelElements(new RackRepository(getApplication()),  getIntent().getLongExtra("storage_id", 0L));

        binding.setVm(rackMenuVM);
        binding.setCmdHandler(handler());
        binding.setPhotoCmdHandler(photoCmdHandler());
    }

    //
    //  sets the handlers
    //
    private EntityMenuCmdHandler handler()
    {
        // handlers and methods
        return new EntityMenuCmdHandler()
        {
            @Override
            public void takePhoto()
            {
                startActivityForResult(imgProcessor.dispatchTakePictureIntent(getApplicationContext(),
                        getExternalFilesDir(Environment.DIRECTORY_PICTURES)), 1);
            }
            @Override
            public void removePhoto() { rackMenuVM.removeRackImg(); }
            @Override
            public void addEntity()
            {
                rackMenuVM.addRack();
                switchToItem();
            }
            @Override
            public void browsePhoto()
            {
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI),1);
            }
            @Override
            public void cancelMenu() { switchToItem(); }
        };
    }

    //
    //  sets the handler of the photo
    //
    private PhotoCmdHandler photoCmdHandler()
    {
        // handlers and methods
        return new PhotoCmdHandler()
        {
            @Override
            public void takePhoto()
            {
                startActivityForResult(imgProcessor.dispatchTakePictureIntent(getApplicationContext(),
                        getExternalFilesDir(Environment.DIRECTORY_PICTURES)), 1);
            }
            @Override
            public void browsePhoto()
            {
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI),1);
            }
            @Override
            public void removePhoto() { rackMenuVM.removeRackImg(); }
        };
    }

    //
    //  immediately retrieves file from taken img
    //  sets the VM img resource property
    //  loadImg will be called on after trigger of set()
    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (imgProcessor.isFromCamera())
        {
            rackMenuVM.rackImgObs.set(imgProcessor.browseImage(data, getApplication()));

        }
        else { rackMenuVM.setRackBitmap(imgProcessor.getImgPath()); }
    }

    //
    //  handles activity switch
    //
    private void switchToItem()
    {
        Intent intent = new Intent(RackMenuActivity.this, RackOverViewActivity.class);
        intent.putExtra("storage_id", rackMenuVM.getStorageID());
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
    }
}