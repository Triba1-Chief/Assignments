import java.util.*;



public abstract class CsvProcess {
    private String csvName; //File name
    private ArrayList<String> csvFieldNames; // Stores the fields
    private String fieldSelection; //Type of fields to build using builder
    private String field1, field2, field3; //Custom fields by user
    private final CsvLibrary csvLibrary = CsvLibrary.getInstance();

    protected void setCsvName(String aCsvName) {
        if  (aCsvName == null || aCsvName.isBlank()) {
            throw new IllegalArgumentException("CSV Name cannot be null or blank");
        }
        this.csvName = aCsvName;
    }

    protected void defineCsvFields() {
        CsvFieldBuilder builder = new CsvFieldBuilder();

        if (fieldSelection == null || fieldSelection.isBlank()) {
            throw new IllegalArgumentException("Field selection is null or blank: " + fieldSelection);
        }
        if (fieldSelection.equalsIgnoreCase("default student fields")) {
            this.csvFieldNames = builder.defaultStudentFields().build();
        }
        else if (fieldSelection.equalsIgnoreCase("default schedule fields")) {
            this.csvFieldNames = builder.defaultScheduleFields().build();
        }
        else if  (fieldSelection.equalsIgnoreCase("custom fields")) {
            this.csvFieldNames = builder.customFields(field1, field2, field3).build();
        }
        else {
            throw new IllegalArgumentException("Incorrect field selection: " + fieldSelection);
        }
    }

    protected void openCsv() {
        csvLibrary.setFileOpen(getCsvName()); //Enforces one file processing
    }

    abstract void process();

    protected void closeCsv() {
        csvLibrary.setFileClose(); //Enforces one file processing
    }

    //Executes the CSV processing in order
    public void execute(String aCsvName, String afieldSelection, String custom1, String custom2, String custom3) {
        this.fieldSelection = afieldSelection;
        this.field1 = custom1;
        this.field2 = custom2;
        this.field3 = custom3;

        setCsvName(aCsvName);
        defineCsvFields();
        openCsv();
        try {
            process();
        }
        finally {//Closes the file even if an error occurs to prevent file corruption
            closeCsv();
        }
    }

    public String getCsvName() {
        return csvName;
    }
    public ArrayList<String> getCsvFieldNames() {
        return csvFieldNames;
    }
}