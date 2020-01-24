package scanner;

import java.util.Objects;

public class BadCharacterException extends Exception
{
    private String errorMessage;

    public BadCharacterException(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage()
    {
        return errorMessage;
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
