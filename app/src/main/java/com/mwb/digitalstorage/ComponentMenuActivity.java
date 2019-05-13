package com.mwb.digitalstorage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import com.mwb.digitalstorage.command_handlers.ComponentCategoryMenuCmdHandler;
import com.mwb.digitalstorage.database.ComponentRepository;
import com.mwb.digitalstorage.databinding.ActivityComponentMenuBinding;
import com.mwb.digitalstorage.misc.ImageProcessor;
import com.mwb.digitalstorage.viewmodel.ComponentMenuViewModel;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


public class ComponentMenuActivity extends AppCompatActivity
{
    private ComponentMenuViewModel componentVM;
    private ImageProcessor imgProcessor;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        ActivityComponentMenuBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_component_menu);

        //ItemSpinnerCmdHandler itemSpinnerCmdHandler = spinnerHandler();

        componentVM = ViewModelProviders.of(this).get(ComponentMenuViewModel.class);
        componentVM.setViewModelElements(new ComponentRepository(getApplication()), this, this.getApplicationContext(), getIntent().getLongExtra("rack_id", 0L));

        binding.setVm(componentVM);
        binding.setCmdHandler(handlers());

        imgProcessor = new ImageProcessor();
    }

    //
    //  sets the handlers
    //
    private ComponentCategoryMenuCmdHandler handlers()
    {
        // handlers and methods
        return new ComponentCategoryMenuCmdHandler()
        {
            @Override
            public void takePhoto()
            {
                startActivityForResult(imgProcessor.dispatchTakePictureIntent(getApplicationContext(),
                                        getExternalFilesDir(Environment.DIRECTORY_PICTURES)), 1);
            }
            @Override
            public void removePhoto() { componentVM.removePhoto(); }
            @Override
            public void addNewCategory() { componentVM.newCategoryObs.set(!componentVM.newCategoryObs.get()); }
            @Override
            public void addEntity()
            {
                componentVM.addComponent();
                switchActivity(ComponentOverViewActivity.class);
            }
            @Override
            public void browsePhoto()
            {

            }
            @Override
            public void cancelMenu()
            {
                switchActivity(ComponentOverViewActivity.class);
            }
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
        componentVM.setComponentBitmap(imgProcessor.getImgPath());
    }

    //
    //  handles activity switch
    //
    void switchActivity(Class classType)
    {
        Intent intent = new Intent(ComponentMenuActivity.this, classType);
        intent.putExtra("rack_id", componentVM.getRackID());
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}