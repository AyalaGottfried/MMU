package main.java.com.hit.dao;

import java.io.IOException;


/**
 * manages the data in the ram
 *
 * @param <T>
 */
public interface IDao<ID extends java.io.Serializable, T> {
    /**
     * Deletes a given entity
     *
     * @param entity given entity
     */
    void delete(T entity) throws IOException;

    /**
     * Retrieves an entity by its id
     *
     * @param id must not be null
     * @return the entity with the given id or null if none found
     */
    T find(ID id) throws IOException;

    /**
     * Saves a given entity
     *
     * @param entity given entity
     */
    void save(T entity) throws IOException;

}
