package library;

import enums.BookGenre;
import enums.SectionType;


public class Book extends LibraryItem implements Borrowable {
    private Author author;
    private BookGenre genre;
    private boolean isBorrowed;

    public Book(String id, String title, SectionType section, Author author, BookGenre genre) {
        super(id, title, section);
        this.author = author;
        this.genre = genre;
        this.isBorrowed = false;
    }

    @Override
    public void borrowItem(Reader reader) {
        if (!isBorrowed) {
            isBorrowed = true;
            System.out.println("Book borrowed: " + getTitle());
        } else {
            System.out.println("Book already borrowed: " + getTitle());
        }
    }

    @Override
    public void returnItem(Reader reader) {
        if (isBorrowed) {
            isBorrowed = false;
            System.out.println("Book returned: " + getTitle());
        } else {
            System.out.println("Book was not borrowed: " + getTitle());
        }
    }

    public Author getAuthor() {
        return author;
    }

    public BookGenre getGenre() {
        return genre;
    }
}
