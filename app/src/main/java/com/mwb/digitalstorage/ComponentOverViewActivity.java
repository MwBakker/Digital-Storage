package com.mwb.digitalstorage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import com.mwb.digitalstorage.adapter.ComponentCategoryListAdapter;
import com.mwb.digitalstorage.adapter.ComponentListAdapter;
import com.mwb.digitalstorage.command_handlers.ComponentCategoryCmdHandler;
import com.mwb.digitalstorage.command_handlers.ComponentCmdHandler;
import com.mwb.digitalstorage.command_handlers.SpinnerSetterCmdHandler;
import com.mwb.digitalstorage.command_handlers.entity.ImgCmdHandler;
import com.mwb.digitalstorage.command_handlers.entity.RetrieveEntityCmdHandler;
import com.mwb.digitalstorage.databinding.ActivityComponentOverviewBinding;
import com.mwb.digitalstorage.modelUI.UIComponent;
import com.mwb.digitalstorage.modelUI.UIComponentCategory;
import com.mwb.digitalstorage.modelUI.UIEntity;
import com.mwb.digitalstorage.modelUI.UIRack;
import com.mwb.digitalstorage.viewmodel.ComponentOverViewViewModel;
import com.mwb.digitalstorage.viewmodel.ToolbarViewModel;
import java.util.List;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


public class ComponentOverViewActivity extends BaseActivity
{
    private long rackID;

    private ComponentOverViewViewModel componentOverViewVM;
    private ComponentListAdapter componentListAdapter;
    private SpinnerSetterCmdHandler spinnerSetterCmdHandlerCallBack;

    private List<UIComponentCategory> uiComponentCategories;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        rackID = getIntent().getLongExtra("rack_id", 0L);

        // handler
        ComponentCmdHandler componentCmdHandler = componentCmdHandler();
        spinnerSetterCmdHandlerCallBack = spinnerSetterCmdHandler();

        // vms
        ToolbarViewModel tbVM = new ToolbarViewModel("Components");
        componentOverViewVM = ViewModelProviders.of(this).get(ComponentOverViewViewModel.class);
        componentOverViewVM.setViewModelElements(rackID, retrieveEntityCmdHandler());

        // bindings
        ActivityComponentOverviewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_component_overview);
        binding.setTbvm(tbVM);
        binding.setVm(componentOverViewVM);
        binding.setComponentCmdHandler(componentCmdHandler);
        binding.setTbCmdHandler(toolbarCmdHandler());

        componentOverViewVM.getUIComponentCategories(rackID).observe(this, uiComponentCategories ->
        {
            this.uiComponentCategories = uiComponentCategories;
            binding.setComponentCategoryListAdapter(new ComponentCategoryListAdapter(uiComponentCategories, componentCategoryCmdHandler()));
        });

        componentOverViewVM.getUIComponents(rackID).observe(this, uiComponents ->
        {
            componentListAdapter = new ComponentListAdapter(uiComponentCategories, uiComponents, componentCmdHandler,
                                                            spinnerSetterCmdHandler(), imgCmdHandler());
            componentOverViewVM.setUiComponentProperties(uiComponents);
            binding.setComponentListAdapter(componentListAdapter);
        });
    }

    //  sets the handler for the item (component category)
    private ComponentCategoryCmdHandler componentCategoryCmdHandler()
    {
        return new ComponentCategoryCmdHandler()
        {
            @Override
            public void sort(UIComponentCategory uiComponentCategory)
            {
                sortComponents(uiComponentCategory);
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
                intent.putExtra("rack_id", componentOverViewVM.getUiRack().id);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
            }
            @Override
            public boolean editEntity(UIEntity uiEntity)
            {
                ((UIComponent)uiEntity).uiComponentCategorySpinnerAdapterObsv.set(spinnerSetterCmdHandlerCallBack.setComponentCategorySpinner(uiComponentCategories));
                componentOverViewVM.setEditableComponent((UIComponent)uiEntity);
                return true;
            }
            @Override
            public void saveEntity(boolean isNew) { saveEdit(); }
            @Override
            public void deleteEntity() { componentOverViewVM.deleteComponent(); }
        };
    }

    //  retrieval command handler
    public RetrieveEntityCmdHandler retrieveEntityCmdHandler()
    {
        return new RetrieveEntityCmdHandler()
        {
            @Override
            public void entityRetrieved(UIEntity uiEntity)
            {
                componentOverViewVM.setUIRack((UIRack)uiEntity);
            }
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
            public void removePhoto() { componentOverViewVM.getEditableUiComponent().removeImg();  }
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

    //  performs sorting on the components per selected category
    private void sortComponents(UIComponentCategory toBeSortedUiComponentCategory)
    {
        if (componentOverViewVM.getPreviousSortComponentCategory().getID() != toBeSortedUiComponentCategory.getID())
        {
            componentOverViewVM.repositoryFactory.componentRepository.getCategoryFilteredComponents(rackID, toBeSortedUiComponentCategory.getID()).observe(this, uiComponents ->
            {
                componentListAdapter.setComponents(uiComponents);
            });
            componentOverViewVM.getPreviousSortComponentCategory().setSelectedState();
            componentOverViewVM.setPreviousSortComponentCategory(toBeSortedUiComponentCategory);
            componentOverViewVM.getPreviousSortComponentCategory().setSelectedState();
        }
        else
        {
            componentOverViewVM.repositoryFactory.componentRepository.getRackComponents(rackID).observe(this, components ->
            {
                componentListAdapter.setComponents(components);
            });
            componentOverViewVM.getPreviousSortComponentCategory().setSelectedState();
            componentOverViewVM.setPreviousSortComponentCategory(new UIComponentCategory(0L, "", 0));
        }
    }

    //  performs the editing of the editable component
    private void saveEdit()
    {
        componentOverViewVM.repositoryFactory.componentRepository.getAllComponentCategories().observe(this, uiComponentCategories ->
        {
            long categoryID = uiComponentCategories.get(componentOverViewVM.getEditableUiComponent().selectedCategoryInListObsv.get()).getID();
            UIComponent editableUIComponent = componentOverViewVM.getEditableUiComponent();
            componentOverViewVM.repositoryFactory.componentRepository.editComponent(editableUIComponent.getId(), categoryID, editableUIComponent.getName(),
                    editableUIComponent.getCode(), editableUIComponent.getImgPath());
        });
    }


    //  immediately retrieves file from taken img
    //  sets the VM img resource property
    //  loadImg will be called on after trigger of set()
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (componentOverViewVM.imgProcessor.isFromCamera())
        {
            componentOverViewVM.getEditableUiComponent().setImgPath(componentOverViewVM.imgProcessor.getImgPath());
        }
        else
        {
            componentOverViewVM.getEditableUiComponent().setImgPath(componentOverViewVM.imgProcessor.browseImage(data, getApplication()));
        }
        componentOverViewVM.getEditableUiComponent().imgObsv.set(componentOverViewVM.imgProcessor.decodeImgPath());
    }
}