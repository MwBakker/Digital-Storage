package com.mwb.digitalstorage;

import android.content.Intent;
import android.os.Bundle;
import com.mwb.digitalstorage.command_handlers.SearchCmdHandler;
import com.mwb.digitalstorage.command_handlers.SearchedEntityCmdHandler;
import com.mwb.digitalstorage.database.SearchedEntityRepository;
import com.mwb.digitalstorage.databinding.ActivitySearchBinding;
import com.mwb.digitalstorage.modelUI.UIEntity;
import com.mwb.digitalstorage.viewmodel.SearchViewModel;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


public class SearchActivity extends AppCompatActivity
{
    private SearchViewModel searchVM;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        searchVM = new SearchViewModel();
        searchVM.setViewModelElements(getIntent().getStringExtra("previous_activity_name"),
                                      getIntent().getLongExtra("previous_activity_id", 0L),
                                      searchedEntityCmdHandler());
        ActivitySearchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.setVm(searchVM);
        binding.setCmdHandler(searchCmdHandler());
    }

    private SearchedEntityCmdHandler searchedEntityCmdHandler()
    {
        return new SearchedEntityCmdHandler()
        {
            @Override
            public void goToSearchedEntity(UIEntity uiEntity)
            {
                Intent intent = new Intent(SearchActivity.this, uiEntity.getClass());
                intent.putExtra("entity_id", uiEntity.getId());
                intent.putExtra("is_searched", true);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        };
    }

    //
    //  sets the command handler for the search
    //
    private SearchCmdHandler searchCmdHandler()
    {
        return new SearchCmdHandler()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                searchVM.searchRelevance(s.toString());
            }
        };
    }

}
