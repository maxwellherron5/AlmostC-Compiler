package scanner;

public class BadCharacterException extends Exception
{
    private String errorMessage;

    public BadCharacterException(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "BadCharacterException: " + this.errorMessage;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
