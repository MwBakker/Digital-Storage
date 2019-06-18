package com.mwb.digitalstorage.viewmodel;

import com.mwb.digitalstorage.adapter.SearchedEntityListAdapter;
import com.mwb.digitalstorage.command_handlers.SearchedEntityCmdHandler;
import androidx.databinding.ObservableField;


public class SearchViewModel extends BaseViewModel
{
    public ObservableField<SearchedEntityListAdapter> foundEntitiesAdapterObsv = new ObservableField<>();

    //  sets the viewModel elements
    public void setViewModelElements(SearchedEntityCmdHandler cmdHandlerCallBack)
    {
        foundEntitiesAdapterObsv.set(new SearchedEntityListAdapter(cmdHandlerCallBack));
    }

    //  performs searching through the database
    public void searchRelevance(String s)
    {
        foundEntitiesAdapterObsv.get().setListSource(repositoryFactory.searchedEntityRepository.findEntities(s));
        foundEntitiesAdapterObsv.notifyChange();
    }
}
