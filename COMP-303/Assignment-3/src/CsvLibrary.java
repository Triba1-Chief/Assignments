public class CsvLibrary {
    private static final CsvLibrary INSTANCE = new CsvLibrary();
    private String currFileName;
    private boolean fileOpen = false; //Status of file (opened or closed?)

    private CsvLibrary() {}

    public void setFileOpen(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("File name cannot be blank " + fileName);
        }

        if (!fileOpen) {
            fileOpen = true;
            currFileName = fileName;
            System.out.println("File " + currFileName + ".csv successfully opened.");
        }
        else {
            throw new IllegalStateException("File is already open: " + getFileName());
        }
    }

    public void setFileClose() {
        if (fileOpen) {
            System.out.println("File " + currFileName + ".csv successfully closed." + "\n");
            fileOpen = false;
            currFileName = null;
        }
        else {
            throw new IllegalStateException("File is already closed: " + getFileName());
        }
    }

    public static CsvLibrary getInstance() {
        return INSTANCE;
    }

    public String getFileName() {
        return currFileName;
    }
}