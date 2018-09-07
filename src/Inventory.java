import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Inventory {
    public static void main(String[] args) {
        String inputFileName = "./src/in.txt";
        String outputFileName = "out.txt";
        Database db = new Database();

        try {
            String line;
            ArrayList<String> inputCommand;

            // Redirect output to the output text file
            PrintStream fileStream = new PrintStream(outputFileName);
            System.setOut(fileStream);

            FileReader inputFileReader = new FileReader(inputFileName);
            BufferedReader inputBufferedReader = new BufferedReader(inputFileReader);

            while ((line = inputBufferedReader.readLine()) != null) {
                inputCommand = cleanInputCommand(line);
                execute(db, inputCommand);
            }
        }
        catch (FileNotFoundException fx) {
            System.out.println("Input file not found: " + inputFileName);
        }
        catch (IOException ix) {
            System.out.println("Unable to read input file: " + inputFileName);
        }
    }

    /**
     * Clean the input command and return an ArrayList representation of the input.
     * @param inputCommand: String, input command
     * @return ArrayList, cleaned input command
     */
    private static ArrayList<String> cleanInputCommand(String inputCommand) {
        ArrayList<String>cleanedCommand = new ArrayList<>();

        // Find the desired pattern
        Matcher matcher = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(inputCommand);

        while(matcher.find()) {
            cleanedCommand.add(matcher.group(1));
        }

        return cleanedCommand;
    }

    /**
     * Match and execute the input command.
     * @param db: Database, HashMap database
     * @param inputCommand: ArrayList<String> input command
     */
    private static void execute(Database db, ArrayList<String> inputCommand) {
        String function = inputCommand.get(0);

        switch (function) {
            case "ADD":
                Functions.add(db, inputCommand);
                break;
            case "STATUS":
                Functions.status(db, inputCommand);
                break;
            case "BUY":
                Functions.buy(db, inputCommand);
                break;
            case "SELL":
                Functions.sell(db, inputCommand);
                break;
            case "QUAN":
                Functions.quan(db, inputCommand);
                break;
            case "STORE":
                Functions.store(db, inputCommand);
                break;
            case "LOAD":
                Functions.load(db, inputCommand);
                break;
            case "CLEAR":
                Functions.clear(db, inputCommand);
                break;
            case "SEARCH":
                Functions.search(db, inputCommand);
                break;
            default:
                System.out.println("MAIN: UNKNOWN_COMMAND");
        }

    }
}
