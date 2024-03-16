package it.polimi.ingsw.gc03;


import java.util.List;


public class Desk {
    /**
     * The two resource cards that are visible on the table.
     */
    private List<CardResource> displayedResource;

    /**
     * The two gold cards that are visible on the table.
     */
    private List<CardGold> displayedGold;

    /**
     * The two public objective cards.
     */
    private List<CardObjective> displayedObjective;

    /**
     * The Resource Deck on the table
     */
    private DeckResource deckResource;

    /**
     * The Gold Deck on the table
     */
    private DeckGold deckGold;

    /**
     * The players whose codex are on the table
     */
    private List<Player> players;

    /**
     * Deck formed by the 6 Starter Card
     */
    private DeckStarter deckStarter;

    /**
     * Constructs a new Desk
     * @param displayedResource ResourceCards shown on the table
     * @param displayedGold GoldCards shown on the table
     * @param displayedObjective ObjectiveCards shown on the table
     * @param deckResource deckResource on the table
     * @param deckGold deckGold on the Table
     * @param players players at the Table
     */
    public Desk(List<CardResource> displayedResource,List<CardGold> displayedGold,List<CardObjective> displayedObjective,DeckResource deckResource,DeckGold deckGold,List<Player> players,DeckStarter deckStarter){
        this.setDisplayedResource(displayedResource);
        this.setDisplayedGold(displayedGold);
        this.setDisplayedObjective(displayedObjective);
        this.setDeckResource(deckResource);
        this.setDeckGold(deckGold);
        this.setPlayers(players);
        this.setDeckStarter(deckStarter);
    }

    /**
     * Setter DisplayedResources Card
     * @param displayedResource ResourceCards shown on the table
     */
    public void setDisplayedResource(List<CardResource> displayedResource){this.displayedResource = displayedResource;}

    /**
     * Getter DisplayedResources Card
     * @return the displayed Resources
     */
    public List<CardResource> getDisplayResource(){return this.displayedResource;}

    /**
     * Setter DisplayedGold Card
     * @param displayedGold GoldCards shown on the table
     */
    public void setDisplayedGold(List<CardGold> displayedGold){this.displayedGold = displayedGold;}

    /**
     * Getter DisplayedGold Card
     * @return the displayed GoldResources
     */
    public List<CardGold> getDisplayedGold(){return this.displayedGold;}

    /**
     * Setter Objective
     * @param displayedObjective ObjectiveCards shown on the table
     */
    public void setDisplayedObjective(List<CardObjective> displayedObjective){this.displayedObjective = displayedObjective;}

    /**
     * Getter DisplayedObjective Card
     * @return the displayed Common Objectives
     */
    public List<CardObjective> getDisplayedObjective(){return this.displayedObjective;}

    /**
     * Setter DeckResource
     * @param deckResource deckResource on the table
     */
    public void setDeckResource(DeckResource deckResource){this.deckResource = deckResource;}

    /**
     * Getter Resource Deck
     * @return the DeckResource on the table
     */
    public DeckResource getDeckResource(){return this.deckResource;}

    /**
     * Setter DeckGold
     * @param deckGold deckGold on the Table
     */
    public void setDeckGold(DeckGold deckGold){this.deckGold = deckGold;}

    /**
     * Getter Gold Deck
     * @return the DeckGold on the table
     */
    public DeckGold getDeckGold(){return this.deckGold;}

    /**
     * Setter Players
     * @param players players at the Table
     */
    public void setPlayers(List<Player> players) {this.players = players;}

    /**
     * Getter Players
     * @return a List of players at the table
     */
    public List<Player> getPlayers(){return this.players;}

    /**
     * Setter StarterDeck
     * @param deckStarter Deck of Starter Card
     */
    public void setDeckStarter(DeckStarter deckStarter){this.deckStarter=deckStarter;}

    /**
     * Add a player to the desk, throws two exceptions
     * @param player Player to be added
     * @throws PlayerAlreadyJoinedException Exception
     * @throws DeskIsFullException Exception
     */
    public void addPlayer(Player player) throws PlayerAlreadyJoinedException,DeskIsFullException{
       if(this.players.contains(player)) {
           throw new PlayerAlreadyJoinedException("Error: This Player is already at this table");
       }
       else if (this.players.size()<4) {
           this.players.add(player);
       }else throw new DeskIsFullException("Error: The desk is already full");
    }

    /**
     * Remove a player from the desk
     * @param player Player to be removed
     */
    public void removePlayer(Player player){
        this.players.remove(player);
    }

    /**
     * Draw a card from ResourceDeck and display it only if Displayed Resources are less than 2
     */
    public void drawResourceAndDisplay() throws NoMoreCardException{
        if(!deckResource.getDeckResource().isEmpty()){
            if(this.displayedResource.size()<2) this.displayedResource.add(this.deckResource.drawCardResource());
        } else throw new NoMoreCardException("Error: ResourceCard in the deck are finished");
    }

    /**
     * Chose a ResourceCard from the DisplayedResources and return it
     * @param i Index of the Displayed List
     * @return the chosen ResourceCard from the DisplayedResources
     * @throws CardNotFoundException Exception
     */
    public CardResource drawDisplayedResource(int i) throws CardNotFoundException{
        if(this.displayedResource.size()<i) throw new CardNotFoundException("Error: ResourceCard not found");
        else {
            CardResource card = this.displayedResource.get(i);
            this.displayedResource.remove(i);
            return card;
        }
    }
    /**
     * Draw a card from GoldDeck and display it, only if Displayed Gold are less than 2
     */
    public void drawGoldAndDisplay() throws NoMoreCardException {
        if (!this.deckGold.getDeckGold().isEmpty()) {
            if (this.displayedGold.size() < 2) this.displayedGold.add(this.deckGold.drawCardGold());
        } else throw new NoMoreCardException("Error: GoldCard in the deck are finished");
    }
    /**
     * Chose a GoldCard from the DisplayedGold and return it
     * @param i Index of the Displayed List
     * @return the chosen GoldCard from the DisplayedGold
     * @throws CardNotFoundException Exception
     */
    public CardGold drawGoldResource(int i) throws CardNotFoundException{
        if(this.displayedGold.size()<i) throw new CardNotFoundException("Error: GoldCard not found");
        else {
            CardGold card = this.displayedGold.get(i);
            this.displayedGold.remove(i);
            return card;
        }
    }

    /**
     * Sets a random StarterCard for every player at the desk, the shuffle has been made at the end of the construction of the deck
     */
    public void setRandomStarterCard() throws NoMoreCardException {
        for (Player player : this.players) {
            player.setCardStarter(this.deckStarter.drawCardStarter());
        }
    }
}
