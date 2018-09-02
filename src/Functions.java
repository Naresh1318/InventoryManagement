import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

class Functions {
    /**
     *
     * @param db
     * @param inputCommand
     */
    private final static String DATE_FORMAT = "MM/dd/yyyy";

    static void add(Database db, ArrayList<String> inputCommand) {
        if (inputCommand.size() != 4) {
            System.out.println("ADD: ERROR WRONG_ARGUMENT_COUNT");
            return;
        }
        if (!checkDate(inputCommand.get(3))) {
            System.out.println("ADD: INVALID_DATE");
            return;
        }

        // New inventory stock
        ArrayList<String> databaseEntry = new ArrayList<>(inputCommand.subList(1, 4));
        databaseEntry.add("0");

        // Add to database
        if (!checkDuplicates(db, databaseEntry)) {
            db.database.put(Integer.toString(db.database.size()), databaseEntry);
            System.out.println("ADD: OK " + inputCommand.get(1) + " " + inputCommand.get(2));
        } else {
            System.out.println("ADD: ERROR DUPLICATE_ENTRY");
        }
    }

    /**
     *
     * @param db
     */
    static void status(Database db, ArrayList<String> inputCommand) {
        if (inputCommand.size() != 1) {
            System.out.println("STATUS: ERROR WRONG_ARGUMENT_COUNT");
            return;
        }

        // Print status
        System.out.println("STATUS: OK " + db.database.keySet().size());
        for (String key: db.database.keySet()) {
            System.out.println(db.database.get(key).get(0) + "," + db.database.get(key).get(1) + ","
                    + db.database.get(key).get(2) + "," + db.database.get(key).get(3));
        }
    }

    /**
     *
     * @param db
     * @param inputCommand
     */
    static void buy(Database db, ArrayList<String>inputCommand) {
        int itemIndex = 0;
        boolean itemFound = false;

        // Check argument count
        if (inputCommand.size() != 4) {
            System.out.println("BUY: ERROR WRONG_ARGUMENT_COUNT");
            return;
        }

        // Check quantity
        try {
            if (Integer.parseInt(inputCommand.get(3)) <= 0) {
                System.out.println("BUY: INVALID_QUANTITY");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("BUY: INVALID_QUANTITY");
            return;
        }


        for (String key: db.database.keySet()) {
            if (db.database.get(key).subList(0, 2).equals(inputCommand.subList(1, 3))) {
                itemFound = true;
                break;
            } else {
                itemIndex++;
            }
        }
        if (!itemFound) {
            System.out.println("BUY: CANNOT_BUY_BEFORE_ADD " + inputCommand.get(1));
            return;
        }

        // Add item quantity to the database
        ArrayList<String>itemEntry = db.database.get(Integer.toString(itemIndex));
        int newStock = Integer.parseInt(itemEntry.get(3)) + Integer.parseInt(inputCommand.get(3));
        itemEntry.set(3, Integer.toString(newStock));
        db.database.put(Integer.toString(itemIndex), itemEntry);
        System.out.println("BUY: OK " + itemEntry.get(1) + " " + itemEntry.get(2) + " " + itemEntry.get(3));
    }

    /**
     *
     * @param db
     * @param inputCommand
     */
    static void sell(Database db, ArrayList<String>inputCommand) {
        int itemIndex = 0;
        boolean itemFound = false;

        // Check argument count
        if (inputCommand.size() != 4) {
            System.out.println("SELL: ERROR WRONG_ARGUMENT_COUNT");
            return;
        }

        // Check quantity
        try {
            if (Integer.parseInt(inputCommand.get(3)) <= 0) {
                System.out.println("SELL: INVALID_QUANTITY");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("SELL: INVALID_QUANTITY");
            return;
        }


        for (String key: db.database.keySet()) {
            if (db.database.get(key).subList(0, 2).equals(inputCommand.subList(1, 3))) {
                itemFound = true;
                break;
            } else {
                itemIndex++;
            }
        }
        if (!itemFound) {
            System.out.println("SELL: CANNOT_SELL_BEFORE_ADD " + inputCommand.get(1));
            return;
        }

        // Remove item quantity from the database
        ArrayList<String>itemEntry = db.database.get(Integer.toString(itemIndex));
        int newStock = Integer.parseInt(itemEntry.get(3)) - Integer.parseInt(inputCommand.get(3));

        if (newStock < 0) {
            System.out.println("SELL: ERROR CANNOT_SELL_QUANTITY_MORE_THAN_STOCK");
            return;
        }

        itemEntry.set(3, Integer.toString(newStock));
        db.database.put(Integer.toString(itemIndex), itemEntry);
        System.out.println("SELL: OK " + itemEntry.get(1) + " " + itemEntry.get(2) + " " + itemEntry.get(3));
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
                System.out.println("MAIN: UNKNOWN_COMMAND");
        }
    }

    private static void greater(Database db, ArrayList<String>inputCommand) {
        boolean itemFound = false;
        ArrayList<String>itemIndex = new ArrayList<>();

        // Check argument count
        if (inputCommand.size() != 3) {
            System.out.println("QUAN GREATER: ERROR WRONG_ARGUMENT_COUNT");
            return;
        }

        // Check quantity
        try {
            if (Integer.parseInt(inputCommand.get(2)) <= 0) {
                System.out.println("QUAN GREATER: INVALID_QUANTITY");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("QUAN GREATER: INVALID_QUANTITY");
            return;
        }

        for (String key: db.database.keySet()) {
            if (Integer.parseInt(db.database.get(key).get(3)) > Integer.parseInt(inputCommand.get(2))) {
                itemFound = true;
                itemIndex.add(key);
            }
        }
        if (!itemFound) {
            System.out.println("QUAN GREATER: NO MATCH FOUND");
            return;
        }

        System.out.println("QUAN: OK " + itemIndex.size());
        for (String key: itemIndex) {
            System.out.println(db.database.get(key).get(0) + "," + db.database.get(key).get(1) + ","
                    + db.database.get(key).get(2) + "," + db.database.get(key).get(3));
        }
    }

    private static void fewer(Database db, ArrayList<String>inputCommand) {
        boolean itemFound = false;
        ArrayList<String>itemIndex = new ArrayList<>();

        // Check argument count
        if (inputCommand.size() != 3) {
            System.out.println("QUAN FEWER: ERROR WRONG_ARGUMENT_COUNT");
            return;
        }

        // Check quantity
        try {
            if (Integer.parseInt(inputCommand.get(2)) <= 0) {
                System.out.println("QUAN FEWER: INVALID_QUANTITY");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("QUAN FEWER: INVALID_QUANTITY");
            return;
        }

        for (String key: db.database.keySet()) {
            if (Integer.parseInt(db.database.get(key).get(3)) < Integer.parseInt(inputCommand.get(2))) {
                itemFound = true;
                itemIndex.add(key);
            }
        }
        if (!itemFound) {
            System.out.println("QUAN FEWER: NO MATCH FOUND");
            return;
        }

        System.out.println("QUAN: OK " + itemIndex.size());
        for (String key: itemIndex) {
            System.out.println(db.database.get(key).get(0) + "," + db.database.get(key).get(1) + ","
                    + db.database.get(key).get(2) + "," + db.database.get(key).get(3));
        }
    }

    private static void between(Database db, ArrayList<String>inputCommand) {
        boolean itemFound = false;
        ArrayList<String>itemIndex = new ArrayList<>();

        // Check argument count
        if (inputCommand.size() != 4) {
            System.out.println("QUAN BETWEEN: ERROR WRONG_ARGUMENT_COUNT");
            return;
        }

        // Check quantity
        try {
            if (Integer.parseInt(inputCommand.get(2)) <= 0 || Integer.parseInt(inputCommand.get(3)) <= 0 ||
                    Integer.parseInt(inputCommand.get(2)) >= Integer.parseInt(inputCommand.get(3))) {
                System.out.println("QUAN BETWEEN: INVALID_QUANTITY");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("QUAN BETWEEN: INVALID_QUANTITY");
            return;
        }

        for (String key: db.database.keySet()) {
            if (Integer.parseInt(db.database.get(key).get(3)) > Integer.parseInt(inputCommand.get(2)) &&
                    Integer.parseInt(db.database.get(key).get(3)) < Integer.parseInt(inputCommand.get(3))) {
                itemFound = true;
                itemIndex.add(key);
            }
        }
        if (!itemFound) {
            System.out.println("QUAN BETWEEN: NO MATCH FOUND");
            return;
        }

        System.out.println("QUAN: OK " + itemIndex.size());
        for (String key: itemIndex) {
            System.out.println(db.database.get(key).get(0) + "," + db.database.get(key).get(1) + ","
                    + db.database.get(key).get(2) + "," + db.database.get(key).get(3));
        }
    }

    static void store(Database db, ArrayList<String>inputCommand) {
        db.convertToCSV(inputCommand.get(1));
    }

    static void load(Database db, ArrayList<String> inputCommand) {
        db.loadFromCSV(inputCommand.get(1));
    }

    static void clear(Database db, ArrayList<String> inputCommand) {
        db.database.clear();
        System.out.println("CLEAR: OK");
    }

    static void search(Database db, ArrayList<String> inputCommand) {
        boolean itemFound = false;
        ArrayList<String>itemIndex = new ArrayList<>();

        // Check argument count
        if (inputCommand.size() != 2) {
            System.out.println("SEARCH: ERROR WRONG_ARGUMENT_COUNT");
            return;
        }

        for (String key: db.database.keySet()) {
            if ((db.database.get(key).get(0).equals(inputCommand.get(1))) ||
                    (db.database.get(key).get(1).equals(inputCommand.get(1)))) {
                itemFound = true;
                itemIndex.add(key);
            }
        }
        if (!itemFound) {
            System.out.println("SEARCH: NO_MATCH_FOUND");
            return;
        }

        System.out.println("SEARCH: OK " + itemIndex.size());
        for (String key: itemIndex) {
            System.out.println(db.database.get(key).get(0) + "," + db.database.get(key).get(1) + ","
                    + db.database.get(key).get(2) + "," + db.database.get(key).get(3));
        }
    }

    private static boolean checkDuplicates(Database db, ArrayList<String> databaseEntry) {
        for (String key: db.database.keySet()) {
            if (db.database.get(key).subList(0, 2).equals(databaseEntry.subList(0, 2))) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkDate(String date)
    {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
