package org.duyphung.vocamemo.services;


import org.duyphung.vocamemo.models.ReviewEntity;
import org.duyphung.vocamemo.models.RoleEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    void saveReview(ReviewEntity review);
}