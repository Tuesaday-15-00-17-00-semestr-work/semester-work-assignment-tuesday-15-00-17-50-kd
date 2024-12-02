package com.virtuallibrary.springbootapi.service;

import com.virtuallibrary.springbootapi.api.model.Transaction;
import com.virtuallibrary.springbootapi.api.model.User;
import com.virtuallibrary.springbootapi.api.model.Book;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {
    private final String jdbcUrl = "jdbc:sqlite:/C:\\Users\\Admin\\JavaProjects\\virtuallibrary.db";
    private Connection connection;
    private Statement statement;

    public DatabaseService() {
        try {
            this.connection = DriverManager.getConnection(jdbcUrl);
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Cannot connect to database.");
            throw new RuntimeException(e);
        }
    }

    public List<Book> getAllBooks() throws SQLException {
        String sql = "SELECT * FROM BOOKS";
        ResultSet result = statement.executeQuery(sql);
        List<Book> books = new ArrayList<>();

        while (result.next()) {
            int bookId = result.getInt("book_id");
            String title = result.getString("title");
            String author = result.getString("author");
            String isbnNumber = result.getString("isbn_number");
            int availableCopies = result.getInt("available_copies");

            books.add(new Book(bookId, title, author, isbnNumber, availableCopies));
        }

        return books;
    }

    public Book getBookById(int id) throws SQLException {
        String sql = "SELECT * FROM BOOKS WHERE book_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                int bookId = result.getInt("book_id");
                String title = result.getString("title");
                String author = result.getString("author");
                String isbnNumber = result.getString("isbn_number");
                int availableCopies = result.getInt("available_copies");

                return new Book(bookId, title, author, isbnNumber, availableCopies);
            } else {
                return null;
            }
        }
    }

    public Book getBookByIsbn(String isbnNumber) throws SQLException {
        String sql = "SELECT * FROM BOOKS WHERE isbn_number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, isbnNumber);
            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                int bookId = result.getInt("book_id");
                String title = result.getString("title");
                String author = result.getString("author");
                int availableCopies = result.getInt("available_copies");

                return new Book(bookId, title, author, isbnNumber, availableCopies);
            } else {
                return null;
            }
        }
    }

    public Book createBook(Book book) throws SQLException {
        String sql = "INSERT INTO BOOKS (title, author, isbn_number, available_copies) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getIsbnNumber());
            preparedStatement.setInt(4, book.getAvailableCopies());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    book.setBookId(generatedKeys.getInt(1));
                }
                return book;
            } else {
                return null;
            }
        }
    }

    public boolean updateBook(Book book) throws SQLException {
        if (!isBookExist(book.getBookId())) {
            return false;
        }

        String sql = "UPDATE BOOKS SET title = ?, author = ?, isbn_number = ?, available_copies = ? WHERE book_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getIsbnNumber());
            preparedStatement.setInt(4, book.getAvailableCopies());
            preparedStatement.setInt(5, book.getBookId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean deleteBook(int bookId) throws SQLException {
        if (!isBookExist(bookId)) {
            return false;
        }

        String sql = "DELETE FROM BOOKS WHERE book_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bookId);
            preparedStatement.executeUpdate();
            return true;
        }
    }

    private boolean isBookExist(int id) throws SQLException {
        String sql = "SELECT * FROM BOOKS WHERE book_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            return result.next();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM USERS";

        ResultSet result = statement.executeQuery(sql);
        List<User> users = new ArrayList<>();

        while (result.next()) {
            int userId = result.getInt("user_id");
            String username = result.getString("username");
            int roleId = result.getInt("role_id");
            String email = result.getString("email");
            String password = result.getString("password");

            users.add(new User(userId, username, roleId, email, password));
        }

        return users;
    }

    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                int userId = result.getInt("user_id");
                String username = result.getString("username");
                int roleId = result.getInt("role_id");
                String email = result.getString("email");
                String password = result.getString("password");

                return new User(userId, username, roleId, email, password);
            } else {
                return null;
            }
        }
    }

    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE username like ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);

            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                int userId = result.getInt("user_id");
                username = result.getString("username");
                int roleId = result.getInt("role_id");
                String email = result.getString("email");
                String password = result.getString("password");

                return new User(userId, username, roleId, email, password);
            } else {
                return null;
            }
        }
    }

    public User createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, role_id, email, password) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setInt(2, user.getRoleId());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                user = getUserByUsername(user.getUsername());
                return user;
            } else {
                return null;
            }
        }
    }

    public boolean updateUser(User user) throws SQLException {
        if (!isUserExist(user.getUserId())) {
            return false;
        }

        String sql = "UPDATE users SET username = ?, role_id = ?, email = ?, password = ? WHERE user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setInt(2, user.getRoleId());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, user.getUserId());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        }
    }

    public boolean deleteUser(int userId) throws SQLException {
        if (!isUserExist(userId)) {
            return false;
        }

        String sql = "DELETE FROM USERS WHERE user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        }

        return true;
    }

    private boolean isUserExist(int id) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE user_id = " + id;

        ResultSet result = statement.executeQuery(sql);

        return result.next();
    }

    public boolean takeBook(int userId, int bookId) throws SQLException {
        String sql = "INSERT INTO transactions (user_id, book_id, action, date) VALUES (?, ?, ?, ?)";

        Book book = getBookById(bookId);
        if (book == null || book.getAvailableCopies() <= 0) {
            System.out.println("Book is not available for borrowing.");
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, bookId);
            preparedStatement.setString(3, "borrowed");
            preparedStatement.setString(4, getCurrentDateTime());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                updateBookCopies(bookId, book.getAvailableCopies() - 1);
                return true;
            }
            return false;
        }
    }

    public boolean returnBook(int userId, int bookId) throws SQLException {
        String sql = "DELETE FROM transactions WHERE user_id=? and book_id=?";

        Book book = getBookById(bookId);
        if (book == null) {
            System.out.println("Book not found.");
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, bookId);
/*            preparedStatement.setString(3, "returned");
            preparedStatement.setString(4, getCurrentDateTime());*/

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                updateBookCopies(bookId, book.getAvailableCopies() + 1);
                return true;
            }
            return false;
        }
    }

    public List<Transaction> getTransactionsByUser(int userId) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE user_id = ?";
        List<Transaction> transactions = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                int transactionId = result.getInt("transaction_id");
                int bookId = result.getInt("book_id");
                String action = result.getString("action");
                String date = result.getString("date");

                transactions.add(new Transaction(transactionId, userId, bookId, action, date));
            }
        }

        return transactions;
    }

    public List<Transaction> getTransactionsByBook(int bookId) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE book_id = ?";
        List<Transaction> transactions = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bookId);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                int transactionId = result.getInt("transaction_id");
                int userId = result.getInt("user_id");
                String action = result.getString("action");
                String date = result.getString("date");

                transactions.add(new Transaction(transactionId, userId, bookId, action, date));
            }
        }

        return transactions;
    }

    private String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private void updateBookCopies(int bookId, int newAvailableCopies) throws SQLException {
        String sql = "UPDATE BOOKS SET available_copies = ? WHERE book_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, newAvailableCopies);
            preparedStatement.setInt(2, bookId);
            preparedStatement.executeUpdate();
        }
    }
}