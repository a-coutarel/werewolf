package model;

public abstract class Generic_role {

    // name of the role
    protected final String role_name;

    // name of the player
    protected final String name;

    // boolean : true if the player is dead, else false
    protected boolean isDead;

    // partner of the player choosed by Cupid 
    protected Generic_role partner;

    // boolean : true in the case the player is the captain, else false
    protected boolean captain;

    // number of votes the player received against him in a turn
    protected int votes_against;



    /**
     * Constructor
     * @param role_name
     */
    protected Generic_role(String role_name, String name) { 
        this.role_name = role_name;
        this.name = name;
        this.partner = null;
        this.captain = false;
        this.votes_against = 0;
    }



    /**
     * @return the name of the role
     */
    public String getRole_name() { return this.role_name; }

    /**
     * @return name of the player
     */
    public String getName() { return this.name; }

    /**
     * @return a boolean to know if the player is dead or not
     */
    public boolean isDead() { return this.isDead; }

    /**
     * @return the partner of the player
     */
    public Generic_role getPartner() { return this.partner; }

    /**
     * @return the number of votes against the player
     */
    public int getVotes_against() { return this.votes_against; }

    /**
     * @return true if the player is the captain, else false
     */
    public boolean isCaptain() { return this.captain; }



    /**
     * Sets if the player is dead or not
     * @param isDead 
     */
    public void setIsDead(boolean isDead) { this.isDead = isDead; }

    /**
     * Sets the partner of the player
     * @param partner
     */
    public void setPartner(Generic_role partner) { this.partner = partner; }

    /**
     * Sets if the player is the captain or not
     * @param captain
     */
    public void setCaptain(boolean captain) { this.captain = captain; }

    /**
     * Reset the number of votes against the player to 0
     */
    public void resetVotes_against() { this.votes_against = 0; }



    /**
     * Sets the boolean isDead at true for the player and his/her partner to notify that they are dead
     */
    public void dead() {
        this.isDead = true;
        if(this.partner != null) { this.partner.setIsDead(true); }
    }

    /**
     * Sets the boolean isDead at false for the player and his/her partner to notify that they have been resurrected/protected
     */
    public void revive() {
        this.isDead = false;
        if(this.partner != null) { this.partner.setIsDead(false); }
    }

    /**
     * Add one vote against the player who called this function
     */
    public void takeVote() {
        this.votes_against++;
    }

    // Overriding toString() method
    @Override
    public String toString() {
        String res = "";
        res = "* Player name : " + this.name + " / Role : " + this.role_name;
        if(this.getPartner() != null) res += " / In couple with " + this.getPartner().getName();
        if(this.captain) res += " / This player is the captain !";
        return res;
    }

    /**
     * Sets the player in parameter as the new captain
     * @param player
     */
    public void electNewCaptain(Generic_role player) {
        if(this.captain) {
            this.captain = false;
            player.setCaptain(true); 
        }
    }
    
}