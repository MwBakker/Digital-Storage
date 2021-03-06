package com.mwb.digitalstorage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import com.mwb.digitalstorage.adapter.RackListAdapter;
import com.mwb.digitalstorage.command_handlers.RackCmdHandler;
import com.mwb.digitalstorage.command_handlers.entity.ImgCmdHandler;
import com.mwb.digitalstorage.command_handlers.entity.RetrieveEntityCmdHandler;
import com.mwb.digitalstorage.databinding.ActivityRackOverviewBinding;
import com.mwb.digitalstorage.modelUI.UIEntity;
import com.mwb.digitalstorage.modelUI.UIRack;
import com.mwb.digitalstorage.modelUI.UIStorage;
import com.mwb.digitalstorage.viewmodel.RackOverViewViewModel;
import com.mwb.digitalstorage.viewmodel.ToolbarViewModel;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


public class RackOverViewActivity extends BaseActivity
{
    private RackOverViewViewModel rackOverViewVM;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        long storageID = getIntent().getLongExtra("storage_id", 0L);

        // command handler
        RackCmdHandler rackCmdHandler = rackCmdHandler();

        // vms
        ToolbarViewModel tbVM = new ToolbarViewModel("Racks");
        rackOverViewVM = ViewModelProviders.of(this).get(RackOverViewViewModel.class);
        rackOverViewVM.getUiStorageFromDB(storageID, retrieveEntityCmdHandler());

        // bindings
        ActivityRackOverviewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_rack_overview);
        binding.setVm(rackOverViewVM);
        binding.setTbvm(tbVM);
        binding.setCmdHandler(rackCmdHandler);
        binding.setTbCmdHandler(toolbarCmdHandler());
        // adapter
        rackOverViewVM.getUIRacks(storageID).observe(this, uiRacks ->
        {
            binding.setRackListAdapter(new RackListAdapter(uiRacks, rackCmdHandler, imgCmdHandler()));
            rackOverViewVM.setUiRackProperties(uiRacks);
        });
    }

    //  sets the rack handlers
    private RackCmdHandler rackCmdHandler()
    {
        return new RackCmdHandler()
        {
            @Override
            public void enterEntity(long rackID)
            {
                Intent intent = new Intent(RackOverViewActivity.this, ComponentOverViewActivity.class);
                intent.putExtra("rack_id", rackID);
                switchToItem(intent);
            }
            @Override
            public void addNewEntity()
            {
                Intent intent = new Intent(RackOverViewActivity.this, RackMenuActivity.class);
                intent.putExtra("storage_id", rackOverViewVM.getUiStorage().id);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
            }
            @Override
            public boolean editEntity(UIEntity uiEntity)
            {
                rackOverViewVM.setEditableRack((UIRack)uiEntity);
                return true;
            }
            @Override
            public void saveEntity(boolean isNew) { rackOverViewVM.saveRackEdit(); }
            @Override
            public void deleteEntity() { rackOverViewVM.deleteRack(); }
        };
    }

    //  entity retrieval command handler
    public RetrieveEntityCmdHandler retrieveEntityCmdHandler()
    {
        return new RetrieveEntityCmdHandler()
        {
            @Override
            public void entityRetrieved(UIEntity uiEntity)
            {
                rackOverViewVM.setUiStorage((UIStorage)uiEntity);
            }
        };
    }

    //  photo command handler
    private ImgCmdHandler imgCmdHandler()
    {
        return new ImgCmdHandler()
        {
            @Override
            public void takePhoto()
            {
                rackOverViewVM.imgProcessor.setCamerabool(true);
                startActivityForResult(rackOverViewVM.imgProcessor.dispatchTakePictureIntent(getApplicationContext(),
                        getExternalFilesDir(Environment.DIRECTORY_PICTURES)), 1);
            }
            @Override
            public void browsePhoto()
            {
                rackOverViewVM.imgProcessor.setCamerabool(false);
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI),1);
            }
            @Override
            public void removePhoto() { rackOverViewVM.getUiRack().removeImg();  }
        };
    }

    //  after photo taken
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (rackOverViewVM.imgProcessor.isFromCamera())
        {
            rackOverViewVM.getUiRack().setImgPath(rackOverViewVM.imgProcessor.getImgPath());
        }
        else
        {
            rackOverViewVM.getUiRack().setImgPath(rackOverViewVM.imgProcessor.browseImage(data, getApplication()));
        }
        rackOverViewVM.getUiRack().imgObsv.set(rackOverViewVM.imgProcessor.decodeImgPath());
    }

    //  handles the backButton
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        switchToItem(new Intent(RackOverViewActivity.this, StorageOverViewActivity.class));
    }

    //  switch to item
    private void switchToItem(Intent intent)
    {
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
