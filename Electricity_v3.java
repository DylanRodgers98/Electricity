
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Electricity_v3 {

    //Initialise static variables
    public static ArrayList<Card> cardsTurnedOver;
    public static int numberOfPlayers;
    public static Player[] playerArray;
    public static ArrayList<Integer> linkedCards;
    public static int numberOfCardsInPlay;

    public static void main(String[] args) {
        //Ask user how many players are playing
        setPlayers();
        //Deal cards to each player
        dealCards();
        //Initialise turn counter
        int turn = 0;
        //Play game
        for (int p = 0; p < numberOfPlayers; p++) {
            //Get current player
            Player currentPlayer = playerArray[p];
            String name = currentPlayer.getName();
            //Ask player to flip card
            System.out.print(name + " - Flip card? Y/N: ");
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            if (input.equalsIgnoreCase("Y")) {
                //Increment turn counter
                turn++;
                //Flip card
                Card turnedCard = currentPlayer.turnCard();
                System.out.println(turnedCard.toString());
                //Add flipped card to array of each player's current face-up cards
                cardsTurnedOver.set(p, turnedCard);
                //Check if card 'links' to previously flipped cards
                checkCardsLink(p);
            } else if (input.equalsIgnoreCase("N")) {
                //If player says NO to flipping card, end execution
                exitGame();
            } else { //If anything other than 'Y' or 'N' is typed
                //Ask player again without incrementing the loop
                p--;
            }

            //If last player
            if (p == numberOfPlayers - 1) {
                //If all cards in play have been played
                if (turn == numberOfCardsInPlay) {
                    exitGame();
                } else {
                    //Go back to first player
                    p = -1; //-1 so when p++ is called p goes back to 0, i.e. starting from Player 1
                }
            }
        }
    }

    /*
     * METHOD TO EXIT GAME
     */
    public static void exitGame() {
        System.out.println("THANKS FOR PLAYING ELECTRICITY!");
        System.exit(0);
    }

    /*
     * METHOD TO SET HOW MANY PLAYERS ARE PLAYING
     */
    public static void setPlayers() {
        //Ask user how many players are playing
        Scanner playerInput = new Scanner(System.in);
        System.out.print("How many players are playing Electricity? ");
        //Check input is integer
        while (!playerInput.hasNextInt()) {
            System.out.println("Please enter an integer value.");
            playerInput.next(); //'Throw away' wrong input
            System.out.print("How many players are playing Electricity? ");
        }
        //Set number of players
        numberOfPlayers = playerInput.nextInt();
        //Create an array to store player objects
        playerArray = new Player[numberOfPlayers];
        //Create player objects for each player and store them in playerArray
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.print("Player " + (i + 1) + " Name: ");
            playerArray[i] = new Player(in.nextLine());
        }
        //Fill ArrayLists with empty elements equal to the numberOfPlayers
        linkedCards = new ArrayList<>(Collections.nCopies(numberOfPlayers, 0));
        Card blank = new Card("", "");
        cardsTurnedOver = new ArrayList<>(Collections.nCopies(numberOfPlayers, blank));
    }

    /*
     * METHOD TO DEAL CARDS TO EACH PLAYER
     */
    public static void dealCards() {
        //Calculate how many cards each player is to be given
        final int CARDS = 52;
        final int CARDS_PER_PLAYER = CARDS / numberOfPlayers;
        numberOfCardsInPlay = CARDS_PER_PLAYER * numberOfPlayers;

        //Shuffle deck
        ArrayList<Card> shuffledDeck = ShuffleDeck.shuffle();

        //Deal cards to each player until each player has the correct amount of cards
        for (int cardNum = 0; cardNum < numberOfCardsInPlay;) {
            for (int j = 0; j < CARDS_PER_PLAYER; j++) {
                for (int k = 0; k < numberOfPlayers; k++) {
                    //Get k'th player
                    Player currentPlayer = playerArray[k];
                    //Add card to player's "hand" array
                    currentPlayer.addCardsToHand(shuffledDeck.get(cardNum));
                    
                    //Increment cardNum
                    cardNum++;
                }
            }
        }

        //Print each player's hand
        //USED FOR TESTING ONLY - COMMENT OUT THIS BLOCK WHEN FINISHED TESTING
        for (int p = 0; p < numberOfPlayers; p++) {
            //Get p'th player
            Player currentPlayer = playerArray[p];
            //Print player's "hand" array
            System.out.println("Player " + (p + 1) + "'s hand: "
                    + Arrays.toString(currentPlayer.getHand().toArray()));
        }
    }

    /*
     * METHOD TO CHECK IF CARDS LINK
     */
    //Variables declared outside method so they don't reset if method recurses
    public static boolean isCardsAdded = false;
    public static int numOfLinkedPlayers = 0;

    public static void checkCardsLink(int curPlayer) {
        //Get previous player
        int prevPlayer = getPrevPlayer(curPlayer);
        //If first player, all players must have already played; else check cards link
        if ((curPlayer == 0 && (cardsTurnedOver.size() >= numberOfPlayers - 1)) || curPlayer > 0) {
            //Create Card objects for the current and previous players' cards
            Card currentCard = cardsTurnedOver.get(curPlayer);
            Card prevCard = cardsTurnedOver.get(prevPlayer);
            //RGet cards' values and suits
            int curCardVal = currentCard.getVal();
            int prevCardVal = prevCard.getVal();
            String currentSuit = currentCard.getSuit();
            String previousSuit = prevCard.getSuit();

            //If cards' values equal
            if (curCardVal == prevCardVal) {
                //Increment numOfLinkedPlayers
                numOfLinkedPlayers++;
                //Add curCardVal and prevCardVal to linkedCards ArrayList
                linkedCards.set(curPlayer, curCardVal);
                linkedCards.set(prevPlayer, prevCardVal);
                //As cards have been added to linkedCards, set Boolean to true
                isCardsAdded = true;

                //Check previous players
                //If first player, all players must have already played
                if (isCardsAdded == true && ((curPlayer == 0 && (cardsTurnedOver.size() >= numberOfPlayers - 1)) || curPlayer > 0)) {
                    //If not all players are already linked, check previous players for links
                    if (numOfLinkedPlayers < linkedCards.size()) {
                        //Recurse method using prevPlayer as a parameter
                        checkCardsLink(prevPlayer);
                    }
                }

                //If cards' suits equal
            } else if (currentSuit.equalsIgnoreCase(previousSuit)) {
                //Increment numOfLinkedPlayers
                numOfLinkedPlayers++;
                //Add curCardVal and prevCardVal to linkedCards ArrayList
                linkedCards.set(curPlayer, curCardVal);
                linkedCards.set(prevPlayer, prevCardVal);
                //As cards have been added to linkedCards, set Boolean to true
                isCardsAdded = true;

                //Check previous players
                //If first player, all players must have already played
                if (isCardsAdded == true && ((curPlayer == 0 && (cardsTurnedOver.size() >= numberOfPlayers - 1)) || curPlayer > 0)) {
                    //If not all players are already linked, check previous players for links
                    if (numOfLinkedPlayers < linkedCards.size()) {
                        //Recurse method using prevPlayer as a parameter
                        checkCardsLink(prevPlayer);
                    }
                }
            }
        }

        //If cards have been added to linkedCards ArrayList
        if (isCardsAdded == true) {
            //Reset static variables
            isCardsAdded = false;
            numOfLinkedPlayers = 0;
            //Start drinking timer
            drinkTimer(linkedCards);
        } else {
            //Fill linkedCards ArrayList with '0' elements 
            linkedCards = new ArrayList<>(Collections.nCopies(numberOfPlayers, 0));
        }
    }

    /*
     * METHOD TO START DRINKING TIMER
     */
    public static void drinkTimer(ArrayList<Integer> cards) {
        //Calculate which card has the largest value
        int largest = 0;
        for (int i = 0; i < cards.size(); i++) {
            //Get i'th player
            Player currentPlayer = playerArray[i];
            //Print how many seconds the player must drink for
            System.out.println(currentPlayer.getName() + " must drink for " + cards.get(i) + " seconds!");
            //Calculate if elem is largest value in the ArrayList
            if (cards.get(i) > largest) {
                largest = cards.get(i);
            }
        }

        //Ask player when to start the drinking timer
        String input;
        do {
            System.out.print("Start Timer? Y/N: ");
            Scanner in = new Scanner(System.in);
            input = in.nextLine();
            if (input.equalsIgnoreCase("Y")) {
                //If yes, count down in seconds from largest card value to 0
                Countdown.timer(largest);

                //Make program wait for countdown to end before executing the rest of the code
                try {
                    TimeUnit.SECONDS.sleep(largest + 1);
                } catch (InterruptedException ex) {
                    System.out.println(ex);
                } finally {
                    System.out.println("");
                }
            }
        } while (!input.equalsIgnoreCase("Y"));
    }

    /*
     * METHOD TO GET PREVIOUS PLAYER NUMBER
     */
    public static int getPrevPlayer(int curPlayer) {
        //Initialise prevPlayer
        int prevPlayer = 0;
        
        if (curPlayer == 0) {
            return numberOfPlayers - 1;
        } else if (curPlayer > 0) {
            prevPlayer = curPlayer - 1;
        }
        
        return prevPlayer;
    }
}
