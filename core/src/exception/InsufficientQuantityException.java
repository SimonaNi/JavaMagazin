package exception;

public class InsufficientQuantityException extends Exception {
    public InsufficientQuantityException(String itemName, int missingQuantity) {
        super("Insufficient quantity of " + itemName + ". Missing quantity: " + missingQuantity);
    }
}
