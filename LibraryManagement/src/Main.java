import library.*;
import services.*;
import exceptions.*;
import enums.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        LibraryService libraryService = new LibraryService();

        // Sample data
        Author author = new Author("1", "J.K. Rowling");
        Book book1 = new Book("1", "Harry Potter and the Sorcerer's Stone", SectionType.CHILDREN, author, BookGenre.FANTASY);
        Book book2 = new Book("2", "Harry Potter and the Chamber of Secrets", SectionType.CHILDREN, author, BookGenre.FANTASY);
        Reader reader = new Reader("1", "John Doe", "john.doe@example.com");
        Publisher publisher = new Publisher("1", "Bloomsbury");
        Librarian librarian = new Librarian("1", "Jane Smith");

        libraryService.addBook(book1);
        libraryService.addBook(book2);
        libraryService.addReader(reader);
        libraryService.addPublisher(publisher);
        libraryService.addLibrarian(librarian);

        try {
            // Issue and return a book
            libraryService.issueBook("1", "1");
            libraryService.returnBook("1", "1");

            // Search for books by title
            List<Book> booksByTitle = libraryService.searchBooksByTitle("Harry Potter and the Sorcerer's Stone");
            List<Book> booksByAuthor = libraryService.searchBooksByAuthor("J.K. Rowling");
            List<Book> booksBySection = libraryService.listBooksBySection(SectionType.CHILDREN);
            List<Book> overdueBooks = libraryService.listOverdueBooks();
            List<Book> booksByGenre = libraryService.listBooksByGenre(BookGenre.FANTASY);

            System.out.println("Books by Title: " + booksByTitle.size());
            System.out.println("Books by Author: " + booksByAuthor.size());
            System.out.println("Books by Section: " + booksBySection.size());
            System.out.println("Overdue Books: " + overdueBooks.size());
            System.out.println("Books by Genre: " + booksByGenre.size());

            // List publishers
            List<Publisher> publishers = libraryService.listAllPublishers();
            System.out.println("Publishers: " + publishers.size());

            // List librarians
            List<Librarian> librarians = libraryService.listAllLibrarians();
            System.out.println("Librarians: " + librarians.size());

            // Assign librarian to section
            libraryService.assignLibrarianToSection("1", "1");

        } catch (BookNotFoundException e) {
            // Handle BookNotFoundException
            System.out.println(e.getMessage()); // Print the error message
            e.printStackTrace(); // Print the stack trace for debugging
        } catch (ReaderNotFoundException e) {
            // Handle ReaderNotFoundException
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Handle other exceptions
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        // Save data to CSV on exit
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            libraryService.saveDataToCSV();
        }));
    }
}
