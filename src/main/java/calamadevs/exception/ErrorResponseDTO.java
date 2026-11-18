package calamadevs.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponseDTO {

    private LocalDateTime timestamp;

    private int status;

    private String error;

    private List<String> details;

    public ErrorResponseDTO(

            LocalDateTime timestamp,
            int status,
            String error,
            List<String> details

    ){

        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.details = details;

    }

    // getters
    public LocalDateTime getTimestamp(){
        return this.timestamp;
    }

    public int getStatus(){
        return this.status;
    }
    public String getError(){
        return this.error;
    }

    public List<String> getDetails(){
        return this.details;
    }
}
