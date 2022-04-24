package model;

public class Guard extends Generic_role {
    
    // instance of the last player that the guard has protected
    private Generic_role last_player_protected;



    /**
     * Constructor
     */
    public Guard(String name) { 
        super("guard", name);
        last_player_protected = null;
    }



    /**
     * @return instance of the last player protected by the guard
     */
    public Generic_role getLast_player_protected() {
        return this.last_player_protected;
    }



    /**
     * Protects the player who is in parameter
     * @param player
     */
    public void protect(Generic_role player) {
        player.revive();
        last_player_protected = player;
    }
    
}