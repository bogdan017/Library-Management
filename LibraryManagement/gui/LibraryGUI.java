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

        // Create and set up the main frame
        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Panel for input fields
        JPanel inputPanel = createInputPanel();
        frame.add(inputPanel, BorderLayout.NORTH);

        // Text area to display results
        JTextArea resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Button to perform actions
        JButton performActionButton = new JButton("Perform Action");
        performActionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform actions and display results
                StringBuilder result = new StringBuilder();
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

                    result.append("Books by Title: ").append(booksByTitle.size()).append("\n");
                    result.append("Books by Author: ").append(booksByAuthor.size()).append("\n");
                    result.append("Books by Section: ").append(booksBySection.size()).append("\n");
                    result.append("Overdue Books: ").append(overdueBooks.size()).append("\n");
                    result.append("Books by Genre: ").append(booksByGenre.size()).append("\n");

                    // List publishers
                    List<Publisher> publishers = libraryService.listAllPublishers();
                    result.append("Publishers: ").append(publishers.size()).append("\n");

                    // List librarians
                    List<Librarian> librarians = libraryService.listAllLibrarians();
                    result.append("Librarians: ").append(librarians.size()).append("\n");

                    // Assign librarian to section
                    libraryService.assignLibrarianToSection("1", "1");

                } catch (BookNotFoundException ex) {
                    // Handle BookNotFoundException
                    result.append(ex.getMessage()).append("\n");
                    ex.printStackTrace();
                } catch (ReaderNotFoundException ex) {
                    // Handle ReaderNotFoundException
                    result.append(ex.getMessage()).append("\n");
                    ex.printStackTrace();
                } catch (Exception ex) {
                    // Handle other exceptions
                    result.append(ex.getMessage()).append("\n");
                    ex.printStackTrace();
                }

                // Display results in the text area
                resultTextArea.setText(result.toString());

                // Save data to CSV on exit
                libraryService.saveDataToCSV();
            }
        });
        frame.add(performActionButton, BorderLayout.SOUTH);

        // Display the frame
        frame.setVisible(true);
    }
    private static JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));

        // Add labels and text fields for input
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
