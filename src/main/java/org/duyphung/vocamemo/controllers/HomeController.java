package org.duyphung.vocamemo.controllers;

import org.duyphung.vocamemo.models.WordEntity;
import org.duyphung.vocamemo.reponses.WordResponse;
import org.duyphung.vocamemo.repositories.WordRepository;
import org.duyphung.vocamemo.services.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
public class HomeController {

    private final DictionaryService dictionaryService;

    @Autowired
    private WordRepository wordRepository;

    public HomeController(@Autowired DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @RequestMapping("/home")
    public String getHomePage(Model model) {
        Set<WordEntity> words = dictionaryService.getWords();
        model.addAttribute("words", words);
        return "home";
    }

    @GetMapping("/search/{word}")
    public WordResponse searchWord(@PathVariable String word) {
        WordResponse response = dictionaryService.getWordResponse(word);
        dictionaryService.saveWord(response);
        return response;
    }

    @GetMapping("/test")
    public String getTest(Model model) {
        model.addAttribute("selectedWord", wordRepository.findById(1));
        return "home";
    }
}