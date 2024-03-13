import java.io.*;
import java.util.ArrayList;
public class Library implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Book> books;
    private ArrayList<User> users;
    private ArrayList<ArrayList<Book>> borrowedBooks; // ArrayList to store borrowed books for each user

    public Library() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
        this.borrowedBooks = new ArrayList<>();
    
        // Initialize borrowedBooks for each user
        for (int i = 0; i < users.size(); i++) {
            borrowedBooks.add(new ArrayList<>());
        }
    }

    public ArrayList<Book> getBorrowedBooks(User user) {
        int userIndex = users.indexOf(user);
        if (userIndex != -1) {
            return borrowedBooks.get(userIndex);
        }
        return new ArrayList<>();
    }
    public void updateBorrowedBooks(User user, ArrayList<Book> borrowedBooks) {
        int userIndex = users.indexOf(user);
        if (userIndex != -1) {
            this.borrowedBooks.set(userIndex, borrowedBooks);
        }
    }


    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addBook(Book book) {
        books.add(book);
    }
    

    public void addUser(User user) {
        users.add(user);
        borrowedBooks.add(new ArrayList<>());
    }

    public void checkOutBook(User user, Book book) {
        if (book.isAvailable())
         {
            book.setAvailable(false);
            user.borrowBook(book);
    
            // Update the borrowed books for the user
            updateBorrowedBooks(user, user.getBorrowedBooks());
    
            // Save the library after borrowing a book
            LibraryManagementSystem.saveLibraryToFile(this);
        } 
        else 
        {
            System.out.println("The book is not available for checkout.");
        }
    }



    public void returnBook(User user, Book book) 
    {
        book.setAvailable(true);
        user.returnBook(book);
    
        // Update the borrowed books for the user
        updateBorrowedBooks(user, user.getBorrowedBooks());
    
        // Save the library after returning a book
        LibraryManagementSystem.saveLibraryToFile(this);
    }



  
     // Searches for books with a specified title.
    public ArrayList<Book> searchBooksByTitle(String title) 
    {
        // Initialize an ArrayList to store the search results
        ArrayList<Book> result = new ArrayList<>();
        
        // Iterate through the list of books in the library
        for (Book book : books) 
        {
            // Check if the title of the current book matches the provided title (case-insensitive)
            if (book.getTitle().equalsIgnoreCase(title)) 
            {
                // If a match is found, add the book to the search result ArrayList
                result.add(book);
            }
        }
        
        // Return the list of books with titles matching the provided title
        return result;
    }


 
    //  Searches for a user in the library by their ID.
     
    public User searchUserByID(int userID) {
        // Iterate through the list of users in the library
        for (User user : users) {
            // Check if the ID of the current user matches the provided userID
            if (user.getUserID() == userID) {
                // If a match is found, return the user
                return user;
            }
        }
        // If no user with the provided ID is found, return null
        return null;
    }

    

      
    //  Searches for books in the library by author.
     
    public ArrayList<Book> searchBooksByAuthor(String author) {
        // Create an ArrayList to store the search results
        ArrayList<Book> result = new ArrayList<>();
        
        // Iterate through the list of books in the library
        for (Book book : books) {
            // Check if the author of the current book matches the provided author (ignoring case)
            if (book.getAuthor().equalsIgnoreCase(author)) {
                // If a match is found, add the book to the search results
                result.add(book);
            }
        }
        
        // Return the ArrayList containing the search results
        return result;
    }

}
