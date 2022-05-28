package application;

import java.util.Locale;
import java.util.ResourceBundle;

public enum StatusCodes {

	
    OK,
    MESSAGE_TAMPERED_WITH,
    SHA256_CHECKSUM_NOT_ENTERED,
    MESSAGE_SAME;
    

    private static final String STATUSCODES_CONST = "resources.StatusCodes";


    public String getMessageForLanguage(String lang) {
        return ResourceBundle.getBundle(STATUSCODES_CONST, Locale.forLanguageTag(lang)).getString(name());
    
    }
}
