package com.mwb.digitalstorage.viewmodel;

import com.mwb.digitalstorage.command_handlers.entity.RetrieveEntityCmdHandler;
import com.mwb.digitalstorage.modelUI.UIComponent;
import com.mwb.digitalstorage.modelUI.UIComponentCategory;
import com.mwb.digitalstorage.modelUI.UIRack;
import java.util.List;
import androidx.lifecycle.LiveData;


public class ComponentOverViewViewModel extends BaseViewModel
{
    private UIComponentCategory uiComponentCategory;
    private UIComponentCategory previousSortComponentCategory;
    private UIComponent editableUIComponent;
    private UIRack uiRack;


    // sets the elements belonging to the viewModel
    public void setViewModelElements(long rackID, RetrieveEntityCmdHandler retrieveEntityCmdHandler)
    {
        previousSortComponentCategory = new UIComponentCategory(0L, "", 0);
        repositoryFactory.rackRepository.getUIRack(rackID, imgProcessor, retrieveEntityCmdHandler);
    }

    public UIComponentCategory getPreviousSortComponentCategory () { return previousSortComponentCategory; }

    public void setPreviousSortComponentCategory (UIComponentCategory uiComponentCategory) { previousSortComponentCategory = uiComponentCategory; }

    //  gets the uiComponents
    public LiveData<List<UIComponent>> getUIComponents(long rackID)
    {
        return  repositoryFactory.componentRepository.getRackComponents(rackID);
    }

    //  gets the uiComponent categories
    public LiveData<List<UIComponentCategory>> getUIComponentCategories(long rackID)
    {
        return repositoryFactory.componentRepository.getRackComponentCategories(rackID);
    }

    //  sets the properties to the uiComponentCategories
    public void setUiComponentProperties(List<UIComponent> uiComponents)
    {
        repositoryFactory.componentRepository.setUIComponentProperties(uiComponents, imgProcessor);
    }

    //  gets the uiRack
    public UIRack getUiRack() { return uiRack; }

    //  sets the uiRack
    public void setUIRack(UIRack uiRack) { this.uiRack = uiRack; }

    //  gets the component
    public UIComponent getEditableUiComponent() { return editableUIComponent; }

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
        if (this.editableUIComponent != null)
        {
            this.editableUIComponent.isEditObsv.set(false);
        }
        uiComponent.isEditObsv.set(true);
        this.editableUIComponent = uiComponent;
    }

    //  saves the category edit
    public void saveComponentCategoryEdit()
    {
        repositoryFactory.componentRepository.editComponentCategory(uiComponentCategory.getID(),
                                                            uiComponentCategory.nameObsv.get());
    }

    //  removes the component
    public void deleteComponent()
    {
        repositoryFactory.componentRepository.deleteComponent(editableUIComponent.getId());
    }
}