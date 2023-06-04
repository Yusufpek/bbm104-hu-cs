/**
 * The ICommand interface defines operations for performing various commands.
 */
public interface ICommand {
    /**
     * Converts the given value base 10 to binary (base 2) representation.
     *
     * @param value the value to be converted
     * @return the binary representation of the value
     */
    String convertBinary(String value);

    /**
     * Counts the number of binary digits in the given value.
     *
     * @param value the value to count the binary digits
     * @return the count of binary digits
     */
    String countBinary(String value);

    /**
     * Checks if the given value is a palindrome.
     * Do not consider white spaces and punctuation characters.
     * 
     * @param value the value to be checked
     * @return "true" if the value is a palindrome, "false" otherwise
     */
    String checkPalindrome(String value);

    /**
     * Checks the validity of the given expression.
     * For (),{},[]
     *
     * @param value the mathematical expression to be checked
     * @return "valid" if the expression is valid, an error message otherwise
     */
    String checkExpression(String value);
}
