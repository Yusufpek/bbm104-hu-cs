public abstract class Member extends LibraryObject {
    boolean isMissed = false;
    int bookLimit;
    int timeLimitInWeek;
    MemberType type;

    Member(MemberType type, int bookLimit, int timeLimit, int id) {
        super(id);
        this.type = type;
        this.bookLimit = bookLimit;
        this.timeLimitInWeek = timeLimit;
    }

    void setBookLimit(int limit) {
        this.bookLimit = limit;
    }

    int getBookLimit() {
        return this.bookLimit;
    }

    int getTimeLimitInWeek() {
        return this.timeLimitInWeek;
    }

    int getTimeLimitInDay() {
        return this.timeLimitInWeek * 7;
    }
}

enum MemberType {
    Student,
    Academic
}