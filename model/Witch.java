package model;

public class Witch extends Generic_role {
    
    // boolean : true if the witch still has a healing potion, else false
    private boolean healing_potion;

    // boolean : true if the witch still has a poisoning potion, else false
    private boolean poisoning_potion;



    /**
     * Constructor
     */
    public Witch(String name) { 
        super("witch", name);
        this.healing_potion = true;
        this.poisoning_potion = true;
    }



    /**
     * @return true if the witch still has a healing potion, else false
     */
    public boolean getHealing_potion() { return this.healing_potion; }

    /**
     * @return true if the witch still has a poisoning potion, else false
     */
    public boolean getPoisoning_potion() { return this.poisoning_potion; }



    /**
     * @return String which announces the number of potions remaining
     */
    public String getRemainingPotions() {
        String res = "You have";
        
        if(healing_potion) { res += " 1 healing potion and"; }
        else { res += " 0 healing potion and"; }
        
        if(poisoning_potion) { res += " 1 poisoning potion."; }
        else { res += " 0 poisoning potion."; }
    
        return res;
    }

    /**
     * Uses the healing potion :
     * <ul>
     * <li>Revives the player who is in parameter
     * <li>Sets healing_potion to false to notify the healing potion has been used
     * </ul>
     * @param player
     */
    public void useHealingPotion(Generic_role player) {
        if(healing_potion) {
            player.revive();
            this.healing_potion = false;
        }
    }

    /**
     * Uses the poisoning potion :
     * <ul>
     * <li>Kills the player who is in parameter
     * <li>Sets poisoning_potion to false to notify the poisoning potion has been used
     * </ul>
     * @param player
     */
    public void usePoisoningPotion(Generic_role player) {
        if(poisoning_potion) {
            player.dead();
            this.poisoning_potion = false;
        }
    }

}