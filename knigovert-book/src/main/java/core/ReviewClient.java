package core;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("review-service")
public interface ReviewClient {
    @GetMapping("/reviews")
    List<Review> getReviews();
}
