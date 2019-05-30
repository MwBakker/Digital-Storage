package com.mwb.digitalstorage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import com.mwb.digitalstorage.command_handlers.ComponentCategoryCmdHandler;
import com.mwb.digitalstorage.command_handlers.ComponentCmdHandler;
import com.mwb.digitalstorage.command_handlers.entity.ImgCmdHandler;
import com.mwb.digitalstorage.databinding.ActivityComponentOverviewBinding;
import com.mwb.digitalstorage.modelUI.UIComponent;
import com.mwb.digitalstorage.modelUI.UIComponentCategory;
import com.mwb.digitalstorage.modelUI.UIEntity;
import com.mwb.digitalstorage.viewmodel.ComponentOverViewViewModel;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


public class ComponentOverViewActivity extends BaseActivity
{
    private ComponentOverViewViewModel componentOverViewVM;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityComponentOverviewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_component_overview);

        ComponentCmdHandler componentCmdHandler = componentCmdHandler();
        ComponentCategoryCmdHandler componentCategoryCmdHandler = componentCategoryHandler();

        componentOverViewVM = ViewModelProviders.of(this).get(ComponentOverViewViewModel.class);
        componentOverViewVM.setViewModelElements(getIntent().getLongExtra("storage_id", 0L), getIntent().getLongExtra("rack_id", 0L),
                                                this, componentCategoryCmdHandler, componentCmdHandler(), imgCmdHandler());

        binding.setComponentCmdHandler(componentCmdHandler);
        binding.setTbCmdHandler(toolbarCmdHandler());
        binding.setVm(componentOverViewVM);
    }

    //  sets the handler for the item (component category)
    private ComponentCategoryCmdHandler componentCategoryHandler()
    {
        return new ComponentCategoryCmdHandler()
        {
            @Override
            public void sort(UIComponentCategory uiComponentCategory)
            {
                componentOverViewVM.sort(uiComponentCategory);
            }
            @Override
            public boolean editComponentCategory(UIComponentCategory uiComponentCategory)
            {
                componentOverViewVM.setEditableComponentCategory(uiComponentCategory);
                return false;
            }
            @Override
            public void saveEdit() { componentOverViewVM.saveComponentCategoryEdit(); }
        };
    }

    //  component handler
    public ComponentCmdHandler componentCmdHandler()
    {
        return new ComponentCmdHandler()
        {
            @Override
            public void enterEntity(long id) { }
            @Override
            public void addNewEntity()
            {
                Intent intent = new Intent(ComponentOverViewActivity.this, ComponentMenuActivity.class);
                intent.putExtra("rack_id", componentOverViewVM.getRackID());
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
            }
            @Override
            public boolean editEntity(UIEntity uiEntity)
            {
                componentOverViewVM.executor.execute(() ->
                {
                    componentOverViewVM.setEditableComponent((UIComponent) uiEntity);
                });
                return true;
            }
            @Override
            public void saveEntity(boolean isNew) { componentOverViewVM.saveComponentEdit(); }
            @Override
            public void deleteEntity() { componentOverViewVM.deleteComponent(); }
        };
    }

    //  image cmd handler
    public ImgCmdHandler imgCmdHandler()
    {
        return new ImgCmdHandler()
        {
            @Override
            public void takePhoto()
            {
                componentOverViewVM.imgProcessor.setCamerabool(true);
                startActivityForResult(componentOverViewVM.imgProcessor.dispatchTakePictureIntent(getApplicationContext(),
                        getExternalFilesDir(Environment.DIRECTORY_PICTURES)), 1);
            }
            @Override
            public void browsePhoto()
            {
                componentOverViewVM.imgProcessor.setCamerabool(false);
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI),1);
            }
            @Override
            public void removePhoto() { componentOverViewVM.getUiComponent().removeImg();  }
        };
    }

    //  immediately retrieves file from taken img
    //  sets the VM img resource property
    //  loadImg will be called on after trigger of set()
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (componentOverViewVM.imgProcessor.isFromCamera())
        {
            componentOverViewVM.getUiComponent().setImgPath(componentOverViewVM.imgProcessor.getImgPath());
        }
        else
        {
            componentOverViewVM.getUiComponent().setImgPath(componentOverViewVM.imgProcessor.browseImage(data, getApplication()));
        }
        componentOverViewVM.getUiComponent().imgObsv.set(componentOverViewVM.imgProcessor.decodeImgPath());
    }
}