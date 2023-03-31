import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Calculation {
    private List<String> inputs;
    private IO io;

    Calculation(List<String> inputs, IO io) {
        this.inputs = inputs;
        this.io = io;
    }

    // main method of the class
    public void calculate() {
        int number = 0;
        String command;
        // look for each command
        for (int i = 0; i < inputs.size(); i++) {
            command = inputs.get(i).split(" ")[0]; // get command
            // if command equals to 'exit' finish the loop
            if (command.equals("Exit")) {
                io.outputStrings.add("Finished...");
                break;
            }
            // sort command - look for sort type and get the numbers until -1
            else if (command.equals("Ascending") || command.equals("Descending")) {
                io.outputStrings.add(inputs.get(i));
                String sortType = inputs.get(i).split(" ")[0];
                List<Integer> numbers = new ArrayList<>();
                while (true) {
                    number = Integer.parseInt(inputs.get(++i));
                    if (number == -1) {
                        break;
                    }
                    numbers.add(number);
                    String result = orderSort(sortType, numbers);
                    io.outputStrings.add(result);
                }
                io.outputStrings.add(""); // new line
            }
            // bounded calculations (armstrong numbers, emirp numbers, abundant numbers)
            else {
                number = Integer.parseInt(inputs.get(++i));
                writeCommand(inputs.get(i - 1), number);
                io.outputStrings.add(boundedCommands(command, number));
                io.outputStrings.add("");
            }
        }
    }

    // add the commands to output list
    private void writeCommand(String command, int number) {
        String formattedCommand = command.substring(0, command.length() - 1);
        io.outputStrings.add(formattedCommand + " " + number + ":");
    }

    private String listToString(List<Integer> numbers) {
        if (numbers.size() == 0)
            return "There is no number for this condition";
        return numbers.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(",", "");
    }

    // bounded calculations checker (armstrong, emirp, abundant)
    interface Checker {
        boolean check(int number);
    }

    // all bounded commands calculations are same the difference is number type
    private String boundedCommands(String command, int bound) {
        Checker checker = Calculation::isArmstrongNumber;
        int startNumber = 0;
        if (command.equals("Armstrong")) {
            checker = Calculation::isArmstrongNumber;
            startNumber = 1;
        } else if (command.equals("Emirp")) {
            checker = Calculation::isEmirpNumbers;
            startNumber = 13;
        } else if (command.equals("Abundant")) {
            checker = Calculation::isAbundantNumber;
            startNumber = 10;
        }
        List<Integer> numbers = new ArrayList<>();
        for (int i = startNumber; i <= bound; i++) {
            if (checker.check(i)) {
                numbers.add(i);
            }
        }
        return listToString(numbers);
    }

    // armstrong number check
    static private boolean isArmstrongNumber(int number) {
        char[] text = Integer.toString(number).toCharArray();
        int sum = 0;
        for (char digit : text) {
            sum += Math.pow(Integer.parseInt(String.valueOf(digit)), text.length);
        }
        if (sum == number) {
            return true;
        }
        return false;
    }

    // emirp number check
    static private boolean isEmirpNumbers(int number) {
        if (!isPrime(number))
            return false;
        // reverse the number 13 -> 31
        String reversedNumberStr = "";
        char[] text = Integer.toString(number).toCharArray();
        for (int i = text.length - 1; i >= 0; i--) {
            reversedNumberStr += text[i];
        }
        int reversedNumber = Integer.parseInt(reversedNumberStr);
        if (reversedNumber == number)
            return false;
        if (isPrime(reversedNumber))
            return true;
        return false;
    }

    // check prime
    static private boolean isPrime(int number) {
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    // check abundant
    static private boolean isAbundantNumber(int number) {
        int sum = 0;
        for (int i = 1; i < number; i++) {
            if (number % i == 0) {
                sum += i;
            }
        }
        if (sum > number) {
            return true;
        }
        return false;
    }

    // sorting by sort type (ascending - descending) for given number list
    private String orderSort(String sortType, List<Integer> numbers) {
        Collections.sort(numbers);
        if (sortType.equals("Descending")) {
            Collections.reverse(numbers);
        }
        return listToString(numbers);
    }
}