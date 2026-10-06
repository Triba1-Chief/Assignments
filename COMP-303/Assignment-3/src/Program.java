public class Program {
    public static void main(String[] args) {
        try {
            CsvReader reader = new CsvReader();
            CsvWriter writer = new CsvWriter();

            writer.execute("students", "default student fields", null,null,null);
            reader.execute("students", "default student fields", null,null,null);

            writer.execute("schedule", "default schedule fields", null, null, null);
            reader.execute("schedule", "default schedule fields", null, null, null);

            writer.execute("exam", "custom fields", "subject", "room", "time");
            reader.execute("exam", "custom fields", "subject", "room", "time");

            writer.execute("studentZ", "custom fields", null,null,null);
            reader.execute("studentZ", "custom fields", null,null,null);
        }
        catch (Exception e){
            System.err.println("The following error occurred: " + e.getMessage() + ". Please try again.");
        }

    }
}