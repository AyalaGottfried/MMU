package main.java.com.hit.memory;

import com.google.gson.Gson;
import java1.algorithm.IAlgoCache;
import main.java.com.hit.dm.DataModel;

/**
 * manages the ram
 *
 * @param <T> type of the content in the ram
 */
public class CacheUnit<T> {
    private final IAlgoCache<Long, DataModel<T>> algo;

    public CacheUnit(IAlgoCache<Long, DataModel<T>> algo) {
        this.algo = algo;
    }

    public DataModel<T>[] getDataModels(Long[] ids) {
        DataModel<T>[] arr = new DataModel[ids.length];
        for (int i = 0; i < ids.length; i++) {
            arr[i] = algo.getElement(ids[i]);
        }
        return arr;
    }

    public DataModel<T>[] putDataModels(DataModel<T>[] datamodels) {
        DataModel<T>[] arr = new DataModel[datamodels.length];
        for (int i = 0; i < datamodels.length; i++) {
            arr[i] = null;
            if (datamodels[i] != null) {
                DataModel<T> data = algo.putElement(datamodels[i].getDataModelId(), datamodels[i]);
                if (data != null) arr[i] = data;
            }
        }
        return arr;
    }

    public void removeDataModels(Long[] ids) {
        for (Long id : ids) {
            algo.removeElement(id);
        }
    }

    public DataModel<T>[] getAllDataModels() {
        return algo.getAllElements().values().toArray(new DataModel[0]);
    }
}
