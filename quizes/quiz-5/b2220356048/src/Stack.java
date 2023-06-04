public class Stack<T> implements IDataStructure<T> {
    Node<T> root;

    Stack() {

    }

    Stack(T value) {
        this.root = new Node<T>(value);
    }

    @Override
    public void add(T value) {
        if (this.root == null) {
            this.root = new Node<T>(value);
            return;
        }
        Node<T> newRoot = new Node<T>(value);
        newRoot.tail = root;
        root.root = newRoot;
        this.root = newRoot;
    }

    @Override
    public void add(Node<T> node) {
        if (this.root == null) {
            this.root = node;
            return;
        }
        node.tail = root;
        root.root = node;
        this.root = node;
    }

    @Override
    public T remove() {
        Node<T> temp = root;
        while (temp.tail != null) {
            temp = temp.tail;
        }
        Node<T> newTail = temp.root;
        newTail.tail = null;
        return temp.data;
    }

    T pop() {
        if (this.root == null) {
            return null;
        }
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

    @Override
    public String toString() {
        String stackString = "";
        while (this.getValue() != null) {
            stackString += this.pop();
        }
        return stackString;
    }
}
