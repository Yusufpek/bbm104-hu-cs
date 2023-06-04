public interface IDataStructure<T> {
    T remove();

    void add(Node<T> node);

    void add(T value);
}
