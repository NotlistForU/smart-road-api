package calamadevs.exception;

public class MaintenanceNotFoundException extends RuntimeException {
    public MaintenanceNotFoundException() {
        super("Maintenance record not found");
    }

    public MaintenanceNotFoundException(String message) {
        super(message);
    }

    public MaintenanceNotFoundException(Long id) {
        super("Maintenance not found with id: " + id);
    }
}