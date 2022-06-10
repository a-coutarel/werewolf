package view;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import controller.Controller;
import model.*;
import utils.Lire;

public class View {
    
    // controller of the game
    private Controller controller;


    /**
     * Constructor
     * @param controller of the game
     */
    public View(Controller controller) {
        this.controller = controller;
    }


    /**
     * Clears the console
     */
    private final static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) { new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); }
            else { System.out.print("\033\143"); }
        }
        catch (final Exception e) { System.out.println("Error when trying to clear the console."); }
    }


    /**
     * Makes a delay of 1s and print the text placed in parameter
     * @param text
     */
    private void timer(String text) {
        for (int j = 5; j > 0; j--) {
            System.out.println(text + j + "s");
            try { TimeUnit.MILLISECONDS.sleep(1000); } 
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }


    /**
     * Prints all players with their role and prints the players targeted by the werewolves and by the witch
     * <p>
     * This fuction is just useful for the game master to follow the progress of the game
     */
    private void printPlayers() {
        for(Generic_role player : this.controller.getPlayers_list()) {
            System.out.println(player.toString());
        }
        
        if(this.controller.getTargeted_player_werewolf() != null) { 
            System.out.println("** Player targeted by the werewolves : " + this.controller.getTargeted_player_werewolf().getName());
        }

        if(this.controller.getTargeted_player_witch() != null) {
            System.out.println("** Player targeted by the witch : " + this.controller.getTargeted_player_witch().getName());
        }
    }


    /**
     * Asks if you want to load player's names saved
     * or Asks the number of players and their names
     * @return ArrayList<String> of players'names
     */
    public ArrayList<String> initPlayers() {
        ArrayList<String> res  = new ArrayList<String>();
        String response;
        clearConsole();
        timer("Start of the game in ");
        do {
            System.out.println("\nDo you want to load the list of players saved ? yes/no");
            response = Lire.S();
        } while(!response.equals("yes") && !response.equals("no"));
        if(response.equals("yes")) {
            res = this.controller.loadPlayers();
            if(res.size()<8) {
                timer("The file doesn't contain enough players. Datas will not be loaded.");
                res.clear();
            }
        }
        if(res.size()==0 || response.equals("no")) {
            int nb_players;
            do{
                clearConsole();
                System.out.println("\nEnter the number of players (8-18)");
                nb_players = Lire.i();
            }while(nb_players < 8 || nb_players > 18);

            System.out.println("Now make sure you enter a different name for each player !");
            for(int i = 1; i <= nb_players; i++) {
                System.out.println("Enter the name of the player " + i);
                final String name = Lire.S();
                res.add(name);
            }
        }
        return res; 
    }


    /**
     * Indicates to the game master which role he has to call to wake up
     * @param role
     */
    private void startStep(String role) {
        clearConsole();
        System.out.println("---- GAME MASTER ----");
        System.out.println("\nIt's the "+role+"'s turn, you have to call the "+role+" to wake up.");
        System.out.println("");
        this.timer("Waiting for the "+role+" : ");
    }


    /**
     * Indicates to the game master informations of the end of a step
     * @param role
     * @param text
     */
    private void endStep(String role, String text) {
        System.out.println("Great ! You can go to sleep now.");
        System.out.println("");
        timer("Waiting for the "+role+" : ");
        clearConsole();
        System.out.println("---- GAME MASTER ----");
        System.out.println("");
        System.out.println(text);
        System.out.println("");
        this.printPlayers();
        System.out.println("\nIf you're done, press enter to continue :");
        Lire.S();
        this.timer("Next step in : ");
    }


    /**
     * View of the starting stage
     */
    public void start() {
        clearConsole();
        System.out.println("---- GAME MASTER ----");
        System.out.println("\nHere is the list of players and their associated roles :");
        this.printPlayers();
        System.out.println("\nGo around the table and whisper to each player their role.");
        System.out.println("If you're done, press enter to continue :");
        Lire.S();
        System.out.println("It's the night, the village goes to sleep, players close their eyes.");
        System.out.println("");
        this.timer("Next step in : ");
    }


    /**
     * Indicates to the game master that it's the beginning of the night
     */
    public void startNight() {
        clearConsole();
        System.out.println("---- GAME MASTER ----");
        System.out.println("\nIt's the night, the village goes to sleep, players close their eyes.");
        System.out.println("\nHere is the list of players in this step of the game :");
        this.printPlayers();
        System.out.println("\nIf you're done, press enter to continue :");
        Lire.S();
        this.timer("Next step in : ");
    }


    /**
     * View of the thief's step
     * @param thief_name
     * @return name of the player choosen by the thief
     */
    public String thiefStep(String thief_name) {
        this.startStep("thief");

        clearConsole();
        System.out.println("---- THIEF (" + thief_name + ") ----");
        System.out.println("\nIt's your turn, you must choose a player to steal his role.");
        String player_name = "";
        do {
            System.out.println("Enter the player's name : ");
            player_name = Lire.S();
        }while(this.controller.findPlayerByName(player_name) == -1 || player_name.equals(thief_name));

        return player_name;
    }

    /**
     * Updates the view of the thief's step
     * @param role_name
     */
    public void updateThiefStep(String role_name) {
        System.out.println("\nThis is now your new role : " + role_name);
        this.endStep("thief", "You can inform the player who was robbed that he is now a villager.");
    }


    /**
     * View of Cupid's step
     * @param cupid_name
     * @return name of the players choosen by Cupid
     */
    public String[] cupidStep(String cupid_name) {
        String[] res = new String[2];
        this.startStep("cupid");

        clearConsole();
        System.out.println("---- CUPID (" + cupid_name + ") ----");
        System.out.println("\nIt's your turn, you must choose two players to make a couple.");
        String player1_name = "";
        String player2_name = "";
        do {
            System.out.println("Enter the first player's name : ");
            player1_name = Lire.S();
        }while(this.controller.findPlayerByName(player1_name) == -1);
        System.out.println("");
        do {
            System.out.println("Enter the second player's name : ");
            player2_name = Lire.S();
        }while(this.controller.findPlayerByName(player2_name) == -1);
        
        res[0] = player1_name;
        res[1] = player2_name;
        return res;
    }

    /**
     * Updates the view of Cupid's step
     */
    public void updateCupidStep() {
        this.endStep("Cupid", "You can inform the players who are now in a couple about their partner and their role.");
    }


    /**
     * View of the seer's step
     * @param seer_name
     * @return name of the player choosen by the seer
     */
    public String seerStep(String seer_name) {
        this.startStep("seer");

        clearConsole();
        System.out.println("---- SEER (" + seer_name + ") ----");
        System.out.println("\nIt's your turn, you must choose a player to watch his role.");
        String player_name = "";
        do {
            System.out.println("Enter the player's name : ");
            player_name = Lire.S();
        }while(this.controller.findPlayerByName(player_name) == -1);

        return player_name;
    }

    /**
     * Updates the view of the seer's step
     * @param role
     */
    public void updateSeerStep(String role) {
        System.out.println("The player you have chosen is a " + role);
        this.endStep("seer", "Be ready for the next step.");
    }


    /**
     * View of the werewolves' step
     * @return name of the player choosen by the werewolves
     */
    public String werewolfStep() {
        this.startStep("werewolves");

        clearConsole();
        System.out.println("---- WEREWOLVES ----");
        System.out.println("\nIt's your turn, you must choose a player to eliminate. Agree on a name.");
        String player_name = "";
        do {
            System.out.println("Enter the player's name : ");
            player_name = Lire.S();
        }while(this.controller.findPlayerByName(player_name) == -1);

        return player_name;
    }

    /**
     * Updates the view of the werewolves' step
     */
    public void updateWerewolfStep() {
        this.endStep("werewolves", "Be ready for the next step.");
    }


    /**
     * View of the witch's step
     * @param witch
     * @return array with :
     * <ul>
     * <li> at index 0 : "yes" if the witch wants to revive the player killed by the werewolves, else "no"
     * <li> at index 1 : "no" if the witch doesn't want to use her poisoning potion, else the name of the player targeted by the witch
     * </ul>
     */
    public String[] witchStep(Witch witch) {
        this.startStep("witch");

        String[] res = {"", ""};

        clearConsole();
        System.out.println("---- WITCH (" + witch.getName() + ") ----");
        System.out.println("\nIt's your turn. " + witch.getRemainingPotions());
        System.out.println("\nThe player "+this.controller.getTargeted_player_werewolf().getName()+" has been targeted by the werewolves.");
        
        if(witch.getHealing_potion()) {
            String response = "";
            do {
                System.out.println("\nDo you want to use your healing potion to revive him ? yes/no");
                response = Lire.S();
            }while(!response.equals("yes") && !response.equals("no"));
            if(response.equals("yes")) { res[0] = "yes"; }
            else { res[0] = "no"; }
        } else { res[0] = "no"; }

        if(witch.getPoisoning_potion()) {
            String response2 = "";
            do {
                System.out.println("\nDo you want to use your poisoning potion ? yes/no");
                response2 = Lire.S();
            }while(!response2.equals("yes") && !response2.equals("no"));
            if(response2.equals("yes")) { 
                String player_name = "";
                do {
                    System.out.println("Enter the name of the player you want to eliminate : ");
                    player_name = Lire.S();
                }while(this.controller.findPlayerByName(player_name) == -1);
                res[1] = player_name;
            }
            else { res[1] = "no"; }
        } else { res[1] = "no"; }
    
        return res;
    }

    /**
     * Update the view of the witch's step
     */
    public void updateWitchStep() {
        this.endStep("witch", "Be ready for the next step.");
    }


    /**
     * View of the guard's step
     * @param guard
     * @return name of the player choosen by the guard
     */
    public String guardStep(Guard guard) {
        this.startStep("guard");

        clearConsole();
        System.out.println("---- GUARD (" + guard.getName() + ") ----");
        System.out.println("\nIt's your turn, you must choose a player to protect him. You can't choose the same player twice in a row.");
        String player_name = "";
        do {
            System.out.println("Enter the player's name : ");
            player_name = Lire.S();
        }while(this.controller.findPlayerByName(player_name) == -1 || (guard.getLast_player_protected() != null && guard.getLast_player_protected().getName().equals(player_name)));

        this.endStep("guard", "Be ready for the next step.");
        return player_name;
    }


    /**
     * View of the hunter's step
     * @param hunter_name
     * @return name of the player choosen by the hunter
     */
    public String hunterStep(String hunter_name) {
        clearConsole();
        System.out.println("---- HUNTER (" + hunter_name + ") ----");
        System.out.println("\nYou have been killed. Now, you have to choose a target you want to kill.");
        String player_name = "";
        do {
            System.out.println("Enter the player's name : ");
            player_name = Lire.S();
        }while(this.controller.findPlayerByName(player_name) == -1);

        clearConsole();
        System.out.println("---- EVERYBODY ----");
        System.out.println("\nThe hunter has killed " + player_name + ". This player was " + this.controller.getPlayers_list().get(this.controller.findPlayerByName(player_name)).getRole_name() + ".");
        if(this.controller.getPlayers_list().get(this.controller.findPlayerByName(player_name)).getPartner() != null) {
            System.out.println("This player was in couple with "+this.controller.getPlayers_list().get(this.controller.findPlayerByName(player_name)).getPartner().getName()+" who was "+this.controller.getPlayers_list().get(this.controller.findPlayerByName(player_name)).getRole_name()+". As a result, this player also died.");
        }
        System.out.println("");
        this.timer("Next step in : ");

        return player_name;
    }


    /**
     * Asks to players to elect a captain
     * @return name of the player choosen to be the captain
     */
    public String electFirstCaptain() {
        clearConsole();
        System.out.println("---- EVERYBODY ----");
        System.out.println("\nYou have to designate a player to be the captain. The majority wins.");
        String player_name = "";
        do {
            System.out.println("GAME MASTER, enter the name of the player who has a majority of votes : ");
            player_name = Lire.S();
        }while(this.controller.findPlayerByName(player_name) == -1);

        System.out.println("");
        timer("Be ready for the next step : ");

        return player_name;
    }

    /**
     * Asks to the former captain to elect a new captain
     * @param captain_name
     * @return name of the player choosen by the former captain
     */
    public String electNewCaptain(String captain_name) {
        clearConsole();
        System.out.println("---- CAPTAIN (" + captain_name + ") ----");
        System.out.println("\nYou have been killed. Now, you have to choose a successor to be captain.");
        String player_name = "";
        do {
            System.out.println("Enter the player's name : ");
            player_name = Lire.S();
        }while(this.controller.findPlayerByName(player_name) == -1);

        clearConsole();
        System.out.println("---- EVERYBODY ----");
        System.out.println("\nGreat ! "+player_name+" is now the new captain !");
        System.out.println("");
        timer("Be ready for the next step : ");

        return player_name;
    }


    /**
     * Prints informations of the morning stage : prints players dead during the night
     */
    public void startMorning() {
        clearConsole();
        System.out.println("---- GAME MASTER ----");
        System.out.println("\nIt's the morning, you have to call everyone to wake up.");

        System.out.println("");
        this.timer("Waiting for the village : ");

        clearConsole();
        System.out.println("---- EVERYBODY ----");
        if(this.controller.getTargeted_player_werewolf().isDead()) {
            System.out.println("\nThe werewolves have killed " + this.controller.getTargeted_player_werewolf().getName() + " tonight. This player was " + this.controller.getTargeted_player_werewolf().getRole_name() + ".");
            if(this.controller.getTargeted_player_werewolf().getPartner() != null) {
                System.out.println("This player was in couple with "+this.controller.getTargeted_player_werewolf().getPartner().getName()+" who was "+this.controller.getTargeted_player_werewolf().getPartner().getRole_name()+". As a result, this player also died.");
            }
        }
        if(this.controller.getTargeted_player_witch() != null && this.controller.getTargeted_player_witch().isDead()) {
            System.out.println("\nThe witch has killed " + this.controller.getTargeted_player_witch().getName() + " tonight. This player was " + this.controller.getTargeted_player_witch().getRole_name() + ".");
            if(this.controller.getTargeted_player_witch().getPartner() != null) {
                System.out.println("This player was in couple with "+this.controller.getTargeted_player_witch().getPartner().getName()+" who was "+this.controller.getTargeted_player_witch().getPartner().getRole_name()+". As a result, this player also died.");
            }
        }
        if(!this.controller.getTargeted_player_werewolf().isDead() && this.controller.getTargeted_player_witch() == null || !this.controller.getTargeted_player_werewolf().isDead() && !this.controller.getTargeted_player_witch().isDead()) {
            System.out.println("No one has been killed tonight. It's a great day for the village !");
        }
        System.out.println("");
        timer("Be ready for the next step : ");
    }


    /**
     * Asks to each player to vote
     * @return array with names of players who have received votes against them
     */
    public String[] vote() {
        String[] players_names = new String[this.controller.getPlayers_list().size()];

        clearConsole();
        System.out.println("---- EVERYBODY ----");
        System.out.println("");
        System.out.println("It's time to vote. You have to designate a player to eliminate.");
        System.out.println("");
        timer("Start of votes : ");

        for(int i=0; i<this.controller.getPlayers_list().size(); i++) {
            clearConsole();
            System.out.println("---- "+this.controller.getPlayers_list().get(i).getName()+" ----");
            String player_name = "";
            do {
                System.out.println("");
                System.out.println("Enter the player's name : ");
                player_name = Lire.S();
            }while(this.controller.findPlayerByName(player_name) == -1);
            players_names[i] = player_name;
        }

        return players_names;
    }

    /**
     * Update the view of the stage of vote 
     * @param player eliminated
     */
    public void updateVote(Generic_role player) {

        clearConsole();
        System.out.println("---- EVERYBODY ----");
        System.out.println("");
        System.out.println("The village has voted to eliminate " + player.getName() + ". This player was " + player.getRole_name() + ".");
        if(player.getPartner() != null) {
            System.out.println("This player was in couple with "+player.getPartner().getName()+" who was "+player.getPartner().getRole_name()+". As a result, this player also died.");
        }
        System.out.println("");
        timer("Be ready for the next step : ");

    }


    /**
     * Asks to the captain to choose a player to eliminate
     * @param name of the captain
     * @param max_votes : Arraylist of players who are tied in votes
     * @return name of the player choosen by the captain
     */
    public String captainVote(String name, ArrayList<Generic_role> max_votes) {
        clearConsole();
        System.out.println("---- CAPTAIN (" + name + ") ----");
        System.out.println("");
        System.out.println("There is a tie vote. You must then choose the player to eliminate among the following players : ");
        for(Generic_role player : max_votes) {
            System.out.println("- " + player.getName());
        }
        String player_name = "";
        boolean find = false;
        do {
            System.out.println("\nEnter the name of the player : ");
            player_name = Lire.S();
            for(Generic_role player : max_votes) {
                if(player.getName().equals(player_name)) { find = true; break; }
            }
        } while(!find);

        return player_name;
    }


    /**
     * generic method for the view of the end game
     * @param text
     */
    private void win(String text) {
        clearConsole();
        System.out.println("---- EVERYBODY ----");
        System.out.println("");
        System.out.println(text);
        System.out.println("");
        System.out.println("Do you want to save the list of players for another game ? Write 'yes' or 'no' :");
        String response = Lire.S();
        if(response.equals("yes")) {
            this.controller.savePlayers();
        }
        timer("Endgame : ");
    }

    /**
     * View for the village's win
     */
    public void villageWin() {
        this.win("The game is finished. THE VILLAGE WON !");
    }

    /**
     * View for the werewolves' win
     */
    public void werewolvesWin() {
        this.win("The game is finished. THE WEREWOLVES WON !");
    }

    /**
     * View for the equality
     */
    public void equality() {
        this.win("The game is finished. EQUALITY !");
    }

    /**
     * View for the couple's win
     */
    public void coupleWin() {
       this.win("The game is finished. THE COUPLE WON ! A werewolf and a villager...");
    }

}