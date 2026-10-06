package assignment1;


public class Tile {

    // Fields

    private int xCoord;
    private int yCoord;
    private boolean cityExistence;
    private boolean tileImprovement;
    private ListOfUnits unitsOnTile;



    // Methods

    public Tile (int xCoord, int yCoord) {

        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.cityExistence = false;
        this.tileImprovement = false;
        this.unitsOnTile = new ListOfUnits();


    }



    public int getX() {

        return xCoord;

    }



    public int getY() {

        return yCoord;

    }



    public boolean isCity() {

        return cityExistence;

    }



    public boolean isImproved() {

        return tileImprovement;

    }



    public void buildCity() {

        cityExistence = true;

    }



    public void buildImprovement () {

        tileImprovement = true;

    }



    public boolean addUnit(Unit unit) {

        if (unit instanceof MilitaryUnit) {
            for (Unit armyunit : unitsOnTile.getArmy()) {
                if (!(unit.getFaction().equals(armyunit.getFaction()))) {
                    return false;
                }
            }
        }

        unitsOnTile.addUnit(unit);

        return true;

    }



    public boolean removeUnit(Unit unit) {

        return unitsOnTile.removeUnit(unit);

    }



    public Unit selectWeakEnemy(String faction) {

        Unit lowestHp = null;

        for (Unit enemy : unitsOnTile.getList()) {
            if (!(enemy.getFaction().equals(faction))) {
                if (lowestHp == null || enemy.getHP() < lowestHp.getHP()) {
                    lowestHp = enemy;
                }
            }
        }

        return lowestHp;

    }



    public static double getDistance(Tile tile1, Tile tile2) {

        return Math.sqrt(Math.pow(((double)tile1.getX() - (double)tile2.getX()), 2) + Math.pow(((double)tile1.getY() -
                (double)tile2.getY()), 2));

    }
}