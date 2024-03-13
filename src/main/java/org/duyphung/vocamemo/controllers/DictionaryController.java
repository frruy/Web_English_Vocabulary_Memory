package org.duyphung.vocamemo.controllers;

import org.duyphung.vocamemo.reponses.WordResponse;
import org.duyphung.vocamemo.repositories.WordRepository;
import org.duyphung.vocamemo.services.DictionaryService;
import org.duyphung.vocamemo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/search/{word}")
    public WordResponse searchWord(@PathVariable String word) {
        WordResponse response = dictionaryService.getWordResponse(word);
        dictionaryService.saveWord(response);
        return response;
    }

    @GetMapping("/find")
    public void getFind() {
        var x = dictionaryService.getWord();

        var u = 1+1;
    }
}