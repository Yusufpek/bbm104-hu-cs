
import java.util.Comparator;

public class idComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        int id1 = Integer.parseInt(o1.split("\t")[0]);
        int id2 = Integer.parseInt(o2.split("\t")[0]);
        return Integer.compare(id1, id2);
    }

}
