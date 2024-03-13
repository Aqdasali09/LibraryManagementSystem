import java.io.Serializable;

// Borrowrecord class
public class Borrowrecord implements Serializable {
    // Instance variables
    private User user;
    private Book book;

    // Constructor
    public Borrowrecord(User user, Book book) {
        this.user = user;
        this.book = book;
    }

    // Getter for user
    public User getUser() {
        return user;
    }

    // Getter for book
    public Book getBook() {
        return book;
    }
}
