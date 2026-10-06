package assignment1;

public class Worker extends Unit {

    //Field

    private int numberOfJobs;

    //Methods

    public Worker(Tile tile, double healthPoints, String faction) {

        super (tile, healthPoints, 2, faction);
        numberOfJobs = 0;

    }



    public void takeAction(Tile tile) {

        if (tile != null) {
            if (this.getPosition() == tile && !(tile.isImproved())) {
                tile.buildImprovement();
                numberOfJobs++;
            }

            if (numberOfJobs > 9) {
                this.getPosition().removeUnit(this);
                tile.removeUnit(this);
            }
        }
    }



    @Override
    public boolean equals(Object obj) {

        return obj instanceof Worker && super.equals(obj) && ((Worker) obj).numberOfJobs == this.numberOfJobs;


    }

}