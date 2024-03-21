package org.duyphung.vocamemo.controllers;

import org.duyphung.vocamemo.entities.WordEntity;
import org.duyphung.vocamemo.services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling home page and word-related requests.
 */
@Controller
public class HomeController {

    private final WordService wordService;

    public HomeController(@Autowired WordService wordService) {
        this.wordService = wordService;
    }

    @RequestMapping("/home")
    public String getHomePage(Model model) {
        List<WordEntity> words = wordService.getWordsByUserOrderedBySearchTime();
        model.addAttribute("words", words);
        return "home";
    }

    @GetMapping("/search/{word}")
    @ResponseBody
    public String searchWord(@PathVariable String word) {
        WordEntity wordEntity = wordService.getOrCreateWordEntityAndUpdateTime(word);
        return wordEntity.toJson();
    }

    @RequestMapping("/words/highlight/{wordId}")
    public ResponseEntity<Void> changeWordHighlightStatus(@PathVariable int wordId, @RequestParam("isHighlight") boolean isHighlight) {
        wordService.changeWordHighlightStatus(wordId, isHighlight);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{wordId}")
    public ResponseEntity<Void> deleteWordUser(@PathVariable int wordId) {
        wordService.deleteWordUserByWordId(wordId);
        return ResponseEntity.ok().build();
    }
}