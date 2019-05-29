package com.mwb.digitalstorage.viewmodel;

import android.graphics.Bitmap;
import com.mwb.digitalstorage.adapter.ComponentCategoryListAdapter;
import com.mwb.digitalstorage.adapter.ComponentListAdapter;
import com.mwb.digitalstorage.command_handlers.ComponentCategoryCmdHandler;
import com.mwb.digitalstorage.command_handlers.ComponentCmdHandler;
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
    private long storageID;
    private long selectedCategoryID;

    private ComponentRepository componentRepository;
    private UIComponentCategory uiComponentCategory;
    private UIComponent uiComponent;
    private LifecycleOwner lifecycleOwner;

    public ObservableField<String> rackNameObsv = new ObservableField<>("");
    public ObservableField<Bitmap> rackImgObsv = new ObservableField<>();
    public ObservableField<ComponentListAdapter> componentListAdapterObsv = new ObservableField<>();
    public ObservableField<ComponentCategoryListAdapter> componentCatListAdapterObsv = new ObservableField<>();


    // sets the elements belonging to the viewModel
    public void setViewModelElements(long storageID, long rackID, LifecycleOwner lifecycleOwner, ComponentCategoryCmdHandler componentCategoryCmdHandler,
                                     ComponentCmdHandler componentCmdHandler, ImgCmdHandler imgCmdHandler)
    {
        componentRepository = new ComponentRepository(executor);
        RackRepository rackRepository = new RackRepository();
        this.rackID = rackID;
        this.storageID = storageID;
        this.lifecycleOwner = lifecycleOwner;

        componentRepository.getRackComponentCategories(rackID).observe(lifecycleOwner, componentCategories ->
        {
            componentCatListAdapterObsv.set(new ComponentCategoryListAdapter(componentCategories, componentCategoryCmdHandler));
        });
        componentRepository.getRackComponents(rackID).observe(lifecycleOwner, components ->
        {
            componentListAdapterObsv.set(new ComponentListAdapter(components, componentCmdHandler, imgCmdHandler, imgProcessor));
        });

        executor.execute(() ->
        {
            UIRack UIRack = rackRepository.getRack(rackID);
            rackNameObsv.set(UIRack.nameObsv.get());
            rackImgObsv.set(UIRack.getImg());
        });
    }

    // gets the storage ID
    public long getStorageID() { return storageID; }

    // gets the rack ID
    public long getRackID() { return rackID; }

    // gets the component
    public UIComponent getUiComponent() { return uiComponent; }

    //
    //  sets the to-be-edited component category
    //
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
    public void sort(UIComponentCategory uiComponentCategory)
    {
        if (uiComponentCategory.getID() != selectedCategoryID)
        {
            componentRepository.getCategoryFilteredComponents(rackID, uiComponentCategory.getID()).observe(lifecycleOwner, components ->
            {
                componentListAdapterObsv.get().setComponents(components);
            });
        }
        else
        {
            componentRepository.getRackComponents(rackID).observe(lifecycleOwner, components ->
            {
                componentListAdapterObsv.get().setComponents(components);
            });
        }
        
        uiComponentCategory.setSelectedState();
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
        executor.execute(() ->
        {
            componentRepository.editComponent(uiComponent.getId(), uiComponent.getComponentCategoryID(), uiComponent.nameObsv.get(),
                                        uiComponent.codeObsv.get(), uiComponent.getImgPath());
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