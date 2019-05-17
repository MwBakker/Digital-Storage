package com.mwb.digitalstorage.viewmodel;

import com.mwb.digitalstorage.adapter.SearchedEntityListAdapter;
import com.mwb.digitalstorage.command_handlers.SearchedEntityCmdHandler;
import com.mwb.digitalstorage.database.SearchedEntityRepository;
import androidx.databinding.ObservableField;


public class SearchViewModel extends BaseViewModel
{
    private long previousActivityID;
    private Class previousActivityClass;
    private String previousActivityName;
    private SearchedEntityRepository searchedEntityRepository;

    public ObservableField<SearchedEntityListAdapter> foundEntitiesAdapterObsv = new ObservableField<>();


    public SearchViewModel()
    {
        searchedEntityRepository = new SearchedEntityRepository();
    }

    //
    //  gets the ID of the previous activity
    //
    public long getPreviousActivityID() { return previousActivityID; }

    //
    //  gets the name of the previous activity
    //
    public String getPreviousActivityName() { return previousActivityName; }

    //
    //  gets the class of the previous activity
    //
    public Class getPreviousActivityClass() { return previousActivityClass; }

    //
    //  sets the viewModel elements
    //
    public void setViewModelElements(long previousActivityID, Class previousActivityClass, String previousActivityName,  SearchedEntityCmdHandler cmdHandlerCallBack)
    {
        this.previousActivityID = previousActivityID;
        this.previousActivityClass = previousActivityClass;
        this.previousActivityName = "< " + previousActivityName;
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
