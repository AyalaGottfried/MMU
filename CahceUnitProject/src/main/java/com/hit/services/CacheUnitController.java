package main.java.com.hit.services;

import main.java.com.hit.dm.DataModel;

public class CacheUnitController<T> {
    CacheUnitService<T> service;

    public CacheUnitController() {
        service = new CacheUnitService<>();
    }

    public synchronized boolean update(DataModel<T>[] dataModels) {
        return service.update(dataModels);
    }

    public synchronized boolean delete(DataModel<T>[] dataModels) {
        return service.delete(dataModels);
    }

    public synchronized DataModel<T>[] get(DataModel<T>[] dataModels) {
        return service.get(dataModels);
    }

    public synchronized void saveCacheToIDao() {
        service.saveCacheToIdao();
    }

    public String getStatistics() {
        return service.getStatistics();
    }
}
