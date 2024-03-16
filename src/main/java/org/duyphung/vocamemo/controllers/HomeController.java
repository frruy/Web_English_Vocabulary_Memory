package org.duyphung.vocamemo.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.duyphung.vocamemo.adapters.WordEntityTypeAdapter;
import org.duyphung.vocamemo.entities.WordEntity;
import org.duyphung.vocamemo.reponses.WordResponse;
import org.duyphung.vocamemo.repositories.WordRepository;
import org.duyphung.vocamemo.services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
public class HomeController {

    private final WordService wordService;

    @Autowired
    private WordRepository wordRepository;

    public HomeController(@Autowired WordService wordService) {
        this.wordService = wordService;
    }

    @RequestMapping("/home")
    public String getHomePage(Model model) {
        Set<WordEntity> words = wordService.getWords();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(WordEntity.class, new WordEntityTypeAdapter())
                .create();
        String json = gson.toJson(words);
        model.addAttribute("words", words);
        return "home";
    }

    @GetMapping("/search/{word}")
    @ResponseBody
    public WordEntity searchWord(@PathVariable String word) {
        WordEntity wordEntity = wordService.getOrCreateWordEntity(word);
        return wordEntity;
    }
}