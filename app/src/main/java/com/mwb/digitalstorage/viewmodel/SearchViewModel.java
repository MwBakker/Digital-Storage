package com.mwb.digitalstorage.viewmodel;

import com.mwb.digitalstorage.adapter.SearchedEntityListAdapter;
import com.mwb.digitalstorage.command_handlers.SearchedEntityCmdHandler;
import com.mwb.digitalstorage.database.SearchedEntityRepository;
import androidx.databinding.ObservableField;


public class SearchViewModel extends BaseViewModel
{
    private SearchedEntityRepository searchedEntityRepository;

    public ObservableField<SearchedEntityListAdapter> foundEntitiesAdapterObsv = new ObservableField<>();


    public SearchViewModel()
    {
        searchedEntityRepository = new SearchedEntityRepository();
    }


    //  sets the viewModel elements
    public void setViewModelElements(SearchedEntityCmdHandler cmdHandlerCallBack)
    {
        foundEntitiesAdapterObsv.set(new SearchedEntityListAdapter(cmdHandlerCallBack));
    }

    //  performs searching through the database
    public void searchRelevance(String s)
    {
        executor.execute(() ->
        {
            foundEntitiesAdapterObsv.get().setListSource(searchedEntityRepository.findEntities(s));
            foundEntitiesAdapterObsv.notifyChange();
        });
    }
}
