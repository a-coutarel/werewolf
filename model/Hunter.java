package model;

public class Hunter extends Generic_role {
    
    /**
     * Constructor
     */
    public Hunter(String name) { super("hunter", name);}



    /**
     * kills the player who is in parameter
     * @param player
     */
    public void kill(Generic_role player) {
        player.dead();
    }
    
}