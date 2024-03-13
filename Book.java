import java.io.*;

// Book class
class Book implements Serializable {
    // Serial version UID for serialization
    private static final long serialVersionUID = 1L;

    // Static variable to generate unique book IDs
    private static int nextID = 1;

    // Instance variables
    private int bookID;
    private String title;
    private String author;
    private String genre;
    private boolean available;

    // Constructor
    public Book(String title, String author, String genre) {
        // Initialize book ID and increment the static nextID for the next book
        this.bookID = nextID++;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.available = true; // Set availability to true initially
    }

    // Override toString method to provide a string representation of the book
    @Override
    public String toString() {
        return "Book ID: " + bookID + ", Title: " + title + ", Author: " + author + ", Genre: " + genre + ", Availability: " + (available ? "Available" : "Not Available");
    }

    // Getters and setters for instance variables
    public int getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getGenre() {
        return genre;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
