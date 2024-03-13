import java.io.Serializable;
import java.util.ArrayList;


// Represents a user of the library.

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int nextUserID = 1;

    private int userID;
    private String name;
    private String contactInformation;
    private ArrayList<Book> borrowedBooks;

    // Constructor to initialize a User object.
    // @param name The name of the user.
    // @param contactInformation The contact information of the user.
    
    public User(String name, String contactInformation) {
        this.userID = nextUserID++;
        this.name = name;
        this.contactInformation = contactInformation;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getter methods

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    // Setter methods

    public void setName(String name) {
        this.name = name;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    // Additional methods

    
    // Adds a book to the list of borrowed books for this user.
    // @param book The book to be borrowed.
    
    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    
    // Removes a book from the list of borrowed books for this user.
    //  @param book The book to be returned.
    
    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    @Override
    public String toString() {
        return "User ID: " + userID + ", Name: " + name + ", Contact Information: " + contactInformation;
    }

    // Additional methods for displaying user information
    // Displays information about the user, including borrowed books.
    public void displayInfo() {
        System.out.println("User ID: " + getUserID());
        System.out.println("Name: " + getName());
        System.out.println("Contact Information: " + getContactInformation());
        System.out.println("Borrowed Books: ");
        for (Book book : borrowedBooks) {
            System.out.println("  - " + book.getTitle());
        }
    }
}
