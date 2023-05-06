import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

@SuppressWarnings("unchecked")
public class InputParser {

    private static <T extends Collection<String>> T getType(CollectionType type) {
        switch (type) {
            case ARRAY:
                return (T) new ArrayList<String>();
            case HSET:
                return (T) new HashSet<String>();
            case TREE:
                return (T) new TreeSet<String>();
            case TREE_ORDERED:
                return (T) new TreeSet<String>(new idComparator());
            default:
                return null;
        }
    }

    public static <T extends Collection<String>> T parseInput(CollectionType type, List<String> inputs) {
        T verses = (T) getType(type);
        for (String input : inputs) {
            ((Collection<String>) verses).add(input);
        }
        return verses;
    }

    public static HashMap<Integer, String> parseInputMap(List<String> inputs) {
        HashMap<Integer, String> verses = new HashMap<Integer, String>();
        for (String input : inputs) {
            String[] parsedInput = input.split("\t"); // parse id<TAB>content
            verses.put(Integer.parseInt(parsedInput[0]), parsedInput[1]);
        }
        return verses;
    }

    public static enum CollectionType {
        ARRAY,
        HSET,
        TREE,
        TREE_ORDERED,
        HMAP
    }
}
