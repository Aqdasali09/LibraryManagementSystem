import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.io.*;
import java.util.ArrayList;

//the LibraryManagementSystem inherits properties of Jframe class from jawa swing package
public class LibraryManagementSystem extends JFrame
{
    //Making buttons
    private JButton addBookButton, viewBooksButton, searchBookByNameButton, searchBookByAuthorButton, searchBookByUserButton, editBooksButton, addUserButton, viewUsersButton, editUsersButton, borrowBookButton, returnBookButton,exitButton;

    //creating a library
    private Library library;

    //Constructor of LibraryManagementSystem class
    public LibraryManagementSystem()
        {
            this.library = loadLibraryFromFile();   //loading library from file

            try
                {
                    // Set the look and feel of Swing components to match the native look and feel of the operating system
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                }
            catch (Exception e) 
                {
                    //print out information about the error
                    e.printStackTrace();
                }

            setTitle("Library Management System");
            //this means that program should stop execution when window is closed.
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Creating buttons for books
            addBookButton = new JButton("Add New Book");
            viewBooksButton = new JButton("View All Books");
            editBooksButton = new JButton("Edit Books");
            borrowBookButton = new JButton("Borrow Book");
            returnBookButton = new JButton("Return a Book");
            searchBookByNameButton = new JButton("Search Books by Name");
            searchBookByAuthorButton = new JButton("Search Books by Author");
            searchBookByUserButton = new JButton("Search Books by User ID");

            // Adding action listeners to book buttons
            addBookButton.addActionListener(e -> addNewBook());
            viewBooksButton.addActionListener(e -> viewAllBooks());
            editBooksButton.addActionListener(e -> viewAllBooks());
            borrowBookButton.addActionListener(e -> borrowBook());
            returnBookButton.addActionListener(e -> returnBook());
            searchBookByNameButton.addActionListener(e -> searchBooksByName());
            searchBookByAuthorButton.addActionListener(e -> searchBooksByAuthor());
            searchBookByUserButton.addActionListener(e -> searchBooksByUser());

            // Creating buttons for users
            addUserButton = new JButton("Add New User");
            viewUsersButton = new JButton("View All Users");
            editUsersButton = new JButton("Edit Users");

            // Adding action listeners to user buttons
            addUserButton.addActionListener(e -> addNewUser());
            viewUsersButton.addActionListener(e -> viewAllUsers());
            editUsersButton.addActionListener(e -> viewAllUsers());

            // Creating panel
            JPanel panel = new JPanel(new GridLayout(6, 16, 20, 20));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            panel.setBackground(new Color(240, 240, 240)); // Light gray background

            // Add buttons with updated styles
            addStyledButton(panel, addBookButton, new Color(50, 153, 187)); // Blue
            addStyledButton(panel, addUserButton, new Color(87, 166, 74)); // Green
            addStyledButton(panel, viewBooksButton, new Color(238, 130, 238)); // Violet
            addStyledButton(panel, viewUsersButton, new Color(255, 165, 0)); // Orange
            addStyledButton(panel, editBooksButton, new Color(70, 130, 180)); // Steel blue
            addStyledButton(panel, editUsersButton, new Color(255, 99, 71)); // Tomato
            addStyledButton(panel, borrowBookButton, new Color(154, 205, 50)); // Yellow green
            addStyledButton(panel, returnBookButton, new Color(255, 69, 0)); // Red orange
            addStyledButton(panel, searchBookByNameButton, new Color(255, 182, 193)); // Light pink
            addStyledButton(panel, searchBookByAuthorButton, new Color(173, 216, 230)); // Light blue
            addStyledButton(panel, searchBookByUserButton, new Color(255, 228, 196)); // Bisque

            // Add the panel containing buttons to the frame
            add(panel);
            // Resize the frame to fit its contents
            pack();
            // Center the frame on the screen
            setLocationRelativeTo(null);
            // Make the frame visible to the user
            setVisible(true);
        }

    //Function to add styles in the program
    private void addStyledButton(JPanel panel, JButton button, Color color) 
        {
            button.setBackground(color);
            button.setForeground(Color.BLACK);
            button.setFocusPainted(false);
            button.setFont(new Font("Arial", Font.PLAIN, 16));
            panel.add(button);
        }

    //Function to search book by name of the book
    private void searchBooksByName()
        {
            //showing window to take input of book name
            String title = JOptionPane.showInputDialog(this, "Enter book name to search:");
            //storing lists in an arraylist
            ArrayList<Book> result = library.searchBooksByTitle(title);
            //displaying the search results using table
            displaySearchResults(result, "Search Results for Books by Name: " + title);
        }

    //Function to search book by author of the book
    private void searchBooksByAuthor() 
        {
            //showing window to take input of author name
            String author = JOptionPane.showInputDialog(this, "Enter author name to search:");
            //storing lists in an arraylist
            ArrayList<Book> result = library.searchBooksByAuthor(author);
            //displaying the search results using table
            displaySearchResults(result, "Search Results for Books by Author: " + author);
        }

    //Function to search books borrowed by a user
    private void searchBooksByUser() 
        {
            //Selecting a user to view books borrowed by them
            User selectedUser = selectUser();
            //if user is selected
            if (selectedUser != null) 
            {
                //using getter to store the borrowedbooks arraylist of the user class
                ArrayList<Book> result = library.getBorrowedBooks(selectedUser);
                //Displaying the results using table
                displaySearchResults(result, "Books Borrowed by User: " + selectedUser.getName());
            }
        }

    //Function to borrow Book
    private void borrowBook() 
        {
            //Selecting a user
            User selectedUser = selectUser();
                //Program continues when user is selected
                if (selectedUser != null) 
                {
                    //Selecting a book from all books. The second parameter is a boolean value. If it is false then book is being borrowed and if it is true then book is being returned
                    Book selectedBook;
                do {
                    selectedBook = borrowOrReturnBook(selectedUser, false);
                    if(!selectedBook.isAvailable()){
                        JOptionPane.showMessageDialog(this, "Book not Available!");
                    }
                } while (selectedBook == null || !selectedBook.isAvailable());

                    //Program continues when book is selected
                    if (selectedBook != null) 
                    {
                        //This method checks out book 
                        library.checkOutBook(selectedUser, selectedBook);
            
                        // Save library to file after borrowing a book
                        saveLibraryToFile(library);
                    }
                }
        }
    
    //Function to return a book
    private void returnBook() 
    {
        //Selecting a User
        User selectedUser = selectUser();
    
        if (selectedUser != null) 
        {
            //Selecting a Book and returning it
            Book selectedBook = borrowOrReturnBook(selectedUser, true);
    
            if (selectedBook != null) 
            {
                //This method returns book 
                library.returnBook(selectedUser, selectedBook);
    
                // Save library to file after returning a book
                saveLibraryToFile(library);
            }
        }
    }

    // This method prompts the user to select a book for borrowing or returning, depending on the isReturning parameter.
private Book borrowOrReturnBook(User user, boolean isReturning) 
{
    // Retrieves the list of books either borrowed by the user or available in the library based on the isReturning parameter.
    List<Book> bookList = isReturning ? library.getBorrowedBooks(user) : library.getBooks();
    
    // Checks if the retrieved list of books is empty.
    if (bookList.isEmpty()) 
    {
        // Displays a message if no books are borrowed by the user or if no books are available.
        JOptionPane.showMessageDialog(this, isReturning ? "No books borrowed by this user." : "No books available.");
        
        // Returns null indicating no book is selected.
        return null;
    }
    
    // Converts the list of books into an array of Object.
    Object[] bookOptions = bookList.toArray();
    
    // Displays a dialog box to prompt the user to select a book.
    Book selectedBook = (Book) JOptionPane.showInputDialog(
            this,
            "Select a book:",
            "Book Selection",
            JOptionPane.QUESTION_MESSAGE,
            null,
            bookOptions,
            bookOptions[0]);
    
    // Returns the selected book.
    return selectedBook;
}


    

   // This method displays the search results in a new frame.
private void displaySearchResults(ArrayList<Book> result, String title) 
{
    // Checks if the search result is empty.
    if (result.isEmpty()) 
    {
        // Displays a message if no matching books are found.
        JOptionPane.showMessageDialog(this, "No matching books found.");
    } 
    else 
    {
        // Creates a new frame to display the search results.
        JFrame resultFrame = new JFrame(title);
        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Creating table for search results
        JTable resultTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(resultTable);

        // Setting up table model for search results
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Book ID");
        tableModel.addColumn("Title");
        tableModel.addColumn("Author");
        tableModel.addColumn("Genre");
        tableModel.addColumn("Availability");

        resultTable.setModel(tableModel);

        // Populate the table with search results
        for (Book book : result) 
        {
            // Adds each book's information to the table
            tableModel.addRow(new Object[]{book.getBookID(), book.getTitle(), book.getAuthor(), book.getGenre(), book.isAvailable() ? "Available" : "Not Available"});
        }

        // Adds the table to the frame
        resultFrame.add(scrollPane);
        resultFrame.setSize(600, 400);
        resultFrame.setLocationRelativeTo(this);
        resultFrame.setVisible(true);
    }
}




// This method allows the user to select a user from the list of users in the library.
private User selectUser() 
{
    // Retrieves the list of users from the library
    List<User> userList = library.getUsers();
    
    // Checks if the user list is empty
    if (userList.isEmpty()) 
    {
        // Displays a message if there are no users available
        JOptionPane.showMessageDialog(this, "No users available.");
        return null;
    }

    // Converts the list of users to an array of objects
    Object[] userOptions = userList.toArray();
    
    // Displays a dialog box for user selection
    User selectedUser = (User) JOptionPane.showInputDialog(
            this,
            "Select a user:",
            "User Selection",
            JOptionPane.QUESTION_MESSAGE,
            null,
            userOptions,
            userOptions[0]);

    return selectedUser;
}



    private void addNewBook() 
    {
        try 
        {
            String title;
            do {
                title = JOptionPane.showInputDialog(this, "Enter Book Title:");
                if(title.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Enter a title to continue!");
                }
            } while (title.isEmpty());
            
            String author;
            do {
                author = JOptionPane.showInputDialog(this, "Enter Author:");
                if(author.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Enter a author to continue!");
                }
            } while (author.isEmpty());
            
            String genre;
            do {
                genre = JOptionPane.showInputDialog(this, "Enter Genre:");
                if(genre.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Enter a genre to continue!");
                }
            } while (genre.isEmpty());
            
            
            

            // Create a new Book instance
            Book newBook = new Book(title, author, genre);

            // Add the book to the library
            library.addBook(newBook);

            JOptionPane.showMessageDialog(this, "Book added successfully!");
            saveLibraryToFile(library);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while adding the book. Please try again.");
        }
    }

   // Method to display all books in a JFrame
private void viewAllBooks() 
{
    // Create a new JFrame to display all books
    JFrame viewFrame = new JFrame("All Books");
    
    // Set the default close operation for the JFrame
    viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // Creating table to display books
    JTable bookTable = new JTable();
    JScrollPane scrollPane = new JScrollPane(bookTable);

    // Setting up table model for books
    DefaultTableModel tableModel = new DefaultTableModel() 
    {
        @Override
        public boolean isCellEditable(int row, int column) 
        {
            return true; // Allow all cells to be editable
        }
    };

    // Add columns to the table model
    tableModel.addColumn("Book ID");
    tableModel.addColumn("Title");
    tableModel.addColumn("Author");
    tableModel.addColumn("Genre");
    tableModel.addColumn("Availability");

    // Set the table model to the book table
    bookTable.setModel(tableModel);

    // Populate the table with book data
    for (Book book : library.getBooks()) 
    {
        tableModel.addRow(new Object[]{book.getBookID(), book.getTitle(), book.getAuthor(), book.getGenre(), book.isAvailable() ? "Available" : "Not Available"});
    }

    // Adding a cell editor to update the underlying data when a cell is edited
    bookTable.getModel().addTableModelListener(e -> 
    {
        int row = e.getFirstRow();
        int column = e.getColumn();
        DefaultTableModel model = (DefaultTableModel) e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);

        // Update the Book instance in the library
        Book book = library.getBooks().get(row);
        updateBookField(book, columnName, data);

        // Save the updated library to the file
        saveLibraryToFile(library);
    });

    // Add scroll pane with book table to the view frame
    viewFrame.add(scrollPane);
    // Set the size of the view frame
    viewFrame.setSize(600, 400);
    // Set the location of the view frame relative to this JFrame
    viewFrame.setLocationRelativeTo(this);
    // Set the view frame as visible
    viewFrame.setVisible(true);
}
// Method to update a specific field of a Book object
private void updateBookField(Book book, String columnName, Object data) 
{
    // Switch statement to determine which field to update based on the column name
    switch (columnName) 
    {
        case "Title":
            // Set the title of the book to the new data
            book.setTitle((String) data);
            break;
        case "Author":
            // Set the author of the book to the new data
            book.setAuthor((String) data);
            break;
        case "Genre":
            // Set the genre of the book to the new data
            book.setGenre((String) data);
            break;
        case "Availability":
            // Set the availability of the book based on the new data
            book.setAvailable(((String) data).equalsIgnoreCase("Available"));
            break;
        default:
            // No action needed if column name does not match any case
            break;
    }
}

// Method to add a new user to the library
private void addNewUser() 
{
    try 
    {
        // Prompt the user to enter name and contact information
        String name;
        do {
            name = JOptionPane.showInputDialog(this, "Enter User Name:");
            if(name.isEmpty()){
                JOptionPane.showMessageDialog(this, "Enter a name to conitnue");
            }
        } while (name == null || name.isEmpty());
        
        String contactInfo;
        do {
            contactInfo = JOptionPane.showInputDialog(this, "Enter Contact Information:");
            if(contactInfo.isEmpty()){
                JOptionPane.showMessageDialog(this, "Enter a Contact to conitnue");
            }
        } while (contactInfo == null || contactInfo.isEmpty());
        

        // Create a new User instance with the provided name and contact information
        User newUser = new User(name, contactInfo);

        // Add the new user to the library
        library.addUser(newUser);

        // Display a success message
        JOptionPane.showMessageDialog(this, "User added successfully!");

        // Save the updated library to the file
        saveLibraryToFile(library);
    } 
    catch (Exception e) 
    {
        // Print the stack trace if an exception occurs
        e.printStackTrace();
        
        // Display an error message to the user
        JOptionPane.showMessageDialog(this, "An error occurred while adding the user. Please try again.");
    }
}

   // Method to display all users in a table
private void viewAllUsers() 
{
    // Create a new frame to display user information
    JFrame viewFrame = new JFrame("All Users");
    // Set the default close operation for the frame
    viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // Creating a table to display user information
    JTable userTable = new JTable();
    JScrollPane scrollPane = new JScrollPane(userTable);

    // Setting up the table model for users
    DefaultTableModel tableModel = new DefaultTableModel() 
    {
        // Allow all cells to be editable
        @Override
        public boolean isCellEditable(int row, int column) 
        {
            return true;
        }
    };

    // Add columns to the table model
    tableModel.addColumn("User ID");
    tableModel.addColumn("Name");
    tableModel.addColumn("Contact Information");
    tableModel.addColumn("Borrowed Books");

    // Set the table model for the user table
    userTable.setModel(tableModel);

    // Populate the table with user data
    for (User user : library.getUsers()) 
    {
        // Get the list of borrowed books for each user
        List<Book> borrowedBooks = library.getBorrowedBooks(user);

        // Create a string representation of borrowed books
        String borrowedBooksString = borrowedBooks.isEmpty() ? "None" : String.join(", ", borrowedBooks.stream().map(Book::getTitle).toArray(String[]::new));

        // Add the user to the table
        tableModel.addRow(new Object[]{user.getUserID(), user.getName(), user.getContactInformation(), borrowedBooksString});
    }

    // Adding a cell editor to update the underlying data when a cell is edited
    userTable.getModel().addTableModelListener(e -> 
    {
        int row = e.getFirstRow();
        int column = e.getColumn();
        DefaultTableModel model = (DefaultTableModel) e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);

        // Update the User instance in the library
        User user = library.getUsers().get(row);
        updateUserField(user, columnName, data);

        // Save the updated library to the file
        saveLibraryToFile(library);
    });

    // Add the table to the frame
    viewFrame.add(scrollPane);
    // Set the size of the frame
    viewFrame.setSize(500, 300);
    // Center the frame on the screen
    viewFrame.setLocationRelativeTo(this);
    // Make the frame visible
    viewFrame.setVisible(true);
}


  // Method to update the fields of a user object
private void updateUserField(User user, String columnName, Object data) 
{
    // Switch statement to determine which field to update based on the column name
    switch (columnName) {
        // If the column name is "Name", update the name of the user
        case "Name":
            user.setName((String) data);
            break;
        // If the column name is "Contact Information", update the contact information of the user
        case "Contact Information":
            user.setContactInformation((String) data);
            break;
        // Default case, do nothing
        default:
            break;
    }
}


   // Method to load library data from a file
private Library loadLibraryFromFile() 
{
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("library_data.ser"))) 
    {
        // Read and return the library object from the file
        return (Library) ois.readObject();
    } 
    // If the file is not found, print a message and create a new library
    catch (FileNotFoundException e) 
    {
        System.out.println("Library data file not found. Creating a new library.");
    } 
    // Handle IO and class not found exceptions
    catch (IOException | ClassNotFoundException e) 
    {
        // Print the stack trace for the exception
        e.printStackTrace();
    }

    // Return a new library if loading fails
    return new Library();
}


  // Method to save library data to a file
public static void saveLibraryToFile(Library library) 
{
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("library_data.ser"))) 
    {
        // Write the library object to the file
        oos.writeObject(library);
    } 
    // Handle IO exception
    catch (IOException e) 
    {
        // Print the stack trace for the exception
        e.printStackTrace();
    }
}

    public static void main(String[] args) {
        LibraryManagementSystem Ali_library=new LibraryManagementSystem();
    }
}
