import java.io.FileWriter;
import java.io.IOException;



public class CsvWriter extends CsvProcess {
    @Override
    void process() {
        try (FileWriter writer = new FileWriter(getCsvName() + ".csv")) {
            writer.write(String.join(",", getCsvFieldNames())); // Write header row
            writer.write("\n");
            System.out.println("CSV file " + getCsvName() + ".csv successfully written.");
        }
        catch (IOException e) {
            throw new RuntimeException("Error writing to CSV file: " + e.getMessage());
        }
    }
}