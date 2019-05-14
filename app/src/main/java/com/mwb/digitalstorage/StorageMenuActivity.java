package com.mwb.digitalstorage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import com.mwb.digitalstorage.command_handlers.StorageMenuCmdHandler;
import com.mwb.digitalstorage.command_handlers.entity.PhotoCmdHandler;
import com.mwb.digitalstorage.database.RackRepository;
import com.mwb.digitalstorage.database.StorageRepository;
import com.mwb.digitalstorage.databinding.ActivityStorageMenuBinding;
import com.mwb.digitalstorage.viewmodel.StorageMenuViewModel;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


public class StorageMenuActivity extends BaseActivity
{
    private StorageMenuViewModel storageMenuVM;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        ActivityStorageMenuBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_storage_menu);

        storageMenuVM = ViewModelProviders.of(this).get(StorageMenuViewModel.class);
        storageMenuVM.setViewModelElements();

        binding.setVm(storageMenuVM);
        binding.setCmdHandler(storageMenuCmdHandler());
        binding.setPhotoCmdHandler(photoCmdHandler());
    }

    //
    //  sets the handlers
    //
    private StorageMenuCmdHandler storageMenuCmdHandler()
    {
        // handlers and methods
        return new StorageMenuCmdHandler()
        {
            @Override
            public void addEntity()
            {
                storageMenuVM.addStorage(new RackRepository());
                switchBackToOverView();
            }
            @Override
            public void browsePhoto()
            {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
            @Override
            public void takePhoto()
            {
                startActivityForResult(storageMenuVM.imgProcessor.dispatchTakePictureIntent(getApplicationContext(),
                                  getExternalFilesDir(Environment.DIRECTORY_PICTURES)), 1);
            }
            @Override
            public void removePhoto() { storageMenuVM.storageImgObsv.set(null); }
            @Override
            public void numberPickerValChanged(int newVal) { storageMenuVM.storageRackAmountObsv.set(newVal); }
            @Override
            public void cancelMenu() {
                switchBackToOverView();
            }
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
                storageMenuVM.imgProcessor.setCamerabool(true);
                startActivityForResult(storageMenuVM.imgProcessor.dispatchTakePictureIntent(getApplicationContext(),
                                        getExternalFilesDir(Environment.DIRECTORY_PICTURES)), 1);
            }
            @Override
            public void browsePhoto()
            {
                storageMenuVM.imgProcessor.setCamerabool(false);
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI),1);
            }
            @Override
            public void removePhoto() { storageMenuVM.removeStoragePhoto(); }
        };
    }

    //
    //  after photo taken
    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (storageMenuVM.imgProcessor.isFromCamera())
        {
            storageMenuVM.setStorageBitmap(storageMenuVM.imgProcessor.getImgPath());
        }
        else { storageMenuVM.storageImgObsv.set(storageMenuVM.imgProcessor.browseImage(data, getApplication())); }
    }

    //
    //  overrides the back button pressed
    //
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        switchBackToOverView();
    }

    //
    //  handles activity switch
    //
    void switchBackToOverView()
    {
        Intent intent = new Intent(StorageMenuActivity.this, StorageOverViewActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
    }
}