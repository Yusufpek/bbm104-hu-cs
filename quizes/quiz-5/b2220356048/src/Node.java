public class Node<T> {
    Node<T> root;
    Node<T> tail;
    T data;

    Node(T data) {
        this.data = data;
        this.root = null;
        this.tail = null;
    }

    Node(Node<T> root, T data) {
        this.root = root;
        this.data = data;
        this.tail = null;
    }
}
