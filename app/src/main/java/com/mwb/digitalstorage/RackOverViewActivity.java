package com.mwb.digitalstorage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import com.mwb.digitalstorage.command_handlers.RackCmdHandler;
import com.mwb.digitalstorage.command_handlers.entity.PhotoCmdHandler;
import com.mwb.digitalstorage.database.RackRepository;
import com.mwb.digitalstorage.database.StorageRepository;
import com.mwb.digitalstorage.databinding.ActivityRackOverviewBinding;
import com.mwb.digitalstorage.misc.ImageProcessor;
import com.mwb.digitalstorage.modelUI.UIEntity;
import com.mwb.digitalstorage.modelUI.UIRack;
import com.mwb.digitalstorage.viewmodel.RackOverViewViewModel;
import com.mwb.digitalstorage.viewmodel.ToolbarViewModel;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


public class RackOverViewActivity extends BaseActivity
{
    private RackOverViewViewModel rackOverViewVM;
    private ImageProcessor imgProcessor;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        imgProcessor = new ImageProcessor();

        RackCmdHandler rackCmdHandler = rackCmdHandler();
        PhotoCmdHandler photoCmdHandler = photoCmdHandler();

        rackOverViewVM = ViewModelProviders.of(this).get(RackOverViewViewModel.class);
        rackOverViewVM.setViewModelElements(getIntent().getLongExtra("storage_id", 0L), new StorageRepository(getApplication()),
                                            new RackRepository(getApplication()), this, rackCmdHandler, photoCmdHandler);

        ToolbarViewModel tbVM = new ToolbarViewModel("Racks");

        ActivityRackOverviewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_rack_overview);
        binding.setVm(rackOverViewVM);
        binding.setTbvm(tbVM);
        binding.setRackCmdHandler(rackCmdHandler);
        binding.setTbCmdHandler(getToolbarCmdHandler("Storage", rackOverViewVM.getStorageID()));
    }

    //
    //  sets the rack handlers
    //
    private RackCmdHandler rackCmdHandler()
    {
        return new RackCmdHandler()
        {
            @Override
            public void enterEntity(long rackID)
            {
                Intent intent = new Intent(RackOverViewActivity.this, ComponentOverViewActivity.class);
                intent.putExtra("rack_id", rackID);
                intent.putExtra("storage_id", rackOverViewVM.getStorageID());
                switchToItem(intent);
            }
            @Override
            public void addNewEntity()
            {
                Intent intent = new Intent(RackOverViewActivity.this, RackMenuActivity.class);
                intent.putExtra("storage_id", rackOverViewVM.getStorageID());
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
            public void editEntityTitle(CharSequence s, int start, int before, int count)
            {
                rackOverViewVM.getUiRack().rackName.set(s.toString());
            }
            @Override
            public void saveEntityEdit() { rackOverViewVM.saveRackEdit(); }
            @Override
            public void deleteEntity() { rackOverViewVM.deleteRack(); }
        };
    }

    //
    //  photo command handler
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
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI),1);
            }
            @Override
            public void removePhoto() { rackOverViewVM.getUiRack().removeImg();  }
        };
    }

    //
    //  after photo taken
    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (imgProcessor.isFromCamera())
        {
            rackOverViewVM.getUiRack().setImgPath(imgProcessor.getImgPath());
        }
        else {  }
    }

    //
    //  handles the backButton
    //
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        switchToItem(new Intent(RackOverViewActivity.this, StorageOverViewActivity.class));
    }

    //
    //  switch to item
    //
    private void switchToItem(Intent intent)
    {
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
