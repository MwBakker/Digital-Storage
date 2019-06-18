package com.mwb.digitalstorage.database;

public class RepositoryFactory
{
    public final CompanyRepository companyRepository;
    public final StorageRepository storageRepository;
    public final RackRepository rackRepository;
    public final ComponentRepository componentRepository;
    public final SearchedEntityRepository searchedEntityRepository;


    public RepositoryFactory()
    {
        companyRepository = new CompanyRepository();
        storageRepository = new StorageRepository();
        rackRepository = new RackRepository();
        componentRepository = new ComponentRepository();
        searchedEntityRepository = new SearchedEntityRepository();
    }
}

