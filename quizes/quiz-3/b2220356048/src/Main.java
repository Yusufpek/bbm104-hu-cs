
class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            OutputWriter.writeToFile(Messages.PARAMETER_ERROR);
        } else {
            InputFileManager io = new InputFileManager(args[0]);
            String result = io.readInputFile();
            OutputWriter.writeToFile(result);
        }
    }
}