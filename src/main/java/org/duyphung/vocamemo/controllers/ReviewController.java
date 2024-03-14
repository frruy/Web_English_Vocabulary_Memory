package org.duyphung.vocamemo.controllers;

import org.duyphung.vocamemo.models.ReviewEntity;
import org.duyphung.vocamemo.repositories.WordRepository;
import org.duyphung.vocamemo.services.ReviewService;
import org.duyphung.vocamemo.utils.SectionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Collections;

@RestController
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    private WordRepository wordRepository;
    public ReviewController(@Autowired ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @RequestMapping("/add-review")
    public void addReview() {
        var user = SectionHelper.getUserFromSection();
        if (user == null) return;
        ReviewEntity review = new ReviewEntity();
        review.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        review.setUser(user);
        review.setWords(Collections.singleton(wordRepository.getReferenceById(1)));
        reviewService.saveReview(review);
    }
}
