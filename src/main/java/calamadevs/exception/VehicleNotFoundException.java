package calamadevs.exception;

public class VehicleNotFoundException extends RuntimeException{

    public VehicleNotFoundException(){
        super();
    }

    public VehicleNotFoundException(String message) {
        super(message);
    }

    public VehicleNotFoundException(Long id){
        super("Vehicle not found with id: " + id);
    }
}
