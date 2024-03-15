package org.duyphung.vocamemo.services;


import org.duyphung.vocamemo.entities.ReviewEntity;
import org.springframework.stereotype.Service;

@Service
public interface ReviewService {
    void saveReview(ReviewEntity review);
}