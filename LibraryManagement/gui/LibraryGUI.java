import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import library.*;
import services.*;
import exceptions.*;
import enums.*;

public class LibraryGUI {
    private static LibraryService libraryService;

    public static void main(String[] args) {
        libraryService = new LibraryService();

        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = createInputPanel();
        frame.add(inputPanel, BorderLayout.NORTH);

        JTextArea resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton performActionButton = new JButton("Perform Action");
        performActionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get input values
                JTextField authorIdField = (JTextField) inputPanel.getComponent(1);
                JTextField authorNameField = (JTextField) inputPanel.getComponent(3);
                JTextField bookIdField = (JTextField) inputPanel.getComponent(5);
                JTextField bookTitleField = (JTextField) inputPanel.getComponent(7);
                JTextField readerIdField = (JTextField) inputPanel.getComponent(9);
                JTextField readerNameField = (JTextField) inputPanel.getComponent(11);
                JTextField readerEmailField = (JTextField) inputPanel.getComponent(13);

                String authorId = authorIdField.getText();
                String authorName = authorNameField.getText();
                String bookId = bookIdField.getText();
                String bookTitle = bookTitleField.getText();
                String readerId = readerIdField.getText();
                String readerName = readerNameField.getText();
                String readerEmail = readerEmailField.getText();

                Author author = new Author(authorId, authorName);
                Book book = new Book(bookId, bookTitle, SectionType.COPII, author, BookGenre.FICTIUNE);
                Reader reader = new Reader(readerId, readerName, readerEmail);

                libraryService.addBook(book);
                libraryService.addReader(reader);

                StringBuilder result = new StringBuilder();
                try {
                    libraryService.issueBook(bookId, readerId);
                    libraryService.returnBook(bookId, readerId);

                    List<Book> booksByTitle = libraryService.searchBooksByTitle(bookTitle);
                    List<Book> booksByAuthor = libraryService.searchBooksByAuthor(authorName);
                    List<Book> booksBySection = libraryService.listBooksBySection(SectionType.COPII);
                    List<Book> overdueBooks = libraryService.listOverdueBooks();
                    List<Book> booksByGenre = libraryService.listBooksByGenre(BookGenre.FICTIUNE);

                    result.append("Books by Title: ").append(booksByTitle.size()).append("\n");
                    result.append("Books by Author: ").append(booksByAuthor.size()).append("\n");
                    result.append("Books by Section: ").append(booksBySection.size()).append("\n");
                    result.append("Overdue Books: ").append(overdueBooks.size()).append("\n");
                    result.append("Books by Genre: ").append(booksByGenre.size()).append("\n");

                    List<Publisher> publishers = libraryService.listAllPublishers();
                    result.append("Publishers: ").append(publishers.size()).append("\n");

                    List<Librarian> librarians = libraryService.listAllLibrarians();
                    result.append("Librarians: ").append(librarians.size()).append("\n");

                    libraryService.assignLibrarianToSection("1", "1");

                } catch (BookNotFoundException ex) {
                    result.append(ex.getMessage()).append("\n");
                    ex.printStackTrace();
                } catch (ReaderNotFoundException ex) {
                    result.append(ex.getMessage()).append("\n");
                    ex.printStackTrace();
                } catch (Exception ex) {
                    result.append(ex.getMessage()).append("\n");
                    ex.printStackTrace();
                }

                resultTextArea.setText(result.toString());

                libraryService.saveDataToCSV();
            }
        });
        frame.add(performActionButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));

        inputPanel.add(new JLabel("Author ID:"));
        JTextField authorIdField = new JTextField();
        inputPanel.add(authorIdField);

        inputPanel.add(new JLabel("Author Name:"));
        JTextField authorNameField = new JTextField();
        inputPanel.add(authorNameField);

        inputPanel.add(new JLabel("Book ID:"));
        JTextField bookIdField = new JTextField();
        inputPanel.add(bookIdField);

        inputPanel.add(new JLabel("Book Title:"));
        JTextField bookTitleField = new JTextField();
        inputPanel.add(bookTitleField);

        inputPanel.add(new JLabel("Reader ID:"));
        JTextField readerIdField = new JTextField();
        inputPanel.add(readerIdField);

        inputPanel.add(new JLabel("Reader Name:"));
        JTextField readerNameField = new JTextField();
        inputPanel.add(readerNameField);

        inputPanel.add(new JLabel("Reader Email:"));
        JTextField readerEmailField = new JTextField();
        inputPanel.add(readerEmailField);

        return inputPanel;
    }
}
