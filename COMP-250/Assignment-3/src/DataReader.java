
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

//The DataReader class deals with I/O of the dataset and serialized decision tree objects. It also
//splits the read dataset into training and test sets.
public class DataReader {
    ArrayList<Datum> datalist = new ArrayList<Datum>();
    ArrayList<Datum> trainData = new ArrayList<Datum>();
    ArrayList<Datum> testData = new ArrayList<Datum>();

    DataReader() {}

    void read_data(String filename) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        while (line!=null) {
            String[] data = line.split(",");
            int len = data.length-1;
            double [] tempx = new double[len];

            for (int i=0;i<data.length-1 ;i++) {
                tempx[i] = Float.parseFloat(data[i]);
            }
            float f = Float.parseFloat(data[data.length-1]);
            int jj = Math.round(f);
            Datum temp = new Datum(tempx , jj);
            this.datalist.add(temp);
            line = br.readLine();
        }
    }

    void splitTrainTestData(double trainfraction) {
        int no_of_traincases = (int)Math.round(this.datalist.size()*trainfraction);
        int total = this.datalist.size();
        Collections.shuffle(this.datalist , new Random(1));

        for (int i = 0 ;  i< no_of_traincases ; i++) {
            Datum swap = this.datalist.get(i);
            this.trainData.add(swap);
        }

        for (int i = no_of_traincases ; i < total ; i++) {
            this.testData.add(this.datalist.get(i));
        }
    }

    public static void writeSerializedTree( DecisionTree dt, String filename) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(dt);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in "+ filename);
        } catch (IOException ik) {
            ik.printStackTrace();
        }
    }

    public static DecisionTree readSerializedTree(String filename) {
        DecisionTree object1 = null;
        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);
            object1 = (DecisionTree)in.readObject();

            in.close();
            file.close();

            return object1;
        }
        catch(IOException ex) {
            System.out.println("IOException is caught");
            ex.printStackTrace();
        }
        catch(ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }
        return null;
    }
}
