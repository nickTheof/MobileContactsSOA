package gr.aueb.cf.mobilecontacts.validation;

import gr.aueb.cf.mobilecontacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.mobilecontacts.dto.MobileContactUpdateDTO;

public class ValidationUtil {
    /**
     *  No instances of this class should be available
     */
    private ValidationUtil() {};

    public static String validate(MobileContactInsertDTO mobileContactInsertDTO) {
        String errorVector = "";
        if (mobileContactInsertDTO.getPhoneNumber().length() < 5) {
            errorVector += "Error. Phone number must contain at least five digits.\n";
        }
        if (mobileContactInsertDTO.getFirstname().length() < 2) {
            errorVector += "Error. Firstname must contain at least two characters.\n";
        }
        if (mobileContactInsertDTO.getLastname().length() < 2) {
            errorVector += "Error. Lastname must contain at least two characters.\n";
        }
        return errorVector;
    }

    public static String validate(MobileContactUpdateDTO mobileContactUpdateDTO) {
        String errorVector = "";
        if (mobileContactUpdateDTO.getPhoneNumber().length() < 5) {
            errorVector += "Error. Phone number must contain at least five digits.\n";
        }
        if (mobileContactUpdateDTO.getFirstname().length() < 2) {
            errorVector += "Error. Firstname must contain at least two characters.\n";
        }
        if (mobileContactUpdateDTO.getLastname().length() < 2) {
            errorVector += "Error. Lastname must contain at least two characters.\n";
        }
        return errorVector;
    }


}
