package com.mwb.digitalstorage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import com.mwb.digitalstorage.command_handlers.entity.EntityMenuCmdHandler;
import com.mwb.digitalstorage.databinding.ActivityRackMenuBinding;
import com.mwb.digitalstorage.viewmodel.RackMenuViewModel;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


public class RackMenuActivity extends AppCompatActivity
{
    private RackMenuViewModel rackMenuVM;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityRackMenuBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_rack_menu);

        rackMenuVM = ViewModelProviders.of(this).get(RackMenuViewModel.class);
        rackMenuVM.setViewModelElements(getIntent().getLongExtra("storage_id", 0L));

        binding.setVm(rackMenuVM);
        binding.setCmdHandler(handler());
    }

    //  sets the handler
    private EntityMenuCmdHandler handler()
    {
        return new EntityMenuCmdHandler()
        {
            @Override
            public void takePhoto()
            {
                startActivityForResult(rackMenuVM.imgProcessor.dispatchTakePictureIntent(getApplicationContext(),
                        getExternalFilesDir(Environment.DIRECTORY_PICTURES)), 1);
            }
            @Override
            public void browsePhoto()
            {
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI),1);
            }
            @Override
            public void removePhoto() { rackMenuVM.getUiRack().removeImg(); }
            @Override
            public void saveNewEntity()
            {
                rackMenuVM.addRack();
                switchBackToOverView();
            }
            @Override
            public void cancelMenu() { switchBackToOverView(); }
        };
    }

    //  immediately retrieves file from taken img
    //  sets the VM img resource property
    //  loadImg will be called on after trigger of set()
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (rackMenuVM.imgProcessor.isFromCamera())
        {
            rackMenuVM.getUiRack().setImgPath(rackMenuVM.imgProcessor.getImgPath());
        }
        else
        {
            rackMenuVM.getUiRack().setImgPath(rackMenuVM.imgProcessor.browseImage(data, getApplication()));
        }
        rackMenuVM.getUiRack().imgObsv.set(rackMenuVM.imgProcessor.decodeImgPath());
    }

    @Override
    public void onBackPressed() { switchBackToOverView(); }

    //  handles activity switch
    private void switchBackToOverView()
    {
        Intent intent = new Intent(RackMenuActivity.this, RackOverViewActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
        finish();
    }
}