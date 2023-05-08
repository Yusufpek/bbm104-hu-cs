public class Student extends Member {

    Student(int id) {
        super(MemberType.Student, 2, 1, id);
    }

    @Override
    void setBookLimit(int limit) {
        limit = limit > 2 ? 2 : limit;
        super.setBookLimit(limit);
    }

}
