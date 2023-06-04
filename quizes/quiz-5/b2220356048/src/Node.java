/**
 * The Node class represents a node in a doubly linked list.
 *
 * @param <T> the type of data stored in the node
 */
public class Node<T> {
    Node<T> root;
    Node<T> tail;
    T data;

    /**
     * Constructs a node with the given data.
     *
     * @param data the data to be stored in the node
     */
    Node(T data) {
        this.data = data;
        this.root = null;
        this.tail = null;
    }

    /**
     * Constructs a node with the given root and data.
     *
     * @param root the root node of the new node
     * @param data the data to be stored in the node
     */
    Node(Node<T> root, T data) {
        this.root = root;
        this.data = data;
        this.tail = null;
    }
}
