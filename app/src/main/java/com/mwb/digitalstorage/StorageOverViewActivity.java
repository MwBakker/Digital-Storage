package com.mwb.digitalstorage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import com.mwb.digitalstorage.command_handlers.StorageCmdHandler;
import com.mwb.digitalstorage.command_handlers.entity.PhotoCmdHandler;
import com.mwb.digitalstorage.database.CompanyRepository;
import com.mwb.digitalstorage.database.StorageRepository;
import com.mwb.digitalstorage.databinding.ActivityStorageOverviewBinding;
import com.mwb.digitalstorage.misc.ImageProcessor;
import com.mwb.digitalstorage.modelUI.UIEntity;
import com.mwb.digitalstorage.modelUI.UIStorage;
import com.mwb.digitalstorage.viewmodel.StorageOverViewViewModel;
import com.mwb.digitalstorage.viewmodel.ToolbarViewModel;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


public class StorageOverViewActivity extends BaseActivity
{
    private StorageOverViewViewModel storageOverViewVM;
    private ImageProcessor imgProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        StorageCmdHandler mainViewCmdHandler = storageCmdHandler();

        imgProcessor = new ImageProcessor();

        ToolbarViewModel toolbarVM = new ToolbarViewModel("Storages");
        storageOverViewVM = ViewModelProviders.of(this).get(StorageOverViewViewModel.class);
        storageOverViewVM.setViewModelElements(this, new CompanyRepository(getApplication()),
                                                                new StorageRepository(getApplication()), mainViewCmdHandler);

        ActivityStorageOverviewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_storage_overview);

        binding.setVm(storageOverViewVM);
        binding.setTbvm(toolbarVM);
        binding.setCmdHandler(mainViewCmdHandler);
        binding.setPhotoCmdHandler(photoCmdHandler());
        binding.setTbCmdHandler(getToolbarCmdHandler("Main menu", 0L));
    }

    //
    //  command handler for the storage
    //
    private StorageCmdHandler storageCmdHandler()
    {
        return new StorageCmdHandler()
        {
            @Override
            public void enterEntity(long storageID)
            {
                Intent intent = new Intent(StorageOverViewActivity.this, RackOverViewActivity.class);
                intent.putExtra("storage_id", storageID);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            @Override
            public void addNewEntity()
            {
                startActivity(new Intent(StorageOverViewActivity.this, StorageMenuActivity.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
            @Override
            public boolean editEntity(UIEntity uiEntity)
            {
                storageOverViewVM.setEditableStorage((UIStorage)uiEntity);
                return true;
            }
            @Override
            public void editEntityTitle(CharSequence s, int start, int before, int count)
            {
                if (storageOverViewVM.getUiStorage() != null )
                {
                    storageOverViewVM.getUiStorage().name.set(s.toString());
                }
            }
            @Override
            public void editStorageLocation(CharSequence s, int start, int before, int count)
            {
                storageOverViewVM.getUiStorage().location.set(s.toString());
            }
            @Override
            public void saveEntityEdit() { storageOverViewVM.saveStorageEdit(); }
            @Override
            public void deleteEntity() { storageOverViewVM.deleteStorage(); }
            @Override
            public void editCompany()
            {
                storageOverViewVM.editCompanyObs.set(true);
            }
            @Override
            public void editCompanyName(CharSequence s, int start, int before, int count) { }
            @Override
            public void saveCompanyEdit() { storageOverViewVM.saveCompanyEdit(getApplication()); }
        };
    }

    //
    //  command handler for the photo
    //
    private PhotoCmdHandler photoCmdHandler()
    {
        return new PhotoCmdHandler()
        {
            @Override
            public void takePhoto()
            {
                imgProcessor.setCamerabool(true);
                startActivityForResult(imgProcessor.dispatchTakePictureIntent(getApplicationContext(),
                                       getExternalFilesDir(Environment.DIRECTORY_PICTURES)), 1);
            }
            @Override
            public void browsePhoto()
            {
                imgProcessor.setCamerabool(false);
                startActivityForResult(new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI),1);
            }
            @Override
            public void removePhoto() { storageOverViewVM.removeStorageBitmap(); }
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
            storageOverViewVM.setCompanyBitmap(imgProcessor.getImgPath());
        }
        else
        {
            storageOverViewVM.companyImgObs.set(imgProcessor.browseImage(data, getApplication()));
        }
    }
}