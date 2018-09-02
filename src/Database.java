import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    public HashMap<String, ArrayList<String>>database = new HashMap<>();

    public void convertToCSV(String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (String key: database.keySet()) {
                bufferedWriter.write(database.get(key).get(0) + "," + database.get(key).get(1) + ","
                        + database.get(key).get(2) + "," + database.get(key).get(3));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException ix) {
            System.out.println("STORE: ERROR IOException");
        }
        System.out.println("STORE: OK " + database.size());
    }

    public void loadFromCSV(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            database.clear();
            int i = 0;
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] lineRead = line.split(",");
                ArrayList<String> databaseEntry = new ArrayList<>();
                for (int j=0; j<4; j++) {
                    databaseEntry.add(lineRead[j]);
                }
                database.put(Integer.toString(i), databaseEntry);
                line = bufferedReader.readLine();
                i++;
            }
            bufferedReader.close();
        } catch (IOException ix) {
            System.out.println("LOAD: FILE_NOT_FOUND");
        }
        System.out.println("LOAD: OK " + database.size());
    }
}
