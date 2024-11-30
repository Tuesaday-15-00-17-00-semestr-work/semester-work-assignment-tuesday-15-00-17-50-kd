package com.virtuallibrary.springbootapi.api.controller;

import com.virtuallibrary.springbootapi.api.model.Transaction;
import com.virtuallibrary.springbootapi.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController() {
        transactionService = new TransactionService();
    }

    @PostMapping("/book/borrow")
    public ResponseEntity<Transaction> takeBookTransaction(@RequestParam int userId, @RequestParam int bookId) throws SQLException {
        boolean createdTransaction = transactionService.takeBook(userId, bookId);

        if (createdTransaction) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/book/return")
    public ResponseEntity<Transaction> returnBooksTransaction(@RequestParam int userId, @RequestParam int bookId) throws SQLException {
        boolean createdTransaction = transactionService.returnBook(userId, bookId);

        if (createdTransaction) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUserId(@PathVariable Integer userId) throws SQLException {
        List<Transaction> userTransactions = transactionService.getTransactionsByUser(userId);
        if (userTransactions == null || userTransactions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userTransactions, HttpStatus.OK);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Transaction>> getTransactionsByBookId(@PathVariable Integer bookId) throws SQLException {
        List<Transaction> bookTransactions = transactionService.getTransactionsByBook(bookId);
        if (bookTransactions == null || bookTransactions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookTransactions, HttpStatus.OK);
    }
}
