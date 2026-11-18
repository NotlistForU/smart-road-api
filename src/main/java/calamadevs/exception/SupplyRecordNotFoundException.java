package calamadevs.exception;

public class SupplyRecordNotFoundException extends RuntimeException {

    public SupplyRecordNotFoundException() {
        super();
    }

    public SupplyRecordNotFoundException(Long id) {
        super("Supply record not found with id: " + id);
    }

    public SupplyRecordNotFoundException(String message) {
        super(message);
    }
}
