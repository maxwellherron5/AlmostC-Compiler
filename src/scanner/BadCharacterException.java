package scanner;

import java.util.Objects;

/**
 * BadCharacterException.java
 * @author Maxwell Herron
 * This class is my custom exception that will be thrown when the
 * scanner picks up lexeme input that is not matched with any of my
 * specified tokentypes.
 */
public class BadCharacterException extends RuntimeException {
    // This string will hold the output error message
    private String errorMessage;

    /**
     * Creates the BadCharacterException object with an input error message
     * associated with it.
     * @param errorMessage
     */
    public BadCharacterException(String errorMessage)
    {
        this.errorMessage = errorMessage;
        System.out.println(errorMessage);
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String message) {
        this.errorMessage = message;
    }

    @Override
    public String toString() {
        return "BadCharacterException: " + this.errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BadCharacterException)) return false;
        BadCharacterException ex = (BadCharacterException) o;
        return Objects.equals(getErrorMessage(), ex.getErrorMessage());
    }
}
