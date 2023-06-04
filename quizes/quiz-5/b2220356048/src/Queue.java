public class Queue<T> implements IDataStructure<T> {
    Node<T> root;

    Queue() {
    }

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

    T pop() {
        Node<T> oldRoot = this.root;
        this.root = this.root.tail;
        return oldRoot.data;
    }

    Node<T> getNode() {
        return root;
    }

    T getValue() {
        if (root == null) {
            return null;
        }
        return root.data;
    }
}
