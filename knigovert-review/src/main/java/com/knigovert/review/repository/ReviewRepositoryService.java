package com.knigovert.review.repository;

import com.knigovert.review.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Володимир on 05.04.2018.
 */
@Service
public class ReviewRepositoryService implements ReviewService {
    private int pageSize;
    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewRepositoryService(ReviewRepository reviewRepository, @Value("${api.page.size: #{10}}") int pageSize) {
        this.reviewRepository = reviewRepository;
        this.pageSize = pageSize;
    }

    @Override
    public List<Review> getList() {
        List<Review> list = new ArrayList<>();
        reviewRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public Page<Review> getPage(int page) {
        return reviewRepository.findAll(PageRequest.of(page, pageSize));
    }

    @Override
    public Optional<Review> getReview(Long id) {
        return reviewRepository.findById(id);
    }


    @Override
    public Review createReview(Long bookId, Long userId, int rating, String review) {
        Review reviewBook = new Review( bookId,  userId,  rating,  review);
        return reviewRepository.save(reviewBook);
    }

    @Override
    public Review updateReview(Long id, Review updatedReview) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review id: " + id));
        review.setBookId(updatedReview.getBookId());
        review.setUserId(updatedReview.getUserId());
        review.setRating(updatedReview.getRating());
        review.setReview(updatedReview.getReview());
        return reviewRepository.save(review);
    }

}
