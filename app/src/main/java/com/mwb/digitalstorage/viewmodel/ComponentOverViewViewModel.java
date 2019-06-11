package com.mwb.digitalstorage.viewmodel;

import android.graphics.Bitmap;
import com.mwb.digitalstorage.adapter.ComponentCategoryListAdapter;
import com.mwb.digitalstorage.adapter.ComponentListAdapter;
import com.mwb.digitalstorage.command_handlers.ComponentCategoryCmdHandler;
import com.mwb.digitalstorage.command_handlers.ComponentCmdHandler;
import com.mwb.digitalstorage.command_handlers.SpinnerSetterCmdHandler;
import com.mwb.digitalstorage.command_handlers.entity.ImgCmdHandler;
import com.mwb.digitalstorage.database.ComponentRepository;
import com.mwb.digitalstorage.database.RackRepository;
import com.mwb.digitalstorage.modelUI.UIComponent;
import com.mwb.digitalstorage.modelUI.UIComponentCategory;
import com.mwb.digitalstorage.modelUI.UIRack;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LifecycleOwner;


public class ComponentOverViewViewModel extends BaseViewModel
{
    private long rackID;

    private ComponentRepository componentRepository;

    private UIComponentCategory uiComponentCategory;
    private UIComponentCategory previousSortComponentCategory;
    private UIComponent uiComponent;
    private UIRack uiRack;

    private LifecycleOwner lifecycleOwner;

    public ObservableField<ComponentListAdapter> componentListAdapterObsv = new ObservableField<>();
    public ObservableField<ComponentCategoryListAdapter> componentCatListAdapterObsv = new ObservableField<>();


    // sets the elements belonging to the viewModel
    public void setViewModelElements(long rackID, LifecycleOwner lifecycleOwner, SpinnerSetterCmdHandler spinnerSetterCmdHandler, ComponentCategoryCmdHandler componentCategoryCmdHandler,
                                     ComponentCmdHandler componentCmdHandler, ImgCmdHandler imgCmdHandler)
    {
        previousSortComponentCategory = new UIComponentCategory(0L, "", 0);
        componentRepository = new ComponentRepository(executor);
        RackRepository rackRepository = new RackRepository();
        this.rackID = rackID;
        this.lifecycleOwner = lifecycleOwner;

        componentRepository.getRackComponentCategories(rackID).observe(lifecycleOwner, componentCategories ->
        {
            componentCatListAdapterObsv.set(new ComponentCategoryListAdapter(componentCategories, componentCategoryCmdHandler));
        });
        componentRepository.getAllComponentCategories().observe(lifecycleOwner, uiComponentCategories ->
        {
            componentRepository.getRackComponents(rackID).observe(lifecycleOwner, uiComponents ->
            {
                componentListAdapterObsv.set(new ComponentListAdapter(uiComponentCategories, uiComponents, componentCmdHandler,
                                                                        spinnerSetterCmdHandler, imgCmdHandler, imgProcessor));
            });
        });
        executor.execute(() ->
        {
            uiRack = rackRepository.getUIRack(rackID);
            rackRepository.setUIRackElements(uiRack, imgProcessor);
        });
    }

    //  gets the uiRack
    public UIRack getUiRack() { return uiRack; }

    //  gets the component
    public UIComponent getUiComponent() { return uiComponent; }

    //  sets the to-be-edited component category
    public void setEditableComponentCategory(UIComponentCategory uiComponentCategory)
    {
        if (this.uiComponentCategory != null)
        {
            this.uiComponentCategory.isEditObsv.set(false);
        }
        uiComponentCategory.isEditObsv.set(true);
        this.uiComponentCategory = uiComponentCategory;
    }

    //  sets the to-be-edited component
    public void setEditableComponent(UIComponent uiComponent)
    {
        if (this.uiComponent != null)
        {
            this.uiComponent.isEditObsv.set(false);
        }
        uiComponent.isEditObsv.set(true);
        this.uiComponent = uiComponent;
    }

    //  performs sorting on the components per selected category
    public void sort(UIComponentCategory toBeSortedUiComponentCategory)
    {
        if (previousSortComponentCategory.getID() != toBeSortedUiComponentCategory.getID())
        {
            componentRepository.getCategoryFilteredComponents(rackID, toBeSortedUiComponentCategory.getID()).observe(lifecycleOwner, components ->
            {
                componentListAdapterObsv.get().setComponents(components);
            });
            previousSortComponentCategory.setSelectedState();
            previousSortComponentCategory = toBeSortedUiComponentCategory;
            previousSortComponentCategory.setSelectedState();
        }
        else
        {
            componentRepository.getRackComponents(rackID).observe(lifecycleOwner, components ->
            {
                componentListAdapterObsv.get().setComponents(components);
            });
            previousSortComponentCategory.setSelectedState();
            previousSortComponentCategory = new UIComponentCategory(0L, "", 0);
        }
    }

    //  saves the category edit
    public void saveComponentCategoryEdit()
    {
        executor.execute(() ->
        {
            componentRepository.editComponentCategory(uiComponentCategory.getID(),
                                                uiComponentCategory.nameObsv.get());
        });
    }

    //  saves the component edit
    public void saveComponentEdit()
    {
        componentRepository.getAllComponentCategories().observe(lifecycleOwner, uiComponentCategories ->
        {
            long categoryID = uiComponentCategories.get(uiComponent.selectedCategoryInListObsv.get()).getID();
            executor.execute(() ->
            {
                componentRepository.editComponent(uiComponent.getId(), categoryID, uiComponent.getName(),
                        uiComponent.getCode(), uiComponent.getImgPath());
            });
        });
    }

    //  removes the component
    public void deleteComponent()
    {
        executor.execute(() ->
        {
            componentRepository.deleteComponent(uiComponent.getId());
        });
    }
}