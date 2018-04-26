package com.knigovert.review;

import com.knigovert.review.model.Review;
import com.knigovert.review.repository.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Володимир on 05.04.2018.
 */
@RestController
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public Map<String, String> getEndpoints(HttpServletRequest servletRequest) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("getReview",
                ServletUriComponentsBuilder.fromServletMapping(servletRequest).path("/review/{id}").build().toString());
        map.put("getReviews",
              ServletUriComponentsBuilder.fromServletMapping(servletRequest).path("/reviews/").build().toString());
        map.put("createReview",
                ServletUriComponentsBuilder.fromServletMapping(servletRequest).path("/review/").build().toString());
        map.put("updateReview",
                ServletUriComponentsBuilder.fromServletMapping(servletRequest).path("/reviewu/{id}").build().toString());
        return map;
    }

    @GetMapping("/reviews")
    public Page<Review> getReviews(@RequestParam(name = "page", defaultValue = "0") int page) {
        return reviewService.getPage(page);
    }

    @GetMapping("/review/{id}")
    public ResponseEntity<Review> getReview(@PathVariable Long id) {
        return reviewService.getReview(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/review")
    public ResponseEntity createReview(@RequestBody Review newReview, HttpServletRequest servletRequest) {
        Review created = reviewService.createReview(newReview.getBookId(), newReview.getUserId(), newReview.getRating(),newReview.getReview());
        URI uri = ServletUriComponentsBuilder.fromServletMapping(servletRequest)
                .path("/review/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/reviewu/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review updatedReview) {
        return ResponseEntity.ok(reviewService.updateReview(id, updatedReview));
    }

    @GetMapping("/configuration")
    public Map<String, String> getConfiguration(@Value("${api.page.size}") int pageSize, @Value("${review.rating.size}") int ratingSize) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Page size", String.valueOf(pageSize));
        map.put("Rating size", String.valueOf(ratingSize));
        return map;
    }
}
