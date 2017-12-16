
import java.util.ArrayList;
import java.util.Random;

public class ShuffleDeck {

    private static final String[] SUITS = {"Hearts", "Diamonds", "Clubs", "Spades"};
    private static final String[] VALUES = {"2", "3", "4", "5", "6", "7", "8",
        "9", "10", "Jack", "Queen", "King", "Ace"};
    private static final ArrayList<Card> DECK = new ArrayList<>();

    public static ArrayList<Card> shuffle() {
        final int NUMBER_OF_CARDS_IN_DECK = 52;
        final int NUMBER_OF_TIMES_DECK_SHUFFLED = 7;
        
        //Build the DECK
        buildDeck();

        //Shuffle the DECK
        for (int s = 0; s < NUMBER_OF_TIMES_DECK_SHUFFLED; s++) {
            //Shuffle cards in the DECK
            for (int i = 0; i < NUMBER_OF_CARDS_IN_DECK; i++) {
                //Get i'th card in the ArrayList
                Card card = DECK.get(i);
                //Randomly generate new position in ArrayList for the card
                Random rand = new Random();
                int randomPos = rand.nextInt(52);
                //Remove card from original position in ArrayList
                DECK.remove(i);
                //Add card in new position in ArrayList
                DECK.add(randomPos, card);
            }
        }

        return DECK;
    }

    public static void buildDeck() {
        //Build the DECK in order
        for (int suit = 0; suit < 4; suit++) { //for each suit
            for (int val = 0; val < 13; val++) { //for each card value
                //Create card object
                Card newCard = new Card(VALUES[val], SUITS[suit]);
                //Add card to the DECK
                DECK.add(newCard);
            }
        }
    }
}
