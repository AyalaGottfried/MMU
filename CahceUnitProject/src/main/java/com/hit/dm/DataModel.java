package main.java.com.hit.dm;

import java.util.Objects;

public class DataModel<T> implements java.io.Serializable {
    private Long id;
    private T content;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public DataModel(Long id, T content) {
        this.id = id;
        this.content = content;
    }

    public void setDataModelId(Long id) {
        this.id = id;
    }

    public Long getDataModelId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "id: " + this.id + "\ncontent: " + this.content.toString() + "\ncontent type: " + this.content.getClass();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataModel<?> dataModel = (DataModel<?>) o;
        return Objects.equals(content, dataModel.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
