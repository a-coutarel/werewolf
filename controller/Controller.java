package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import model.*;
import view.View;

public class Controller {
    
    // view of the game
    private View view;

    // list of players
    private ArrayList<Generic_role> players_list;

    // list of players dead
    private ArrayList<Generic_role> players_dead_list;

    // boolean : true if the game is over, else false
    private boolean game_over;

    // boolean : true if the village won, false if werewolves won (if a couple werewolf+villager won, true)
    private boolean village_win;

    // boolean : true if there is equality, else false (if a couple werewolf+villager won, true)
    private boolean equality;

    // instance of the player targeted by the werewolves
    private Generic_role targeted_player_werewolf;

    // instance of the player targeted by the witch
    private Generic_role targeted_player_witch;



    /**
     * Constructor
     */
    public Controller() {
        this.view = new View(this);
        this.players_list = new ArrayList<Generic_role>();
        this.players_dead_list = new ArrayList<Generic_role>();
        this.game_over = false;
        this.village_win = false;
        this.equality = false;
        this.targeted_player_werewolf = null;
        this.targeted_player_witch = null;
    }



    /**
     * @return list of players
     */
    public ArrayList<Generic_role> getPlayers_list() { return this.players_list; }

    /**
     * @return true if the game is over, else false
     */
    public boolean isGameOver() { return this.game_over; }

    /**
     * @return true if the village won, false if werewolves won (if a couple werewolf+villager won, true)
     */
    public boolean isVillageWin() { return this.village_win; }

    /**
     * @return true if there is equality, else false (if a couple werewolf+villager won, true)
     */
    public boolean isEquality() { return this.equality; }

    /**
     * @return instance of the player targeted by the werewolves
     */
    public Generic_role getTargeted_player_werewolf() { return this.targeted_player_werewolf; }

    /**
     * @return instance of the player targeted by the witch
     */
    public Generic_role getTargeted_player_witch() { return this.targeted_player_witch; }
    


    /**
     * @param role : role name
     * @return index in the players list of the player who has the role specified in parameter
     * <li>-1 if there isn't player with this role in the players list
     */
    private int isThere(String role) {
        for(int i = 0; i < this.players_list.size(); i++) {
            if(this.players_list.get(i).getRole_name().equals(role)) { return i; }
        }
        return -1;
    }


    /**
     * @param name : player's name
     * @return index in the players list of the player who has the name specified in parameter
     * <li>-1 if there isn't player with this name in the players list
     */
    public int findPlayerByName(String name) {
        for(int i = 0; i < this.players_list.size(); i++) {
            if(this.players_list.get(i).getName().equals(name)) { return i; }
        }
        return -1;
    }
    

    /**
     * @param player_name
     * @return instance of the player contained in the players list whose name is equal to the parameter
     */
    private Generic_role getPlayer(String player_name) {
        if(this.findPlayerByName(player_name) != -1) { return this.players_list.get(this.findPlayerByName(player_name)); }
        return null;
    }


    /**
     * @return true if a player in the game is captain, else false
     */
    private boolean isThereCaptain() {
        for(Generic_role player : this.players_list) { if(player.isCaptain()) {return true;} }
        return false;
    }


    /**
     * Looks if the game is over
     */
    private void isGameFinish() {
        // if the players list is empty, equality
        if(this.players_list.size() == 0) {
            this.game_over = true;
            this.village_win = false;
            this.equality = true;
            return;
        } 

        // if there isn't werewolf and the players list is not empty (because there wasn't return before if we are at this step of the function) then there is at least one villager, the village won 
        if(this.isThere("werewolf") == -1) {
            this.game_over = true;
            this.village_win = true;
            this.equality = false;
            return;
        }

        // if we are at this step of the function, the players list is not empty and there is at least one werewolf, so if there isn't villager, the werewolves won 
        if(this.isThere("cupid") == -1 && this.isThere("guard") == -1 && this.isThere("hunter") == -1 && this.isThere("seer") == -1 && this.isThere("witch") == -1 && this.isThere("villager") == -1) {
            this.game_over = true;
            this.village_win = false;
            this.equality = false;
            return;
        }
       
        // if there is at least one werewolf and at least one villager and there are exactly 2 players left (so exactly one werewolf and one villager), and these players have a partner, then a couple werewolf+villager won 
        if(this.players_list.size() == 2 && this.players_list.get(0).getPartner() != null && this.players_list.get(1).getPartner() != null) {
            this.game_over = true;
            this.village_win = true;
            this.equality = true;
        }
    }


    /**
     * Loads the names of players saved in the file data.txt
     * @return ArrayList<String> of all players' names saved in data.txt
     */
    public ArrayList<String> loadPlayers() {
        ArrayList<String> res  = new ArrayList<String>();
        try {
            FileInputStream file = new FileInputStream("data.txt");   
            Scanner scanner = new Scanner(file);  
            
            while(scanner.hasNextLine())
            {
                res.add(scanner.nextLine());
            }
            scanner.close();    
            }
        catch(IOException e) {
            e.printStackTrace();
        }
        return res;
    }


    /**
     * Writes the names of the players (deads or not) in the file data.txt
     */
    public void savePlayers() {
        PrintWriter writer;
        try {
            writer = new PrintWriter("data.txt", "UTF-8");
            for(Generic_role p : this.players_list) {
                writer.println(p.getName());
            }
            for(Generic_role p : this.players_dead_list) {
                writer.println(p.getName());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }



    /**
     * Starts the game
     */
    public void startGame() {
        ArrayList<String> list = this.view.initPlayers();
        this.rolesAllocation(list);
        this.view.start();
        this.play();
    }


    /**
     * Sets the course of the game
     * <ul>
     * <li>Starts the preparation round
     * <li>While the game isn't over, repeats all the stages of the game in their specific order
     * <li>When the game is over, the corresponding victory view is called
     * </ul>
     */
    private void play() {

        this.preparationRound();

        do {

            this.nightStage();
            this.morningStage();

        }while(!this.isGameOver());

        if(this.isVillageWin() && !this.isEquality()) { this.view.villageWin(); }
        if(!this.isVillageWin() && !this.isEquality()){ this.view.werewolvesWin(); }
        if(!this.isVillageWin() && this.isEquality()){ this.view.equality(); }
        if(this.isVillageWin() && this.isEquality()){ this.view.coupleWin(); }
    }


    
    /**
     * Allocates a role for each player of the players list
     * <p>
     * 1 seer, 1 hunter, 1 guard, 1 cupid, 1 thief, 1 witch, 2 werewolves if there are less than 12 players, else 3 werewolves, and villagers to complete
     * @param list : list of players' names
     */
    private void rolesAllocation(ArrayList<String> list) {
        Collections.shuffle(list);

        this.players_list.add(new Seer(list.get(0)));
        this.players_list.add(new Hunter(list.get(1)));
        this.players_list.add(new Guard(list.get(2)));
        this.players_list.add(new Cupid(list.get(3)));
        this.players_list.add(new Thief(list.get(4)));
        this.players_list.add(new Witch(list.get(5)));
        this.players_list.add(new Werewolf(list.get(6)));
        this.players_list.add(new Werewolf(list.get(7)));

        if(list.size()>8) {
            for(int i = 8; i < list.size(); i++) {
                if(list.size() >= 12 && i == 8) { this.players_list.add(new Werewolf(list.get(8))); }
                else { this.players_list.add(new Villager(list.get(i))); }
            }
        }

        Collections.shuffle(this.players_list);
    }


    /**
     * Starts the preparation round
     * <p>
     * => Starts the thief's step and then the Cupid's step
     */
    private void preparationRound() {
        //it's the first round so the thief and Cupid exist and are alive, no verification is required

        int thief_index = this.isThere("thief");
        this.thiefStep(thief_index);

        int cupid_index = this.isThere("cupid");
        this.cupidStep(cupid_index);
    }


    /**
     * Sets the course of the night stage :
     * <ul>
     * <li>Calls the view to notify the beginning of the night
     * <li>If there is a seer, calls the seer's step
     * <li>Calls the werewolves' step
     * <li>If there is a witch, calls the witch's step
     * <li>If there is a guard, calls the guard's step
     * </ul>
     */
    private void nightStage() {

        this.view.startNight();

        //some roles may not exist because the players aren't alive, so a verification is required
        //if the role exist, calls the corresponding step

        int seer_index = this.isThere("seer");
        if(seer_index != -1) { this.seerStep(seer_index); }

        //no verification is required here because there is necessarily at least one werewolf, otherwise if there is not werewolf then the game is finished and the function nightStage is not called once again
        this.werewolfStep();

        int witch_index = this.isThere("witch");
        if(witch_index != -1) { this.witchStep(witch_index); }

        int guard_index = this.isThere("guard");
        if(guard_index != -1) { this.guardStep(guard_index); }
    }


    /**
     * Sets the course of the morning stage :
     * <ul>
     * <li>Calls the view to inform players about players dead during the night
     * <li>Calls the function updateNightDeaths to remove players who are dead
     * <li>Then if the game is not over :
     *    <ul>
     *    <li>If there isn't captain, calls the function to elect a captain
     *    <li>Calls the function to vote for a player to eliminate
     *    </ul>
     * </ul>
     */
    private void morningStage() {

        this.view.startMorning();

        this.updateNightDeaths();

        if(!this.isGameOver()) {
            if(!this.isThereCaptain()) { this.electFirstCaptain(); }
            this.vote();
        }
    }


    /**
     * Removes from the list of players all players who are dead
     * <ul>
     * <li>If one of the players dead was the hunter, calls the hunter's step
     * <li>If one of the players dead was the captain, calls the function elecNewCaptain to allow the captain to choose his successor
     * <li>Calls the function isGameFinish to check if the game is over after removing dead players
     * </ul>
     * NB : if the game is over, it's not neccessary to elect a new captain if the former is dead
     */
    private void updateGame() {
        ArrayList<Generic_role> toRemove = new ArrayList<Generic_role>();
        Generic_role captain = null;
        Hunter hunter = null;
        
        for(Generic_role player : this.players_list) {
            if(player.isDead()) { 

                toRemove.add(player); 
                
                if(player.isCaptain()) { captain = player; }
                if(player instanceof Hunter) { hunter = (Hunter) player; }
            }
        }
        this.players_list.removeAll(toRemove);
        this.players_dead_list.addAll(toRemove);

        if(hunter != null) { this.hunterStep(hunter); }

        this.isGameFinish();

        if(!this.isGameOver() && captain != null) { this.electNewCaptain(captain); }
    }


    /**
     * Calls the function updateGame to do the necessary actions for the players who are dead during the night (by the werewolves and the witch in the night stage)
     */
    private void updateNightDeaths() {
        this.updateGame();
        this.targeted_player_werewolf = null;
        this.targeted_player_witch = null;
    }



    /**
     * Sets the course of the stage of votes : players vote for someone to eliminate
     */
    private void vote() {
        
        // calls the view to get all players' choices in a tab (one table cell = a name of a player who will receive a vote against him, there are as many cells as votes so if a player has many votes against him, his name appears in as many cells as he has votes)
        String[] players_names = this.view.vote();

        // affection of the votes
        for(String name : players_names) {
            this.getPlayer(name).takeVote();
        }
        
        ArrayList<Generic_role> max_votes = new ArrayList<Generic_role>();
        int max_nb_votes = 0;
        // get the instances of the players who have the most votes
        for(Generic_role player : this.players_list) {
            if(player.getVotes_against() > max_nb_votes) { 
                max_votes.clear();
                max_nb_votes = player.getVotes_against();
                max_votes.add(player); 
            }
            else if(player.getVotes_against() == max_nb_votes) {
                max_votes.add(player);
            }
        }

        // if there is only one player who has the most votes 
        if(max_votes.size() == 1) {
            this.view.updateVote(max_votes.get(0));
            max_votes.get(0).dead();
        }
        else { // many players have the same number of votes and it is also the highest number of votes
            
            int index_captain = 0;
            // there is necessary a captain so no condition for the arraylist size is required here
            while(!this.players_list.get(index_captain).isCaptain()) { index_captain++; }

            // get the name of the player choosen by the captain
            String player_name = this.view.captainVote(this.players_list.get(index_captain).getName(), max_votes);
            this.view.updateVote(this.getPlayer(player_name));
            this.getPlayer(player_name).dead();
        }

        // calls this method to eliminate the player who has been killed by the votes
        this.updateGame();

        // reset the numbers of votes against each player to 0
        for(Generic_role player : this.players_list) {
            player.resetVotes_against();
        }
    }


    /**
     * Elects a captain based on a vote of the players
     * <p>
     * This function is called when there isn't captain and then players have to vote for someone to be the captain
     */
    private void electFirstCaptain() {
        String player_name = this.view.electFirstCaptain();
        this.getPlayer(player_name).setCaptain(true);
    }

    
    /**
     * Elects a captain based on the choice of the former captain
     * <p>
     * This function is called when the captain is dead and then he has to to choose his successor
     * @param player : former captain who will elect the next
     */
    private void electNewCaptain(Generic_role player) {
        String player_name = this.view.electNewCaptain(player.getName());
        player.electNewCaptain(this.getPlayer(player_name));
    }



    /**
     * Sets the thief's step
     * <ul>
     * <li>Calls the thief's step function of the view to get the name of the player chosen by the thief
     * <li>Calls the stealRole method and replaces the thief player with his new role in the players list
     * <li>Sets the role of the player who has been stolen to villager
     * <li>Calls the update method of the view
     * </ul>
     * @param thief_index : index in the players list of the player who is the thief
     */
    private void thiefStep(int thief_index) {
        Thief thief = (Thief) this.players_list.get(thief_index);

        String player_name = this.view.thiefStep(thief.getName());

        String new_role = thief.stealRole(this.getPlayer(player_name));
        //the thief's step is called just one time, and it's the first step in the game so the thief does'nt have partner, etc. So change the role of the thief by 
        //replacing him with a new role which has just the same player name is okay to give the illusion that the thief has stole the role...
        switch(new_role) {
            case "cupid":  this.players_list.set(thief_index, new Cupid(thief.getName())); break; 
            case "guard":  this.players_list.set(thief_index, new Guard(thief.getName())); break; 
            case "hunter":  this.players_list.set(thief_index, new Hunter(thief.getName())); break; 
            case "seer":  this.players_list.set(thief_index, new Seer(thief.getName())); break; 
            case "villager":  this.players_list.set(thief_index, new Villager(thief.getName())); break; 
            case "werewolf":  this.players_list.set(thief_index, new Werewolf(thief.getName())); break; 
            case "witch":  this.players_list.set(thief_index, new Witch(thief.getName())); break; 
        }
        //the player who has been stolen is now a villager, same as the thief, he doesn't have partner, etc. so like the thief, create a new role with just keeping the player name is okay
        this.players_list.set(this.findPlayerByName(player_name), new Villager(player_name));

        this.view.updateThiefStep(new_role);
    }


    /**
     * Sets the Cupid's step
     * @param cupid_index : index in the players list of the player who is Cupid
     */
    private void cupidStep(int cupid_index) {
        Cupid cupid = (Cupid) this.players_list.get(cupid_index);
        String [] players_names = this.view.cupidStep(cupid.getName());
        cupid.makeCouple(this.getPlayer(players_names[0]), this.getPlayer(players_names[1]));
        this.view.updateCupidStep();
    }


    /**
     * Sets the seer's step
     * @param seer_index : index in the players list of the player who is the seer
     */
    private void seerStep(int seer_index) {
        Seer seer = (Seer) this.players_list.get(seer_index);
        String player_name = this.view.seerStep(seer.getName());
        String role = seer.watchRole(this.getPlayer(player_name));
        this.view.updateSeerStep(role);
    }


    /**
     * Sets the werewolves' step
     */
    private void werewolfStep() {
        String player_name = this.view.werewolfStep();
        this.targeted_player_werewolf = this.getPlayer(player_name);
        this.targeted_player_werewolf.dead();
        this.view.updateWerewolfStep();
    }


    /**
     * Sets the witch's step
     * @param witch_index : index in the players list of the player who is the witch
     */
    private void witchStep(int witch_index) {
        Witch witch = (Witch) this.players_list.get(witch_index);
        String[] potions = this.view.witchStep(witch);
        
        // if the witch wants to use her healing potion
        if(potions[0].equals("yes")) {
            witch.useHealingPotion(this.targeted_player_werewolf);
        }

        // if the witch wants to use her poisoning potion, then potions[1] contains the name of the player targeted by the witch
        if(!potions[1].equals("no")) {
            this.targeted_player_witch = this.getPlayer(potions[1]);
            witch.usePoisoningPotion(this.targeted_player_witch);
        }

        this.view.updateWitchStep();
    }


    /**
     * Sets the guard's step
     * @param guard_index : index in the players list of the player who is the guard
     */
    private void guardStep(int guard_index) {
        Guard guard = (Guard) this.players_list.get(guard_index);
        String player_name = this.view.guardStep(guard);
        guard.protect(this.getPlayer(player_name));
    }

    
    /**
     * Sets the hunter's step
     * @param hunter : instance of the player who is the hunter
     */
    private void hunterStep(Hunter hunter) {
        String player_name = this.view.hunterStep(hunter.getName());
        hunter.kill(this.getPlayer(player_name));
        this.updateGame();
    }

}