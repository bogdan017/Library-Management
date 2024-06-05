package library;

public class BorrowedBook {
    private Book book;
    private Reader reader;
    private String borrowDate;
    private String dueDate;

    public BorrowedBook(Book book, Reader reader, String borrowDate, String dueDate) {
        this.book = book;
        this.reader = reader;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public Book getBook() {
        return book;
    }

    public Reader getReader() {
        return reader;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getDueDate() {
        return dueDate;
    }
}
