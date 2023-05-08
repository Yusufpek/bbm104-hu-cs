public abstract class Member extends LibraryObject {
    int bookLimit;
    int timeLimitInWeek;
    MemberType type;

    Member(MemberType type, int bookLimit, int timeLimit, int id) {
        super(id);
        this.type = type;
        this.bookLimit = bookLimit;
        this.timeLimitInWeek = timeLimit;
    }

    @Override
    public String toString() {
        return String.format("%s [id=%s]", type.toString(), getId());
    }
}

enum MemberType {
    Student,
    Academic
}