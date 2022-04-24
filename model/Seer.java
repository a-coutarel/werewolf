package model;

public class Seer extends Generic_role {
    
    /**
     * Constructor
     */
    public Seer(String name) { super("seer", name);}



    /**
     * Reveals the name of the player's role in parameter
     * @param player
     * @return role name
     */
    public String watchRole(Generic_role player) {
        return player.getRole_name();
    }

}