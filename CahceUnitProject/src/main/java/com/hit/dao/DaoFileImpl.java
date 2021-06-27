package main.java.com.hit.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.com.hit.dm.DataModel;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * manages the data in the ram by files
 *
 * @param <T> type of the content in the ram
 */
public class DaoFileImpl<T> implements IDao<Long, DataModel<T>> {
    private final String filePath;
    private int capacity;
    private Gson gson;

    public DaoFileImpl(String filePath,
                       int capacity) {
        this.filePath = filePath;
        this.capacity = capacity;
        gson = new Gson();
    }

    public DaoFileImpl(String filePath) {
        this.filePath = filePath;
    }

    private List<DataModel<T>> getDataFromFile() throws IOException {
        Type userListType = new TypeToken<ArrayList<DataModel<T>>>() {
        }.getType();
        FileReader fileReader;
        List<DataModel<T>> fileData;
        fileReader = new FileReader(filePath);
        fileData = gson.fromJson(fileReader, userListType);
        fileReader.close();
        return fileData;
    }

    private void writeDataToFile(List<DataModel<T>> fileData) throws IOException {
        FileWriter fileWriter;
        fileWriter = new FileWriter(filePath);
        gson.toJson(fileData, fileWriter);
        fileWriter.close();
    }

    @Override
    public void delete(DataModel<T> entity) throws IOException {
        List<DataModel<T>> fileData = getDataFromFile();
        if (fileData != null) {
            fileData.remove(entity);
            writeDataToFile(fileData);
        }
    }

    @Override
    public DataModel<T> find(Long aLong) throws IOException {
        List<DataModel<T>> fileData = getDataFromFile();
        if (fileData == null) return null;
        Optional<DataModel<T>> matchingObject = fileData.stream().filter(data -> data.getDataModelId().equals(aLong)).findFirst();
        return matchingObject.orElse(null);
    }

    @Override
    public void save(DataModel<T> entity) throws IOException {
        List<DataModel<T>> fileData = getDataFromFile();
        if (fileData == null) fileData = new ArrayList<DataModel<T>>();
        Long id = entity.getDataModelId();
        if (id == null) return;
        List<DataModel<T>> result = fileData.stream().filter(item -> item.getDataModelId().equals(id)).collect(Collectors.toList());
        if (result.size() == 0) fileData.add(entity);
        else if (!result.get(0).equals(entity)) {
            delete(result.get(0));
            fileData.add(entity);
        }
        writeDataToFile(fileData);
    }
}
