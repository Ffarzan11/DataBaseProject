import java.sql.Date;

public class Card {
    private long cardNumber;
    private int ssn;
    private Date expirationDate;

    public Card() {
        // Default cons.
    }

    public Card(long cardNumber,  Date expirationDate, int ssn) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.ssn = ssn;

    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getSSN() {
        return ssn;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date date) {
        this.expirationDate = date;
    }

    public String toString() {
        return "Card{" +
                "cardNumber=" + cardNumber +
                ", ssn=" + ssn +
                ", expirationDate='" + expirationDate + '\'' +
                '}';
    }

}
