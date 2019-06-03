package com.mwb.digitalstorage.viewmodel;

import android.content.Context;
import android.widget.ArrayAdapter;
import com.mwb.digitalstorage.R;
import com.mwb.digitalstorage.command_handlers.SpinnerSetterCmdHandler;
import com.mwb.digitalstorage.database.ComponentRepository;
import com.mwb.digitalstorage.modelUI.UIComponent;
import com.mwb.digitalstorage.modelUI.UIComponentCategory;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LifecycleOwner;


public class ComponentMenuViewModel extends BaseViewModel
{
    private UIComponent uiComponent;
    private ComponentRepository componentRepository;
    private long rackID;
    private LifecycleOwner owner;

    public ObservableField<Integer> categoryListPositionObsv = new ObservableField<>();
    public ObservableField<ArrayAdapter<UIComponentCategory>> spinnerAdapterObsv = new ObservableField<>();
    // new category
    public ObservableField<String> newComponentCategoryNameObsv = new ObservableField<>();
    public ObservableField<Boolean> newCategoryObsv = new ObservableField<>(false);


    //  sets the elements belonging to the viewModel
    public void setViewModelElements(LifecycleOwner owner, SpinnerSetterCmdHandler spinnerSetterCmdHandlerCallback, long rackID)
    {
        this.rackID = rackID;
        this.owner = owner;
        uiComponent = new UIComponent(0L, rackID,0L,
                                     "","","", 0);
        componentRepository = new ComponentRepository(executor);
        componentRepository.getAllComponentCategories().observe(owner, uiComponentCategories ->
        {
            spinnerAdapterObsv.set(spinnerSetterCmdHandlerCallback.setComponentCategorySpinner(uiComponentCategories));
        });
    }

    //  gets the rackID
    public long getRackID() { return rackID; }

    //  gets the uiComponent
    public UIComponent getUiComponent() { return uiComponent; }


    //  adds the component
    //  checks if category is new
    public void addComponent()
    {
        if (newCategoryObsv.get())
        {
            executor.execute(() ->
            {
                componentRepository.insertComponent(rackID, processNewCategoryInput(), uiComponent.nameObsv.get(), uiComponent.codeObsv.get(),
                                                    uiComponent.getImgPath(), 0);
            });
        }
        else
        {
            componentRepository.getAllComponentCategories().observe(owner, componentCategories ->
            {
                executor.execute(() ->
                {
                componentRepository.insertComponent(rackID, componentCategories.get(categoryListPositionObsv.get()).getID(), uiComponent.nameObsv.get(),
                                                    uiComponent.codeObsv.get(), uiComponent.getImgPath(), 0);
                });
            });
        }
    }

    //  retrieves possible existing long categoryID or creates one
    private long processNewCategoryInput()
    {
        // check if category exists
        long categoryID = componentRepository.getComponentCategory(newComponentCategoryNameObsv.get());
        // if default value is returned...
        if (categoryID == 0L)
        {
            // add the new category
            categoryID = componentRepository.insertComponentCategory(newComponentCategoryNameObsv.get());
        }
        return categoryID;
    }
}