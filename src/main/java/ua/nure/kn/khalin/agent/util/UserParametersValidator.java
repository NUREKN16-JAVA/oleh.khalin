package main.java.ua.nure.kn.khalin.agent.util;

import java.util.Date;
import java.util.Objects;

public class UserParametersValidator {

    public UserParametersValidator() {
    }

    public static boolean validateUserParameters(Object id,Object firstName, Object lastName, Object date) {
        if(Objects.isNull(id) || Objects.isNull(firstName) || Objects.isNull(lastName) || Objects.isNull(date))
            return false;
        if(!(id instanceof Long))
            return false;
        if(!(firstName instanceof String))
            return false;
        if(!(lastName instanceof String))
            return false;
        if(!(date instanceof Date))
            return false;
        return true;
    }

}
