import java.util.ArrayList;

class Functions {
    /**
     *
     * @param db
     * @param inputCommand
     */
    static void add(Database db, ArrayList<String> inputCommand) {
        if (inputCommand.size() < 4) {
            System.out.println("ADD: ERROR WRONG_ARGUMENT_COUNT");
            return;
        }
        // TODO: Check data format and duplicates
        ArrayList<String> databaseEntry = new ArrayList<>(inputCommand.subList(1, 4));
        databaseEntry.add("0");  // New inventory stock

        if (!checkDuplicates(db, databaseEntry)) {
            db.database.put(Integer.toString(db.database.size()), databaseEntry);
            status(db);
        }
    }

    /**
     *
     * @param db
     */
    static void status(Database db) {
        System.out.println("Inside Status");

        for (String key: db.database.keySet()) {
            System.out.println(db.database.get(key));
        }
    }

    /**
     *
     * @param db
     * @param inputCommand
     */
    static void buy(Database db, ArrayList<String>inputCommand) {

    }

    /**
     *
     * @param db
     * @param inputCommand
     */
    static void sell(Database db, ArrayList<String>inputCommand) {

    }

    static void quan(Database db, ArrayList<String>inputCommand) {
        String operation = inputCommand.get(1);
        switch (operation) {
            case "GREATER":
                greater(db, inputCommand);
                break;
            case "FEWER":
                fewer(db, inputCommand);
                break;
            case "BETWEEN":
                between(db, inputCommand);
                break;
            default:
                System.out.println(inputCommand + "not found.");
        }
    }

    private static void greater(Database db, ArrayList<String>inputCommand) {

    }

    private static void fewer(Database db, ArrayList<String>inputCommand) {

    }

    private static void between(Database db, ArrayList<String>inputCommand) {

    }

    static void store(Database db, ArrayList<String>inputCommand) {

    }

    static void load(Database db, ArrayList<String> inputCommand) {
    }

    static void search(Database db, ArrayList<String> inputCommand) {
    }

    private static boolean checkDuplicates(Database db, ArrayList<String> databaseEntry) {
        for (String key: db.database.keySet()) {
            if (db.database.get(key).subList(0, 3).equals(databaseEntry.subList(0, 3))) {
                return true;
            }
        }
        return false;
    }
}
