package model;

public class Cupid extends Generic_role {

    /**
     * Constructor
     */
    public Cupid(String name) { super("cupid", name); }


    
    /**
     * Puts players p1 and p2 in couple
     * @param p1 : player 1
     * @param p2 : player 2
     */
    public void makeCouple(Generic_role p1, Generic_role p2) {
        p1.setPartner(p2);
        p2.setPartner(p1);
    }

}