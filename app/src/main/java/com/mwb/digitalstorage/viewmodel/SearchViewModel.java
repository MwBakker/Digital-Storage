package com.mwb.digitalstorage.viewmodel;

import com.mwb.digitalstorage.adapter.SearchedEntityListAdapter;
import com.mwb.digitalstorage.command_handlers.SearchedEntityCmdHandler;
import com.mwb.digitalstorage.database.SearchedEntityRepository;
import androidx.databinding.ObservableField;


public class SearchViewModel extends BaseViewModel
{
    private long previousActivityID;
    private SearchedEntityRepository searchedEntityRepository;

    public ObservableField<String> previousActivityNameObsv = new ObservableField<>("");
    public ObservableField<SearchedEntityListAdapter> foundEntitiesAdapterObsv = new ObservableField<>();


    public SearchViewModel(SearchedEntityRepository searchedEntityRepository)
    {
        this.searchedEntityRepository = searchedEntityRepository;
    }

    //
    //  sets the viewModel elements
    //
    public void setViewModelElements(String previousActivity, long previousActivityID, SearchedEntityCmdHandler cmdHandlerCallBack)
    {
        this.previousActivityNameObsv.set("< " + previousActivity);
        this.previousActivityID = previousActivityID;
        foundEntitiesAdapterObsv.set(new SearchedEntityListAdapter(cmdHandlerCallBack));
    }

    //
    //  performs searching through the database
    //
    public void searchRelevance(String s)
    {
        executor.execute(() ->
        {
            foundEntitiesAdapterObsv.get().setListSource(searchedEntityRepository.findEntities(s));
        });
    }
}
