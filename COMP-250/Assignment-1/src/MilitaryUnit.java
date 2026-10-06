package assignment1;

public abstract class MilitaryUnit extends Unit {

    //Fields

    private double attackDamage;
    private int attackRange;
    private int armor;

    //Methods

    public MilitaryUnit(Tile tile, double healthPoints, int movingRange, String faction, double attackDamage,
                        int attackRange, int armor) {

        super(tile, healthPoints, movingRange, faction);
        this.attackDamage = attackDamage;
        this.attackRange = attackRange;
        this.armor = armor;


    }



    public void takeAction(Tile tile) {

        if (tile != null) {

            double newAttackDamage = attackDamage;

            if (Tile.getDistance(this.getPosition(), tile) <= this.attackRange &&
                    tile.selectWeakEnemy(this.getFaction()) != null) {

                if (this.getPosition().isImproved()) {
                    newAttackDamage = newAttackDamage * 1.05;
                }

                ((tile).selectWeakEnemy(this.getFaction())).receiveDamage(newAttackDamage);
            }
        }
    }



    @Override
    public void receiveDamage (double damage) {

        damage = (double) 100/(100 + armor) * damage;
        super.receiveDamage(damage);

    }
}
