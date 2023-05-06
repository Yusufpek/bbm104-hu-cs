import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        List<String> verses = IO.readInputFile(args[0]);

        List<String> poemArrayList = InputParser.parseInput(InputParser.CollectionType.ARRAY, verses);
        IO.writeToFile("ArrayList", poemArrayList);

        Collections.sort(poemArrayList, new idComparator());
        IO.writeToFile("ArrayListOrderByID", poemArrayList);

        HashMap<Integer, String> poemHashMap = InputParser.parseInputMap(verses);
        IO.writeToFileMap("HashMap", poemHashMap);

        HashSet<String> poemHashSet = InputParser.parseInput(InputParser.CollectionType.HSET, verses);
        IO.writeToFile("HashSet", poemHashSet);

        TreeSet<String> poemTreeSet = InputParser.parseInput(InputParser.CollectionType.TREE, verses);
        IO.writeToFile("TreeSet", poemTreeSet);

        TreeSet<String> poemTreeSetOrdered = InputParser.parseInput(InputParser.CollectionType.TREE_ORDERED, verses);
        IO.writeToFile("TreeSetOrderByID", poemTreeSetOrdered);
    }
}