/**
 * The Queue class represents a generic queue data structure.
 *
 * @param <T> the type of elements stored in the queue
 */
public class Queue<T> implements IDataStructure<T> {
    Node<T> root;

    /**
     * Constructs an empty queue.
     */
    Queue() {
    }

    /**
     * Constructs a queue with a single element.
     *
     * @param value the value of the element
     */
    Queue(T value) {
        this.root = new Node<T>(value);
    }

    @Override
    public void add(T value) {
        if (this.root == null) {
            this.root = new Node<T>(value);
            return;
        }
        Node<T> temp = root;
        while (temp.tail != null) {
            temp = temp.tail;
        }
        temp.tail = new Node<T>(value);
    }

    @Override
    public void add(Node<T> node) {
        if (this.root == null) {
            this.root = node;
            return;
        }
        Node<T> temp = root;
        while (temp.tail != null) {
            temp = root.tail;
        }
        temp.tail = node;
    }

    @Override
    public T remove() {
        Node<T> oldRoot = this.root;
        this.root = this.root.tail;
        return oldRoot.data;
    }

    /**
     * Removes and returns the element at the front of the queue.
     *
     * @return the element at the front of the queue
     */
    T pop() {
        Node<T> oldRoot = this.root;
        this.root = this.root.tail;
        return oldRoot.data;
    }

    /**
     * Returns the root node of the queue.
     *
     * @return the root node
     */
    Node<T> getNode() {
        return root;
    }

    /**
     * Returns the value of the root node.
     *
     * @return the value of the root node, or null if the queue is empty
     */
    T getValue() {
        if (root == null) {
            return null;
        }
        return root.data;
    }
}
