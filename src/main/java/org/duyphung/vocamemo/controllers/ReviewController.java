package org.duyphung.vocamemo.controllers;

import org.duyphung.vocamemo.models.ReviewEntity;
import org.duyphung.vocamemo.repositories.WordRepository;
import org.duyphung.vocamemo.sercurity.UserPrincipal;
import org.duyphung.vocamemo.services.DictionaryService;
import org.duyphung.vocamemo.services.ReviewService;
import org.duyphung.vocamemo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    public ReviewController(@Autowired ReviewService reviewService,  @Autowired UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @RequestMapping("/add-review")
    public void addReview() {
        // Get the authentication object from the SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            // Get the principal, which typically is the UserDetails object representing the authenticated user
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserPrincipal userPrincipal) {

                // Now you can access user information
                String username = userPrincipal.getUsername();
                // Other user details such as authorities, etc.
                ReviewEntity review = new ReviewEntity();
                review.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                review.setUser(userPrincipal.getUser());
                reviewService.saveReview(review);
            }
        }
    }
}
