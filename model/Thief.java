package model;

public class Thief extends Generic_role {
    
    /**
     * Constructor
     */
    public Thief(String name) { super("thief", name);}



    /**
     * @param player
     * @return name of the player's role in parameter
     */
    public String stealRole(Generic_role player) {
        return player.getRole_name();
    }

}