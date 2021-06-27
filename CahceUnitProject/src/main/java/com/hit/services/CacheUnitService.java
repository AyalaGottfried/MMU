package main.java.com.hit.services;

import com.google.gson.Gson;
import java1.algorithm.LRUAlgoCacheImpl;
import main.java.com.hit.dao.DaoFileImpl;
import main.java.com.hit.dm.DataModel;
import main.java.com.hit.memory.CacheUnit;

import java.io.IOException;
import java.util.Arrays;


public class CacheUnitService<T> {
    private static final int CAPACITY = 10;
    private final CacheUnit<T> cache;
    private final DaoFileImpl<T> dao;
    private int requestsNumber, dataModelsNumber, dataModelsSwaps;

    public CacheUnitService() {
        cache = new CacheUnit<>(new LRUAlgoCacheImpl<>(CAPACITY));
        dao = new DaoFileImpl<>("src/main/resources/file.json", CAPACITY);
        requestsNumber = 0;
        dataModelsNumber = 0;
        dataModelsSwaps = 0;
    }

    public boolean update(DataModel<T>[] dataModels) {
        requestsNumber++;
        dataModelsNumber += dataModels.length;
        DataModel<T>[] deleted = cache.putDataModels(dataModels);
        for (DataModel<T> data : deleted) {
            if (data != null) {
                try {
                    dao.save(data);
                } catch (IOException e) {
                    System.out.println("Exception :" + e);
                    return false;
                }
                dataModelsSwaps++;
            }
        }
        return true;
    }

    public boolean delete(DataModel<T>[] dataModels) {
        requestsNumber++;
        dataModelsNumber += dataModels.length;
        Long[] ids = new Long[dataModels.length];
        for (int i = 0; i < dataModels.length; i++) {
            ids[i] = dataModels[i].getDataModelId();
            try {
                dao.delete(dataModels[i]);
            } catch (IOException e) {
                System.out.println("Exception :" + e);
                return false;
            }
        }
        cache.removeDataModels(ids);
        return true;
    }

    public DataModel<T>[] get(DataModel<T>[] dataModels) {
        try {
            requestsNumber++;
            System.out.println(new Gson().toJson(dataModels));
            dataModelsNumber += dataModels.length;
            Long[] ids = new Long[dataModels.length];
            for (int i = 0; i < dataModels.length; i++) ids[i] = dataModels[i].getDataModelId();
            DataModel<T>[] arr = cache.getDataModels(ids);
            for (int i = 0; i < dataModels.length; i++) {
                if (arr[i] == null) {
                    arr[i] = dao.find(ids[i]);
                    if (arr[i] == null) {
                        arr[i] = dataModels[i];
                    }
                }
            }
            DataModel<T>[] deleted = cache.putDataModels(arr);
            for (DataModel<T> data : deleted) {
                if (data != null) {
                    dao.save(data);
                    dataModelsSwaps++;
                }
            }
            System.out.println(new Gson().toJson((arr)));
            return arr;
        } catch (IOException e) {
            System.out.println("Exception :" + e);
            return null;
        }
    }

    public void saveCacheToIdao() {
        try {
            requestsNumber++;
            DataModel<T>[] allDataModels = cache.getAllDataModels();
            for (DataModel<T> dataModel : allDataModels) {
                dao.save(dataModel);
            }
        } catch (IOException e) {
            System.out.println("Exception :" + e);
        }
    }

    public String getStatistics() {
        requestsNumber++;
        return "Capacity: " + 10 + ";Algorithm: LRU;Total number of requests: " + requestsNumber + ";Total number of DataModels (GET/DELETE/UPDATE) requests: " + dataModelsNumber + ";Total number of DataModel swaps (from Cache to Disk): " + dataModelsSwaps;
    }
}
