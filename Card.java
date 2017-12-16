
public class Card {
    public String cardValue;
    public String cardSuit;
    
    public Card (String val, String suit){
        cardValue = val;
        cardSuit = suit;
    }
    
    public int getVal(){
        int cardVal;
        //If card is named (e.g. Jack/King/Queen/Ace) return numerical value for that card
        switch (cardValue) {
            case "Ace":
                cardVal = 14;
                break;
            case "Jack":
                cardVal = 11;
                break;
            case "Queen":
                cardVal = 12;
                break;
            case "King":
                cardVal = 13;
                break;
            case "":
                cardVal = -1;
                break;
            default:
                cardVal = Integer.parseInt(cardValue);
                break;
        }
        return cardVal;
    }
    
    public String getSuit(){
        return cardSuit;
    }
    
    @Override
    public String toString(){
        return cardValue + " of " + cardSuit;
    }
    
}
