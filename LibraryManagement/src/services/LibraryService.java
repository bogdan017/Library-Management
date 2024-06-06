package services;

import enums.BookGenre;
import enums.SectionType;
import library.*;

import java.time.LocalDate;
import java.util.*;
import exceptions.*;

public class LibraryService {
    private Map<String, Book> books = new HashMap<>();
    private Map<String, Reader> readers = new HashMap<>();
    private List<BorrowedBook> borrowedBooks = new ArrayList<>();
    private Map<String, Publisher> publishers = new HashMap<>();
    private Map<String, Librarian> librarians = new HashMap<>();

    private CSVService<Book> bookCSVService = CSVService.getInstance();
    private CSVService<Reader> readerCSVService = CSVService.getInstance();
    private CSVService<Publisher> publisherCSVService = CSVService.getInstance();
    private CSVService<Librarian> librarianCSVService = CSVService.getInstance();

    private static final String BOOK_CSV_FILE = "books.csv";
    private static final String READER_CSV_FILE = "readers.csv";
    private static final String PUBLISHER_CSV_FILE = "publishers.csv";
    private static final String LIBRARIAN_CSV_FILE = "librarians.csv";

    public LibraryService() {
        loadBooksFromCSV();
        loadReadersFromCSV();
        loadPublishersFromCSV();
        loadLibrariansFromCSV();
    }

    private void loadBooksFromCSV() {
        List<Book> bookList = bookCSVService.readFromCSV(BOOK_CSV_FILE, (row) -> {
            Author author = new Author(row[3], row[4]);
            return new Book(row[0], row[1], SectionType.valueOf(row[2]), author, BookGenre.valueOf(row[5]));
        });
        for (Book book : bookList) {
            books.put(book.getId(), book);
        }
    }

    private void loadReadersFromCSV() {
        List<Reader> readerList = readerCSVService.readFromCSV(READER_CSV_FILE, (row) -> new Reader(row[0], row[1], row[2]));
        for (Reader reader : readerList) {
            readers.put(reader.getId(), reader);
        }
    }

    private void loadPublishersFromCSV() {
        List<Publisher> publisherList = publisherCSVService.readFromCSV(PUBLISHER_CSV_FILE, (row) -> new Publisher(row[0], row[1]));
        for (Publisher publisher : publisherList) {
            publishers.put(publisher.getId(), publisher);
        }
    }

    private void loadLibrariansFromCSV() {
        List<Librarian> librarianList = librarianCSVService.readFromCSV(LIBRARIAN_CSV_FILE, (row) -> {
            Librarian librarian = new Librarian(row[0], row[1]);
            if (row.length > 2) {
                librarian.setSectionId(row[2]);
            }
            return librarian;
        });
        for (Librarian librarian : librarianList) {
            librarians.put(librarian.getId(), librarian);
        }
    }

    public void addBook(Book book) {
        books.put(book.getId(), book);
        System.out.println("Book added: " + book.getTitle());
    }

    public void addReader(Reader reader) {
        readers.put(reader.getId(), reader);
        System.out.println("Reader added: " + reader.getName());
    }

    public void addPublisher(Publisher publisher) {
        publishers.put(publisher.getId(), publisher);
        System.out.println("Publisher added: " + publisher.getName());
    }

    public void addLibrarian(Librarian librarian) {
        librarians.put(librarian.getId(), librarian);
        System.out.println("Librarian added: " + librarian.getName());
    }

    public void assignLibrarianToSection(String librarianId, String sectionId) throws Exception {
        Librarian librarian = librarians.get(librarianId);
        if (librarian == null) {
            throw new Exception("Librarian not found: " + librarianId);
        }
        librarian.setSectionId(sectionId);
        System.out.println("Librarian " + librarian.getName() + " assigned to section " + sectionId);
    }

    public List<Publisher> listAllPublishers() {
        return new ArrayList<>(publishers.values());
    }

    public List<Librarian> listAllLibrarians() {
        return new ArrayList<>(librarians.values());
    }

    public void issueBook(String bookId, String readerId) throws BookNotFoundException, ReaderNotFoundException {
        Book book = books.get(bookId);
        if (book == null) {
            throw new BookNotFoundException("Book not found: " + bookId);
        }

        Reader reader = readers.get(readerId);
        if (reader == null) {
            throw new ReaderNotFoundException("Reader not found: " + readerId);
        }

        // imprumuta cartea si adauga un log in fisierul csv pentru actionarea imprumutului
        book.borrowItem(reader);
        borrowedBooks.add(new BorrowedBook(book, reader, LocalDate.now().toString(), LocalDate.now().plusDays(14).toString()));
        AuditService.logAction("issueBook");
    }

    public void returnBook(String bookId, String readerId) throws BookNotFoundException, ReaderNotFoundException {
        Book book = books.get(bookId);
        if (book == null) {
            throw new BookNotFoundException("Book not found: " + bookId);
        }

        Reader reader = readers.get(readerId);
        if (reader == null) {
            throw new ReaderNotFoundException("Reader not found: " + readerId);
        }

        // Return the book and log the action
        book.returnItem(reader);
        borrowedBooks.removeIf(borrowedBook -> borrowedBook.getBook().getId().equals(bookId) && borrowedBook.getReader().getId().equals(readerId));
        AuditService.logAction("returnBook");
    }

    public List<Book> searchBooksByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                result.add(book);
            }
        }
        AuditService.logAction("searchBooksByTitle");
        return result;
    }

    public List<Book> searchBooksByAuthor(String authorName) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().getName().equalsIgnoreCase(authorName)) {
                result.add(book);
            }
        }
        AuditService.logAction("searchBooksByAuthor");
        return result;
    }

    public List<Book> listBooksBySection(SectionType sectionType) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getSection() == sectionType) {
                result.add(book);
            }
        }
        AuditService.logAction("listBooksBySection");
        return result;
    }

    public List<Book> listOverdueBooks() {
        List<Book> result = new ArrayList<>();
        LocalDate now = LocalDate.now();
        for (BorrowedBook borrowedBook : borrowedBooks) {
            if (LocalDate.parse(borrowedBook.getDueDate()).isBefore(now)) {
                result.add(borrowedBook.getBook());
            }
        }
        AuditService.logAction("listOverdueBooks");
        return result;
    }

    public List<Book> listBooksByGenre(BookGenre genre) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getGenre() == genre) {
                result.add(book);
            }
        }
        AuditService.logAction("listBooksByGenre");
        return result;
    }

    public void saveDataToCSV() {
        List<Book> bookList = new ArrayList<>(books.values());
        bookCSVService.writeToCSV(BOOK_CSV_FILE, bookList, (book) -> new String[]{
                book.getId(), book.getTitle(), book.getSection().name(), book.getAuthor().getId(), book.getAuthor().getName(), book.getGenre().name()
        });

        List<Reader> readerList = new ArrayList<>(readers.values());
        readerCSVService.writeToCSV(READER_CSV_FILE, readerList, (reader) -> new String[]{
                reader.getId(), reader.getName(), reader.getEmail()
        });

        List<Publisher> publisherList = new ArrayList<>(publishers.values());
        publisherCSVService.writeToCSV(PUBLISHER_CSV_FILE, publisherList, (publisher) -> new String[]{
                publisher.getId(), publisher.getName()
        });

        List<Librarian> librarianList = new ArrayList<>(librarians.values());
        librarianCSVService.writeToCSV(LIBRARIAN_CSV_FILE, librarianList, (librarian) -> {
            String[] row = {librarian.getId(), librarian.getName()};
            if (librarian.getSectionId() != null) {
                row = new String[]{librarian.getId(), librarian.getName(), librarian.getSectionId()};
            }
            return row;
        });
    }
}
