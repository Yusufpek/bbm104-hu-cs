/**
 * The IDataStructure interface defines the operations for a generic data
 * structure.
 *
 * @param <T> the type of elements stored in the data structure
 */
public interface IDataStructure<T> {

    /**
     * Removes and returns an element from the data structure.
     *
     * @return the removed element
     */
    T remove();

    /**
     * Adds a node to the data structure.
     *
     * @param node the node to be added
     */
    void add(Node<T> node);

    /**
     * Adds a value to the data structure.
     *
     * @param value the value to be added
     */
    void add(T value);
}
