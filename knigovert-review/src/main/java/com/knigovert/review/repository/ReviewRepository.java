package com.knigovert.review.repository;

import com.knigovert.review.model.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Володимир on 05.04.2018.
 */

public interface ReviewRepository extends PagingAndSortingRepository<Review, Long> {
}
