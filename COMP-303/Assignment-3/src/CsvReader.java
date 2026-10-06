import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;



public class CsvReader extends CsvProcess{
    @Override
    void process() {
        try (BufferedReader reader = new BufferedReader(new FileReader(getCsvName() + ".csv"))) {
            String line;
            boolean hasContent = false;

            System.out.println("Reading CSV file: " + getCsvName() + ".csv");

            System.out.println("----- CSV Content -----");
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    hasContent = true;
                    System.out.println(line);
                }
            }
            if (!hasContent) {
                System.out.println("No data found");
            }

            System.out.println("-----------------------" + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file: " + e.getMessage());
        }
    }
}