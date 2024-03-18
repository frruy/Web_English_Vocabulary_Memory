package org.duyphung.vocamemo.controllers;

import org.duyphung.vocamemo.entities.WordEntity;
import org.duyphung.vocamemo.services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
public class HomeController {

    private final WordService wordService;

    public HomeController(@Autowired WordService wordService) {
        this.wordService = wordService;
    }

    @RequestMapping("/home")
    public String getHomePage(Model model) {
        Set<WordEntity> words = wordService.getTop7WordsByUserOrderedByUpdatedTime();
        model.addAttribute("words", words);
        return "home";
    }

    @GetMapping("/search/{word}")
    @ResponseBody
    public String searchWord(@PathVariable String word) {
        WordEntity wordEntity = wordService.getOrCreateWordEntity(word);
        return wordEntity.toJson();
    }
}