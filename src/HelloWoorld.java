import java.io.*;

public class HelloWoorld {
    public static void main(String[] args) {
        String fileName = "out.txt";
        String line;
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write does not automatically append a new line
            bufferedWriter.write("Hello World!!");
            bufferedWriter.newLine();

            bufferedWriter.close();
            System.out.format("File saved under: %s", fileName);
        }
        catch (IOException ex) {
            System.out.println("Error Writing to file: " + fileName);
        }

        // Read written file
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            System.out.println("\n Reading file: " + fileName);
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

        }
        catch (FileNotFoundException fx) {
            System.out.println("Unable to locate: " + fileName);
        }
        catch (IOException ix) {
            System.out.println("Unable to read: " + fileName);
        }

    }
}
