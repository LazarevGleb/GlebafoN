package model.repositories;

import model.entities.AbstractEntity;

import java.util.List;

public interface CRUDRepository<T extends AbstractEntity> {
    /**
     * Returns class of the entity
     *
     * @return class
     */
    Class<T> getClassEntity();

    /**
     * Returns all items from table
     *
     * @return list of items
     */
    List<T> getAll();

    /**
     * Returns all valid items from table
     *
     * @return list of valid items
     */
    List<T> getAllValid();

    /**
     * Creates new item in table
     *
     * @param obj item's entity
     */
    void create(T obj);

    /**
     * Updates item
     *
     * @param obj item to be updated
     */
    void update(T obj);

    /**
     * Returns item according to property amd value
     *
     * @param property property for search
     * @param value    value for search
     * @return item with the value
     */
    T getByProperty(String property, Object value);
}
