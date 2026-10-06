package assignment1;

public class ListOfUnits {

    // Fields

    private Unit[] arrayOfUnits;
    private int size;


    // Private Methods

    private void resizeArray() { //takes the array to resize as input

        if (getSize() == arrayOfUnits.length) {

            Unit[] newArray = new Unit[(getList().length) * 2];

            for (int i = 0; i < getSize(); i++) {

                newArray[i] = getUnit(i);
            }

            arrayOfUnits = newArray;

        }
    }



    // Public Methods

    public ListOfUnits() {

        arrayOfUnits = new Unit[10];
        size = 0;
    }


    public int getSize() {

        return size;

    }


    public Unit[] getList() {

        Unit[] tempArray = new Unit[getSize()];
        int sizeTempArray = 0;

        // Obtaining a list of non-null values from original list
        for (int i = 0; i < getSize(); i++) {
            // This pushes the null values to the end of the list while maintaining length of array.
            if (arrayOfUnits[i] != null) {
                tempArray[sizeTempArray] = arrayOfUnits[i];
                sizeTempArray++;
            }
        }

        size = sizeTempArray;

        for (int i = 0; i < sizeTempArray; i++) {
            arrayOfUnits[i] = tempArray[i];
        }

        return tempArray;
    }


    public Unit getUnit(int unitReference) {

        if (unitReference < 0 || unitReference >= getSize()) {
            throw new IndexOutOfBoundsException();
        }

        return getList()[unitReference];
    }


    public void addUnit(Unit unit) {

        resizeArray();

        if (unit != null) {
            arrayOfUnits[getSize()] = unit;
            size++;
        }

    }



    public int indexOf(Unit unit) {

        //getList();

        for (int i = 0; i < getSize(); i++) {
            if (getUnit(i).equals(unit)) {
                return i;
            }
        }

        return -1;

    }


    public boolean removeUnit(Unit unit) {

        if (indexOf(unit) == -1) {
            return false;
        }
        else {
            for (int i = indexOf(unit); i < getSize(); i++) {
                arrayOfUnits[i] = arrayOfUnits[i+1];
            }

            size--;

            return true;
        }
    }


    public MilitaryUnit[] getArmy() {

        Unit[] tempArray = new Unit[getSize()];
        int sizeTempArray = 0;

        for (int i = 0; i < getSize(); i++) {
            if (getUnit(i) instanceof MilitaryUnit) {
                tempArray[sizeTempArray] = getUnit(i);
                sizeTempArray++;
            }
        }

        MilitaryUnit[] militaryArray = new MilitaryUnit[sizeTempArray];

        // Obtaining a list of non-null values from military array
        for (int i = 0; i < sizeTempArray; i++) {
            militaryArray[i] = (MilitaryUnit) tempArray[i];
        }

        return militaryArray;
    }
}