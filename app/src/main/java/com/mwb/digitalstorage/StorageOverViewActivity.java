package com.mwb.digitalstorage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import com.mwb.digitalstorage.adapter.StorageListAdapter;
import com.mwb.digitalstorage.command_handlers.RegistrationCmdHandler;
import com.mwb.digitalstorage.command_handlers.RetrieveCompanyCmdHandler;
import com.mwb.digitalstorage.command_handlers.StorageCmdHandler;
import com.mwb.digitalstorage.databinding.ActivityStorageOverviewBinding;
import com.mwb.digitalstorage.modelUI.UICompany;
import com.mwb.digitalstorage.modelUI.UIEntity;
import com.mwb.digitalstorage.modelUI.UIStorage;
import com.mwb.digitalstorage.viewmodel.StorageOverViewViewModel;
import com.mwb.digitalstorage.viewmodel.ToolbarViewModel;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


public class StorageOverViewActivity extends BaseActivity
{
    private StorageOverViewViewModel storageOverViewVM;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // command handler
        StorageCmdHandler mainViewCmdHandler = storageCmdHandler();

        // viewModels
        ToolbarViewModel toolbarVM = new ToolbarViewModel("Main page");
        storageOverViewVM = ViewModelProviders.of(this).get(StorageOverViewViewModel.class);
        storageOverViewVM.getUICompanyFromDB(retrieveCompanyCmdHandler());

        // bindings
        ActivityStorageOverviewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_storage_overview);
        binding.setVm(storageOverViewVM);
        binding.setTbvm(toolbarVM);
        binding.setCmdHandler(mainViewCmdHandler);
        binding.setCompanyCmdHandler(registrationCmdHandler());
        binding.setTbCmdHandler(toolbarCmdHandler());
        // adapter
        storageOverViewVM.getStorageUnits().observe(this, storageUnits ->
        {
            binding.setStorageListAdapter(new StorageListAdapter(storageUnits, mainViewCmdHandler));
            storageOverViewVM.setStorageUnitCountingProperties(storageUnits);
        });
    }

    //  command handler for the storage
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
            public void saveEntity(boolean isNew) { storageOverViewVM.saveStorageEdit(); }
            @Override
            public void editStorageLocation(CharSequence s, int start, int before, int count)
            {
                storageOverViewVM.getUiStorage().setLocation(s.toString());
            }
            @Override
            public void deleteEntity() { storageOverViewVM.deleteStorage(); }
        };
    }

    //  handles the callback when the company's data is retrieved
    public RetrieveCompanyCmdHandler retrieveCompanyCmdHandler()
    {
        return new RetrieveCompanyCmdHandler()
        {
            @Override
            public void companyRetrieved(UICompany uiCompany)
            {
                storageOverViewVM.setUiCompany(uiCompany);
            }
        };
    }

    //  command handler for the photo
    private RegistrationCmdHandler registrationCmdHandler()
    {
        return new RegistrationCmdHandler()
        {
            @Override
            public void editCompany() { storageOverViewVM.getUiCompany().isEdit.set(true); }
            @Override
            public void takePhoto()
            {
                storageOverViewVM.imgProcessor.setCamerabool(true);
                startActivityForResult(storageOverViewVM.imgProcessor.dispatchTakePictureIntent(getApplicationContext(),
                        getExternalFilesDir(Environment.DIRECTORY_PICTURES)), 1);
            }
            @Override
            public void browsePhoto()
            {
                storageOverViewVM.imgProcessor.setCamerabool(false);
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI),1);
            }
            @Override
            public void removePhoto() { storageOverViewVM.getUiCompany().removeImg(); }
            @Override
            public void saveCompany() { storageOverViewVM.saveCompanyEdit(); }
        };
    }

    //  immediately retrieves file from taken img
    //  sets the VM img resource property
    //  loadImg will be called on after trigger of set()
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (storageOverViewVM.imgProcessor.isFromCamera())
        {
            storageOverViewVM.getUiCompany().setImgPath(storageOverViewVM.imgProcessor.getImgPath());
        }
        else
        {
            storageOverViewVM.getUiCompany().setImgPath(storageOverViewVM.imgProcessor.browseImage(data, getApplication()));
        }
        storageOverViewVM.getUiCompany().imgObsv.set(storageOverViewVM.imgProcessor.decodeImgPath());
    }
}