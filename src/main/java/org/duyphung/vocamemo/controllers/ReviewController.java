package org.duyphung.vocamemo.controllers;

import com.google.gson.Gson;
import org.duyphung.vocamemo.entities.ReviewEntity;
import org.duyphung.vocamemo.entities.WordEntity;
import org.duyphung.vocamemo.entities.WordUser;
import org.duyphung.vocamemo.services.ReviewService;
import org.duyphung.vocamemo.services.WordService;
import org.duyphung.vocamemo.utils.SectionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    private WordService wordService;
    public ReviewController(@Autowired ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @RequestMapping("/review")
    public String setupReview(Model model) {
        var user = SectionHelper.getUserFromSection();
        if (user == null) return "login";
        ReviewEntity review = new ReviewEntity();
        review.setReviewedAt(new Timestamp(System.currentTimeMillis()));
        var words = wordService.getTopWordsPriorityToReview();
        review.setWords(words);
        review.setWordUserSet(getWordUserListFromWords(words));
//        reviewService.saveReview(review);
        model.addAttribute("words", words);
        return "review";
    }

    private List<WordUser> getWordUserListFromWords(List<WordEntity> words) {
        return wordService.getWordUserByWords(words);
    }

    @PostMapping("/review/done")
    public String handleReviewDone(@RequestParam("wordData") String wordTextsString) {
        String[] wordTexts = wordTextsString.split(",");

        List<WordEntity> wordEntities = wordService.getWordsByTexts(List.of(wordTexts));
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setReviewedAt(new Timestamp(System.currentTimeMillis()));
        List<WordUser> wordUsers = wordService.getWordUserByWords(wordEntities);
        reviewEntity.setWordUserSet(wordUsers);
        wordService.updateReviewTime(wordEntities);
        reviewService.saveReview(reviewEntity);
        return "redirect:/home";
    }
}
