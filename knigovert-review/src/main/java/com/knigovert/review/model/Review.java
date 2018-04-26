package com.knigovert.review.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Володимир on 05.04.2018.
 */
@Entity
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    private Long bookId;

    private Long userId;

    //private String showId;


    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Review() {

    }

    public Review(Long bookId, Long userId, int rating, String review) {
        this.bookId = bookId;
        this.userId = userId;
        this.review = review;
        this.rating = rating;
    }

    @Column(length = 2000)
    private String review;

    private int rating;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
