import library.*;
import services.*;
import exceptions.*;
import enums.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        LibraryService libraryService = new LibraryService();
        ReaderService readerService = new ReaderService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Add sample data:");

        // adaugare autori
        System.out.println("Enter author details:");
        System.out.print("Author ID: ");
        String authorId = scanner.nextLine();
        System.out.print("Author Name: ");
        String authorName = scanner.nextLine();
        Author author = new Author(authorId, authorName);

        // adaugare carti
        System.out.println("Enter book details:");
        System.out.print("Book ID: ");
        String bookId1 = scanner.nextLine();
        System.out.print("Book Title: ");
        String bookTitle1 = scanner.nextLine();
        System.out.print("Book Section (ADULTI, COPII, DIGITALE, MANUALE): ");
        SectionType section1 = SectionType.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Book Genre (FICTIUNE, LITERATURA, MISTER, SF, BIOGRAFIE, STIINTA): ");
        BookGenre genre1 = BookGenre.valueOf(scanner.nextLine().toUpperCase());
        Book book1 = new Book(bookId1, bookTitle1, section1, author, genre1);
        libraryService.addBook(book1);

        System.out.print("Book ID: ");
        String bookId2 = scanner.nextLine();
        System.out.print("Book Title: ");
        String bookTitle2 = scanner.nextLine();
        System.out.print("Book Section (ADULTI, COPII, DIGITALE, MANUALE): ");
        SectionType section2 = SectionType.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Book Genre (FICTIUNE, LITERATURA, MISTER, SF, BIOGRAFIE, STIINTA): ");
        BookGenre genre2 = BookGenre.valueOf(scanner.nextLine().toUpperCase());
        Book book2 = new Book(bookId2, bookTitle2, section2, author, genre2);
        libraryService.addBook(book2);

        // adaugare cititori
        System.out.println("Enter reader details:");
        System.out.print("Reader ID: ");
        String readerId = scanner.nextLine();
        System.out.print("Reader Name: ");
        String readerName = scanner.nextLine();
        System.out.print("Reader Email: ");
        String readerEmail = scanner.nextLine();
        Reader reader = new Reader(readerId, readerName, readerEmail);
        readerService.addReader(reader);

        // adaugare edituri
        System.out.println("Enter publisher details:");
        System.out.print("Publisher ID: ");
        String publisherId = scanner.nextLine();
        System.out.print("Publisher Name: ");
        String publisherName = scanner.nextLine();
        Publisher publisher = new Publisher(publisherId, publisherName);
        libraryService.addPublisher(publisher);

        // adaugare bibliotecari
        System.out.println("Enter librarian details:");
        System.out.print("Librarian ID: ");
        String librarianId = scanner.nextLine();
        System.out.print("Librarian Name: ");
        String librarianName = scanner.nextLine();
        Librarian librarian = new Librarian(librarianId, librarianName);
        libraryService.addLibrarian(librarian);

        try {

            // imprumutare carte
            System.out.println("Issuing a book:");
            System.out.print("Enter Book ID: ");
            String issueBookId = scanner.nextLine();
            System.out.print("Enter Reader ID: ");
            String issueReaderId = scanner.nextLine();
            libraryService.issueBook(issueBookId, issueReaderId);

            // returnare carte
            System.out.println("Returning a book:");
            System.out.print("Enter Book ID: ");
            String returnBookId = scanner.nextLine();
            System.out.print("Enter Reader ID: ");
            String returnReaderId = scanner.nextLine();
            libraryService.returnBook(returnBookId, returnReaderId);

            // cautare carte dupa titlu
            System.out.print("Enter title to search books: ");
            String searchTitle = scanner.nextLine();
            List<Book> booksByTitle = libraryService.searchBooksByTitle(searchTitle);

            System.out.println("Books by Title: " + booksByTitle.size());
            for (Book book : booksByTitle) {
                System.out.println(book.getTitle());
            }

            // cautare carti dupa numele autorului
            System.out.print("Enter author name to search books: ");
            String searchAuthor = scanner.nextLine();
            List<Book> booksByAuthor = libraryService.searchBooksByAuthor(searchAuthor);

            System.out.println("Books by Author: " + booksByAuthor.size());
            for (Book book : booksByAuthor) {
                System.out.println(book.getTitle());
            }

            // carti dupa sectiune
            System.out.print("Enter section to list books (ADULTI, COPII, DIGITALE, MANUALE\n): ");
            SectionType searchSection = SectionType.valueOf(scanner.nextLine().toUpperCase());
            List<Book> booksBySection = libraryService.listBooksBySection(searchSection);

            System.out.println("Books by Section: " + booksBySection.size());
            for (Book book : booksBySection) {
                System.out.println(book.getTitle());
            }

            // carti nereturnate
            List<Book> overdueBooks = libraryService.listOverdueBooks();
            System.out.println("Overdue Books: " + overdueBooks.size());
            for (Book book : overdueBooks) {
                System.out.println(book.getTitle());
            }

            // carti dupa gen
            System.out.print("Enter genre to list books (FICTIUNE, LITERATURA, MISTER, SF, BIOGRAFIE, STIINTA): ");
            BookGenre searchGenre = BookGenre.valueOf(scanner.nextLine().toUpperCase());
            List<Book> booksByGenre = libraryService.listBooksByGenre(searchGenre);

            System.out.println("Books by Genre: " + booksByGenre.size());
            for (Book book : booksByGenre) {
                System.out.println(book.getTitle());
            }

            // lista editurilor
            List<Publisher> publishers = libraryService.listAllPublishers();
            System.out.println("Publishers: " + publishers.size());
            for (Publisher pub : publishers) {
                System.out.println(pub.getName());
            }

            // lista bibliotecarilor
            List<Librarian> librarians = libraryService.listAllLibrarians();
            System.out.println("Librarians: " + librarians.size());
            for (Librarian lib : librarians) {
                System.out.println(lib.getName());
            }

            // asignarea unui bibliotecar la o sectiune
            System.out.print("Enter Librarian ID to assign: ");
            String assignLibrarianId = scanner.nextLine();
            System.out.print("Enter Section ID to assign to: ");
            String assignSectionId = scanner.nextLine();
            libraryService.assignLibrarianToSection(assignLibrarianId, assignSectionId);

            // lista cititorilor
            List<Reader> allReaders = readerService.listReaders();
            System.out.println("Total Readers: " + allReaders.size());
            for (Reader r : allReaders) {
                System.out.println(r.getName());
            }

            // cautarea cititorilor dupa nume
            System.out.print("Enter reader name to search: ");
            String searchReaderName = scanner.nextLine();
            List<Reader> readersByName = readerService.searchReadersByName(searchReaderName);
            System.out.println("Readers named '" + searchReaderName + "': " + readersByName.size());
            for (Reader r : readersByName) {
                System.out.println(r.getName());
            }

            // stergere cititor
            System.out.print("Enter Reader ID to remove: ");
            String removeReaderId = scanner.nextLine();
            readerService.removeReader(removeReaderId);
            System.out.println("Reader removed: " + removeReaderId);

        } catch (BookNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (ReaderNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        // salvare date in fisier CSV
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            libraryService.saveDataToCSV();
        }));

        scanner.close();
    }
}
