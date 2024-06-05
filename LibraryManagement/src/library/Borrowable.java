package library;

public interface Borrowable {
    void borrowItem(Reader reader);
    void returnItem(Reader reader);
}
