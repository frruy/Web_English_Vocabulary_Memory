package org.duyphung.vocamemo.controllers;

import org.duyphung.vocamemo.reponses.WordResponse;
import org.duyphung.vocamemo.repositories.WordRepository;
import org.duyphung.vocamemo.services.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private WordRepository wordRepository;

    @GetMapping("/dictionary/{word}")
    public ResponseEntity<WordResponse> getWordDefinition(@PathVariable String word) {
        ResponseEntity<WordResponse> entries = dictionaryService.getWordDefinition(word);

//        if (!entries.isEmpty()) {
//            Map<String, Object> firstEntry = entries.get(0);
//            String text = (String) firstEntry.get("word");
//
//            // Get the audio URL
//            String audio = "";
//            List<Map<String, Object>> phonetics = (List<Map<String, Object>>) firstEntry.get("phonetics");
//            if (phonetics != null) {
//                for (Map<String, Object> phonetic : phonetics) {
//                    if (phonetic.get("audio") != null && !phonetic.get("audio").equals("")) {
//                        audio = (String) phonetic.get("audio");
//                    }
//                }
//            }
//            WordEntity wordEntity = new WordEntity();
//            wordEntity.setText(text);
//            wordEntity.setAudio(audio);
//        }

        return entries;
    }


}