package view;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import controller.Controller;
import model.*;
import utils.Lire;

public class View {
    
    private Controller controller;


    public View(Controller controller) {
        this.controller = controller;
    }


    public final static void clearConsole()
    {
        try
        {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
            }
        }
        catch (final Exception e) { System.out.println("Error when trying to clear the console."); }
    }

    private void startStep(String name) {
        clearConsole();
        System.out.println("---- GAME MASTER ----");
        System.out.println("");
        System.out.println("It's the "+name+"'s turn, you have to call the "+name+" to wake up.");

        System.out.println("");
        this.timer("Waiting for the "+name+" : ");
    }

    private void timer(String text) {
        for (int j = 5; j > 0; j--) {
            System.out.println(text + j + "s");
            try { TimeUnit.MILLISECONDS.sleep(1000); } 
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

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

    public ArrayList<String> initPlayers() {
        ArrayList<String> res  = new ArrayList<String>();
        clearConsole();
        timer("Start of the game in ");
        int nb_players;
        do{
            clearConsole();
            System.out.println("Enter the number of players (8-18)");
            nb_players = Lire.i();
        }while(nb_players < 8 || nb_players > 18);

        System.out.println("Now make sure you enter a different name for each player !");
        for(int i = 1; i <= nb_players; i++) {
            System.out.println("Enter the name of the player " + i);
            final String name = Lire.S();
            res.add(name);
        }

        return res;
    }

    public void start() {
        clearConsole();
        System.out.println("---- GAME MASTER ----");
        System.out.println("");
        System.out.println("Here is the list of players and their associated roles :");
        
        this.printPlayers();

        System.out.println("");
        System.out.println("Go around the table and whisper to each player their role.");
        System.out.println("If you're done, press enter to continue :");
        Lire.S();
        System.out.println("It's the night, the village goes to sleep, players close their eyes.");
        System.out.println("");
        this.timer("Next step in : ");
    }

    public String thiefStep(String thief_name) {
        this.startStep("thief");

        clearConsole();
        System.out.println("---- THIEF (" + thief_name + ") ----");
        System.out.println("");
        System.out.println("It's your turn, you must choose a player to steal his role.");
        String player_name = "";
        do {
            System.out.println("Enter the player's name : ");
            player_name = Lire.S();
        }while(this.controller.findPlayerByName(player_name) == -1 || player_name.equals(thief_name));
        System.out.println("");

        return player_name;
    }

    public void updateThiefStep(String role_name) {
        System.out.println("Great ! This is now your new role : " + role_name);
        System.out.println("You can go to sleep.");
        System.out.println("");
        this.timer("Waiting for the thief : ");

        clearConsole();
        System.out.println("---- GAME MASTER ----");
        System.out.println("");
        System.out.println("You can inform the player who was robbed that he is now a villager.");
        System.out.println("");
        this.printPlayers();
        System.out.println("");
        System.out.println("If you're done, press enter to continue :");
        Lire.S();
        this.timer("Next step in : ");
    }

    public String[] cupidStep(String cupid_name) {
        String[] res = new String[2];
        this.startStep("cupid");

        clearConsole();
        System.out.println("---- CUPID (" + cupid_name + ") ----");
        System.out.println("");
        System.out.println("It's your turn, you must choose two players to make a couple.");
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

    public void updateCupidStep() {
        System.out.println("Great ! You can go to sleep now.");
        System.out.println("");
        timer("Waiting for Cupid : ");

        clearConsole();
        System.out.println("---- GAME MASTER ----");
        System.out.println("");
        System.out.println("You can inform the players who are now in a couple about their partner and their role.");
        System.out.println("");
        this.printPlayers();
        System.out.println("");
        System.out.println("If you're done, press enter to continue :");
        Lire.S();
        this.timer("Next step in : ");
    }

    public String seerStep(String seer_name) {
        this.startStep("seer");

        clearConsole();
        System.out.println("---- SEER (" + seer_name + ") ----");
        System.out.println("");
        System.out.println("It's your turn, you must choose a player to watch his role.");
        String player_name = "";
        do {
            System.out.println("Enter the player's name : ");
            player_name = Lire.S();
        }while(this.controller.findPlayerByName(player_name) == -1);

        return player_name;
    }

    public void updateSeerStep(String role) {
        System.out.println("Great ! The player you have chosen is a " + role);
        System.out.println("You can go to sleep now.");
        System.out.println("");
        timer("Waiting for the seer : ");

        clearConsole();
        System.out.println("---- GAME MASTER ----");
        System.out.println("");
        System.out.println("Be ready for the next step.");
        System.out.println("");
        this.printPlayers();
        System.out.println("");
        System.out.println("If you're done, press enter to continue :");
        Lire.S();
        this.timer("Next step in : ");
    }

    public String werewolfStep() {
        this.startStep("werewolves");

        clearConsole();
        System.out.println("---- WEREWOLVES ----");
        System.out.println("");
        System.out.println("It's your turn, you must choose a player to eliminate. Agree on a name.");
        String player_name = "";
        do {
            System.out.println("Enter the player's name : ");
            player_name = Lire.S();
        }while(this.controller.findPlayerByName(player_name) == -1);

        return player_name;
    }

    public void updateWerewolfStep() {
        System.out.println("Great ! You can go to sleep now.");
        System.out.println("");
        timer("Waiting for the werewolves : ");

        clearConsole();
        System.out.println("---- GAME MASTER ----");
        System.out.println("");
        System.out.println("Be ready for the next step.");
        System.out.println("");
        this.printPlayers();
        System.out.println("");
        System.out.println("If you're done, press enter to continue :");
        Lire.S();
        this.timer("Next step in : ");
    }

    public String[] witchStep(Witch witch) {
        this.startStep("witch");

        String[] res = {"", ""};

        clearConsole();
        System.out.println("---- WITCH (" + witch.getName() + ") ----");
        System.out.println("");
        System.out.println("It's your turn. " + witch.getRemainingPotions());
        System.out.println("");
        System.out.println("The player "+this.controller.getTargeted_player_werewolf().getName()+" has been targeted by the werewolves.");
        
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

    public void updateWitchStep() {
        System.out.println("Great ! You can go to sleep now.");
        System.out.println("");
        timer("Waiting for the witch : ");

        clearConsole();
        System.out.println("---- GAME MASTER ----");
        System.out.println("");
        System.out.println("Be ready for the next step.");
        System.out.println("");
        this.printPlayers();
        System.out.println("");
        System.out.println("If you're done, press enter to continue :");
        Lire.S();
        this.timer("Next step in : ");
    }

    public String guardStep(Guard guard) {
        this.startStep("guard");

        clearConsole();
        System.out.println("---- GUARD (" + guard.getName() + ") ----");
        System.out.println("");
        System.out.println("It's your turn, you must choose a player to protect him. You can't choose the same player twice in a row.");
        String player_name = "";
        do {
            System.out.println("Enter the player's name : ");
            player_name = Lire.S();
        }while(this.controller.findPlayerByName(player_name) == -1 || (guard.getLast_player_protected() != null && guard.getLast_player_protected().getName().equals(player_name)));

        System.out.println("Great ! You can go to sleep now.");
        System.out.println("");
        timer("Waiting for the guard : ");

        clearConsole();
        System.out.println("---- GAME MASTER ----");
        System.out.println("");
        System.out.println("Be ready for the next step.");
        System.out.println("");
        this.printPlayers();
        System.out.println("");
        System.out.println("If you're done, press enter to continue :");
        Lire.S();
        this.timer("Next step in : ");

        return player_name;
    }

    public void startMorning() {
        clearConsole();
        System.out.println("---- GAME MASTER ----");
        System.out.println("");
        System.out.println("It's the morning, you have to call everyone to wake up.");

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

    public String electNewCaptain(String name) {
        clearConsole();
        System.out.println("---- CAPTAIN (" + name + ") ----");
        System.out.println("");
        System.out.println("You have been killed. Now, you have to choose a successor to be captain.");
        String player_name = "";
        do {
            System.out.println("Enter the player's name : ");
            player_name = Lire.S();
        }while(this.controller.findPlayerByName(player_name) == -1);

        clearConsole();
        System.out.println("---- EVERYBODY ----");
        System.out.println("");
        System.out.println("Great ! "+player_name+" is now the new captain !");
        System.out.println("");
        timer("Be ready for the next step : ");

        return player_name;
    }

    public String hunterStep(String name) {
        clearConsole();
        System.out.println("---- HUNTER (" + name + ") ----");
        System.out.println("");
        System.out.println("You have been killed. Now, you have to choose a target you want to kill.");
        String player_name = "";
        do {
            System.out.println("Enter the player's name : ");
            player_name = Lire.S();
        }while(this.controller.findPlayerByName(player_name) == -1);

        clearConsole();
        System.out.println("---- EVERYBODY ----");
        System.out.println("");
        System.out.println("The hunter has killed " + player_name + ". This player was " + this.controller.getPlayers_list().get(this.controller.findPlayerByName(player_name)).getRole_name() + ".");
        if(this.controller.getPlayers_list().get(this.controller.findPlayerByName(player_name)).getPartner() != null) {
            System.out.println("This player was in couple with "+this.controller.getPlayers_list().get(this.controller.findPlayerByName(player_name)).getPartner().getName()+" who was "+this.controller.getPlayers_list().get(this.controller.findPlayerByName(player_name)).getRole_name()+". As a result, this player also died.");
        }
        System.out.println("");
        this.timer("Next step in : ");

        return player_name;
    }

    public String electFirstCaptain() {
        clearConsole();
        System.out.println("---- EVERYBODY ----");
        System.out.println("");
        System.out.println("You have to designate a player to be the captain. The majority wins.");
        String player_name = "";
        do {
            System.out.println("GAME MASTER, enter the name of the player who has a majority of votes : ");
            player_name = Lire.S();
        }while(this.controller.findPlayerByName(player_name) == -1);

        System.out.println("");
        timer("Be ready for the next step : ");

        return player_name;
    }

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

    public void villageWin() {
        clearConsole();
        System.out.println("---- EVERYBODY ----");
        System.out.println("");
        System.out.println("The game is finished. THE VILLAGE WON !");
        System.out.println("");
        timer("Endgame : ");
    }

    public void werewolvesWin() {
        clearConsole();
        System.out.println("---- EVERYBODY ----");
        System.out.println("");
        System.out.println("The game is finished. THE WEREWOLVES WON !");
        System.out.println("");
        timer("Endgame : ");
    }

    public void startNight() {
        clearConsole();
        System.out.println("---- GAME MASTER ----");
        System.out.println("");
        System.out.println("It's the night, the village goes to sleep, players close their eyes.");
        System.out.println("");
        System.out.println("Here is the list of players in this step of the game :");
        
        this.printPlayers();

        System.out.println("");
        System.out.println("If you're done, press enter to continue :");
        Lire.S();
        this.timer("Next step in : ");
    }

    public void equality() {
        clearConsole();
        System.out.println("---- EVERYBODY ----");
        System.out.println("");
        System.out.println("The game is finished. EQUALITY !");
        System.out.println("");
        timer("Endgame : ");
    }

    public void coupleWin() {
        clearConsole();
        System.out.println("---- EVERYBODY ----");
        System.out.println("");
        System.out.println("The game is finished. THE COUPLE WON ! A werewolf and a villager...");
        System.out.println("");
        timer("Endgame : ");
    }

}