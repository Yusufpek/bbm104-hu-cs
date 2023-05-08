public abstract class LibraryObject {
    private int id;

    public LibraryObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%s [id: %s]", this.getClass().toString().replace("class ", ""), this.getId());
    }
}
