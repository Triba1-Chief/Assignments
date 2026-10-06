import java.util.ArrayList;



public class CsvFieldBuilder {
    private ArrayList<String> fields = new ArrayList<>();

    public CsvFieldBuilder defaultStudentFields() {
        fields.clear();

        fields.add("lastName");
        fields.add("firstName");
        fields.add("degree");
        fields.add("CGPA");
        return this;
    }

    public CsvFieldBuilder defaultScheduleFields() {
        fields.clear();

        fields.add("day");
        fields.add("startTime");
        fields.add("endTime");
        fields.add("class");
        return this;
    }

    public CsvFieldBuilder customFields(String  field1, String field2, String field3) {
        if (field1 == null || field2 == null || field3 == null
                || field1.isBlank() || field2.isBlank() || field3.isBlank()) {
            throw new IllegalArgumentException("Field(s) can't be empty. Received: [" + field1 + "," + field2 + "," +
                    field3 + "]");
        }

        fields.clear();

        fields.add(field1);
        fields.add(field2);
        fields.add(field3);
        return this;
    }

    //Returns the fields as a new arraylist
    public ArrayList<String> build() {
        return new ArrayList<>(fields);
    }
}