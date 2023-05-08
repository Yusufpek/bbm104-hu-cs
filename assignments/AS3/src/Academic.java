public class Academic extends Member {

    Academic(int id) {
        super(MemberType.Academic, 4, 2, id);
    }

    @Override
    void setBookLimit(int limit) {
        limit = limit > 4 ? 2 : limit;
        super.setBookLimit(limit);
    }
}
