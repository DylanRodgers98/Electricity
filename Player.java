
import java.util.ArrayList;

public class Player {

    private final String playerName;
    private final ArrayList<Card> hand = new ArrayList<>();

    public Player(String name) {
        playerName = name;
    }

    public void addCardsToHand(Card card) {
        hand.add(card);
    }

    public Card turnCard() {
        Card card = hand.get(0);
        hand.remove(0);
        return card;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
    
    public String getName(){
        return playerName;
    }
}
