package org.duyphung.vocamemo.services;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.duyphung.vocamemo.models.ReviewEntity;
import org.duyphung.vocamemo.models.WordEntity;
import org.duyphung.vocamemo.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(@Autowired ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    @Transactional
    public void saveReview(ReviewEntity review) {
        reviewRepository.save(review);
    }
}
