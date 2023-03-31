public class Main {
    public static void main(String[] args) throws Exception {

        final Strings messageStrings = new Strings();
        System.out.print(messageStrings.helloWorld);
        System.out.print(messageStrings.nameString());
        System.out.print(messageStrings.firstCodeText);
        System.out.print(messageStrings.getLecture());
        System.out.println(messageStrings.getTerm() + ".");
    }
}

class Strings {
    public String helloWorld = "Hello, World! ";
    public String firstCodeText = "This is my first Java Code that I submit during";

    private String name;
    private String lecture;
    private String term;
    private int year;

    Strings() {
        this.name = "Yusuf ipek";
        this.lecture = "BBM 104";
        this.term = "Spring";
        this.year = 2023;
    }

    Strings(String name, String lecture, String term, int year) {
        this.name = name;
        this.lecture = lecture;
        this.term = term;
        this.year = year;
    }

    String nameString() {
        return "My name is " + name + ". ";
    }

    String getLecture() {
        return " " + lecture + " ";
    }

    String getTerm() {
        return term + " " + year + " Term";
    }
}