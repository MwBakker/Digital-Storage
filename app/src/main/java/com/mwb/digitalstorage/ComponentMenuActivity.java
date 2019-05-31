package com.mwb.digitalstorage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import com.mwb.digitalstorage.command_handlers.ComponentCategoryMenuCmdHandler;
import com.mwb.digitalstorage.command_handlers.SpinnerSetterCmdHandler;
import com.mwb.digitalstorage.databinding.ActivityComponentMenuBinding;
import com.mwb.digitalstorage.modelUI.UIComponentCategory;
import com.mwb.digitalstorage.viewmodel.ComponentMenuViewModel;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


public class ComponentMenuActivity extends AppCompatActivity
{
    private ComponentMenuViewModel componentMenuVM;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        ActivityComponentMenuBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_component_menu);

        componentMenuVM = ViewModelProviders.of(this).get(ComponentMenuViewModel.class);
        componentMenuVM.setViewModelElements(this, spinnerSetterCmdHandler(), getIntent().getLongExtra("rack_id", 0L));

        binding.setVm(componentMenuVM);
        binding.setCmdHandler(componentCategoryMenuCmdHandler());
    }

    //  sets the handler
    private ComponentCategoryMenuCmdHandler componentCategoryMenuCmdHandler()
    {
        // handlers and methods
        return new ComponentCategoryMenuCmdHandler()
        {
            @Override
            public void takePhoto()
            {
                startActivityForResult(componentMenuVM.imgProcessor.dispatchTakePictureIntent(getApplicationContext(),
                                        getExternalFilesDir(Environment.DIRECTORY_PICTURES)), 1);
            }
            @Override
            public void removePhoto() { componentMenuVM.getUiComponent().removeImg(); }
            @Override
            public void addNewCategory() { componentMenuVM.newCategoryObsv.set(true); }
            @Override
            public void removeNewCategory() { componentMenuVM.newCategoryObsv.set(false); }
            @Override
            public void browsePhoto()
            {
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI),1);
            }
            @Override
            public void saveNewEntity()
            {
                componentMenuVM.addComponent();
                switchBackToOverView();
            }
            @Override
            public void cancelMenu() { switchBackToOverView(); }
        };
    }

    //  sets the handler to set the spinner
    public SpinnerSetterCmdHandler spinnerSetterCmdHandler()
    {
        return new SpinnerSetterCmdHandler()
        {
            @Override
            public ArrayAdapter setComponentCategorySpinner(List<UIComponentCategory> uiComponentCategories)
            {
                return new ArrayAdapter<>(getApplicationContext(), R.layout.component_category_spinner_item,
                        uiComponentCategories);
            }
        };
    }

    //  immediately retrieves file from taken img
    //  sets the VM img resource property
    //  loadImg will be called on after trigger of set()
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (componentMenuVM.imgProcessor.isFromCamera())
        {
            componentMenuVM.getUiComponent().setImgPath(componentMenuVM.imgProcessor.getImgPath());
        }
        else
        {
            componentMenuVM.getUiComponent().setImgPath(componentMenuVM.imgProcessor.browseImage(data, getApplication()));
        }
        componentMenuVM.getUiComponent().imgObsv.set(componentMenuVM.imgProcessor.decodeImgPath());
    }

    //  handles activity switch
    private void switchBackToOverView()
    {
        Intent intent = new Intent(ComponentMenuActivity.this, ComponentOverViewActivity.class);
        intent.putExtra("rack_id", componentMenuVM.getRackID());
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
        finish();
    }
}