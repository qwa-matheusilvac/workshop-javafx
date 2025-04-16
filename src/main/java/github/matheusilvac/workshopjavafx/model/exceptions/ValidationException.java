package github.matheusilvac.workshopjavafx.model.exceptions;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;


@Getter
public class ValidationException extends  RuntimeException{

    private Map<String,String> errors = new HashMap<>();

    public ValidationException(String message){
        super(message);
    }

    public void addError(String key, String value){
        errors.put(key,value);
    }

}
