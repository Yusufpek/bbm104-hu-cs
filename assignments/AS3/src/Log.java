import java.util.ArrayList;

public class Log extends ArrayList<String> {
    @Override
    public String toString() {
        if (this.size() == 0)
            return "";
        String stringValue = super.toString();
        return stringValue
                .substring(1, stringValue.length() - 1) // remove []
                .replaceAll(", ", "\n");
    }
}
