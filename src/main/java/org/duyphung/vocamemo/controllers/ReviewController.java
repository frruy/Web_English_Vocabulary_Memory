package org.duyphung.vocamemo.controllers;

import org.duyphung.vocamemo.entities.ReviewEntity;
import org.duyphung.vocamemo.services.ReviewService;
import org.duyphung.vocamemo.services.WordService;
import org.duyphung.vocamemo.utils.SectionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    private WordService wordService;
    public ReviewController(@Autowired ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @RequestMapping("/add-review")
    public void addReview() {
        var user = SectionHelper.getUserFromSection();
        if (user == null) return;
        ReviewEntity review = new ReviewEntity();
        review.setReviewedAt(new Timestamp(System.currentTimeMillis()));
        review.setUser(user);
        review.setWords(wordService.getTopWordsPriorityToReview());
        reviewService.saveReview(review);
    }
}
