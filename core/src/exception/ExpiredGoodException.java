package exception;

public class ExpiredGoodException extends Exception {

    public ExpiredGoodException(String itemName) {
        super("The item " + itemName + " has expired and cannot be sold.");
    }}