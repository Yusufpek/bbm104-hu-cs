public class CommandHandler implements ICommand {

    @Override
    public String convertBinary(String value) {
        int number = Integer.parseInt(value);
        String minus = "";
        if (number < 0) {
            number *= -1;
            minus = "-";
        }
        return String.format("Equivalent of %s (base 10) in base 2 is: %s%s", value, minus, toBinary(number));
    }

    private String toBinary(int number) {
        Stack<Integer> stack = new Stack<Integer>();
        while (number > 1) {
            stack.add(number % 2);
            number /= 2;
        }
        stack.add(number); // add the remaining value
        return stack.toString();
    }

    @Override
    public String countBinary(String value) {
        int number = Integer.parseInt(value);
        Stack<String> stack = new Stack<String>();
        while (number > 0) {
            stack.add("\t" + toBinary(number));
            number--;
        }
        return String.format("Counting from 1 up to %s in binary:%s", value, stack.toString());
    }

    @Override
    public String checkPalindrome(String value) {
        Stack<Character> stack = new Stack<Character>();
        // delete punctuation characters and white spaces
        String formattedValue = value.replaceAll("[^a-zA-Z]", "").replaceAll(" ", "").toLowerCase();
        for (int i = 0; i < formattedValue.length(); i++) {
            stack.add(formattedValue.charAt(i));
        }

        boolean isPalindrome = true;
        while (stack.root != null) {
            if (stack.remove().equals(stack.pop())) {
                continue;
            } else {
                isPalindrome = false;
                break;
            }
        }
        if (isPalindrome) {
            return String.format("\"%s\" is a palindrome.", value);
        } else {
            return String.format("\"%s\" is not a palindrome.", value);
        }
    }

    @Override
    public String checkExpression(String value) {
        final String IS_NOT_VALID = String.format("\"%s\" is not a valid expression.", value);
        final String IS_VALID = String.format("\"%s\" is a valid expression.", value);
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                stack.add(c);
                continue;
            }

            Character last;
            switch (c) {
                case ')':
                    last = stack.pop();
                    if (last == null || last == '{' || last == '[')
                        return IS_NOT_VALID;
                    break;
                case '}':
                    last = stack.pop();
                    if (last == null || last == '(' || last == '[')
                        return IS_NOT_VALID;
                    break;
                case ']':
                    last = stack.pop();
                    if (last == null || last == '(' || last == '{')
                        return IS_NOT_VALID;
                    break;
            }
        }
        if (stack.root != null) {
            return IS_NOT_VALID;
        } else {
            return IS_VALID;
        }
    }
}