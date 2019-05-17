package com.mwb.digitalstorage;

import android.content.Intent;
import android.os.Bundle;
import com.mwb.digitalstorage.command_handlers.ComponentCategoryCmdHandler;
import com.mwb.digitalstorage.command_handlers.ComponentCmdHandler;
import com.mwb.digitalstorage.command_handlers.ToolbarCmdHandler;
import com.mwb.digitalstorage.database.ComponentRepository;
import com.mwb.digitalstorage.database.RackRepository;
import com.mwb.digitalstorage.databinding.ActivityComponentOverviewBinding;
import com.mwb.digitalstorage.modelUI.UIComponent;
import com.mwb.digitalstorage.modelUI.UIComponentCategory;
import com.mwb.digitalstorage.modelUI.UIEntity;
import com.mwb.digitalstorage.viewmodel.ComponentOverViewViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


public class ComponentOverViewActivity extends AppCompatActivity
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
                                    this, componentCategoryCmdHandler, componentCmdHandler());

        binding.setComponentCmdHandler(componentCmdHandler);
        binding.setVm(componentOverViewVM);
    }

    //  sets the handler of the toolbar
    private ToolbarCmdHandler toolbarHandler()
    {
        return new ToolbarCmdHandler()
        {
            @Override
            public void openSearchActivity() { }
        };
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
            public boolean editComponentCat(UIComponentCategory uiComponentCategory)
            {
                componentOverViewVM.setEditableComponentCategory(uiComponentCategory);
                return false;
            }
            @Override
            public void editComponentName(CharSequence s, int start, int before, int count)
            {
                componentOverViewVM.getComponent().nameObsv.set(s.toString());
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
                //componentOverViewVM.setEditableComponentCategory((UIComponent)uiEntity);
                return true;
            }
            @Override
            public void editEntityTitle(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void saveEntity(boolean isNew) { componentOverViewVM.saveComponentEdit(); }
            @Override
            public void deleteEntity() { componentOverViewVM.deleteComponent(); }
        };
    }

    //  handles the back button
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(ComponentOverViewActivity.this, RackOverViewActivity.class);
        intent.putExtra("storage_id", componentOverViewVM.getStorageID());
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}