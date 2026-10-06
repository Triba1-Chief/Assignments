package assignment1;

public abstract class Unit {

    // Fields:

    private Tile tile;
    private double healthPoints;
    private int movingRange;
    private String faction; //my factions: Bantus, Nilotes, Cushites



    // Methods:

    public Unit(Tile tile, double healthPoints, int movingRange, String faction) {

        this.healthPoints = healthPoints;
        this.movingRange = movingRange;
        this.faction = faction;

        if (!(tile.addUnit(this))) {
            throw new IllegalArgumentException();
        }

        this.tile = tile;


    }



    public final Tile getPosition() {

        return this.tile;

    }



    public final double getHP() {

        return this.healthPoints;

    }



    public final String getFaction() {

        return this.faction;

    }



    public boolean moveTo(Tile tile) {

        if (tile != null && Tile.getDistance(getPosition(), tile) <= this.movingRange && tile.addUnit(this)) {
            getPosition().removeUnit(this);
            this.tile = tile;
            return true;
        }

        return false;

    }



    public void receiveDamage(double damage) {

        if (damage < 0) {
            return;
        }
        else if (getPosition().isCity()) {
            damage = (damage * 0.9);
        }


        this.healthPoints = healthPoints - damage;



        if (getHP() <= 0) {
            getPosition().removeUnit(this);
            this.tile = null;
        }

    }



    public abstract void takeAction(Tile tile);



    public boolean equals(Object obj) {

        if (!(obj instanceof Unit)) {
            return false;
        }

        Unit unit = (Unit) obj; //Create a new variable that has obj as a Unit.

        boolean result = Math.pow((this.getHP() - unit.getHP()), 2) < 0.001;

        return  (unit.getPosition() == this.getPosition() && result
                && unit.getFaction().equals(this.getFaction()));
    }

}