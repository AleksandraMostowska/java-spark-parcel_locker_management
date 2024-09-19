package mostowska.aleksandra.repository.generic;

import java.util.List;
import java.util.Optional;

/**
 * Generic repository interface providing CRUD operations for entities.
 *
 * @param <T> The type of the entity.
 * @param <ID> The type of the entity's identifier.
 */
public interface CrudRepository<T, ID> {

    /**
     * Saves a single entity.
     *
     * @param item The entity to be saved.
     * @return The saved entity.
     */
    T save(T item);

    /**
     * Updates an existing entity by its ID.
     *
     * @param id The ID of the entity to be updated.
     * @param item The updated entity.
     * @return The updated entity.
     */
    T update(ID id, T item);

    /**
     * Saves multiple entities.
     *
     * @param items The list of entities to be saved.
     * @return The list of saved entities.
     */
    List<T> saveAll(List<T> items);

    /**
     * Finds an entity by its ID.
     *
     * @param id The ID of the entity to be found.
     * @return An {@link Optional} containing the found entity, or empty if not found.
     */
    Optional<T> findById(ID id);

    /**
     * Finds the last 'n' entities.
     *
     * @param n The number of entities to retrieve.
     * @return A list of the last 'n' entities.
     */
    List<T> findLast(int n);

    /**
     * Finds all entities.
     *
     * @return A list of all entities.
     */
    List<T> findAll();

    /**
     * Finds all entities with the given IDs.
     *
     * @param ids A list of IDs of the entities to be found.
     * @return A list of entities corresponding to the provided IDs.
     */
    List<T> findAllById(List<ID> ids);

    /**
     * Deletes an entity by its ID.
     *
     * @param id The ID of the entity to be deleted.
     * @return The deleted entity.
     */
    T delete(ID id);

    /**
     * Deletes multiple entities by their IDs.
     *
     * @param ids A list of IDs of the entities to be deleted.
     * @return A list of the deleted entities.
     */
    List<T> deleteAllById(List<ID> ids);

    /**
     * Deletes all entities.
     *
     * @return A list of all deleted entities.
     */
    List<T> deleteAll();
}
