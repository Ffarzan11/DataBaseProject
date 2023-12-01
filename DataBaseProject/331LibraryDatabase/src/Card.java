public class Card {
    private int cardNumber;
    private int ssn;
    private String expirationDate;

    public Card() {
        // Default cons.
    }

    public Card(int cardNumber, int ssn, String expirationDate) {
        this.cardNumber = cardNumber;
        this.ssn = ssn;
        this.expirationDate = expirationDate;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber() {
        this.cardNumber = cardNumber;
    }

    public int getSSN() {
        return ssn;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate() {
        this.expirationDate = expirationDate;
    }

    public String toString() {
        return "Card{" +
                "cardNumber=" + cardNumber +
                ", ssn=" + ssn +
                ", expirationDate='" + expirationDate + '\'' +
                '}';
    }

}