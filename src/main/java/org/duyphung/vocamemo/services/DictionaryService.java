package org.duyphung.vocamemo.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.duyphung.vocamemo.models.DefinitionEntity;
import org.duyphung.vocamemo.models.MeaningEntity;
import org.duyphung.vocamemo.models.UserEntity;
import org.duyphung.vocamemo.models.WordEntity;
import org.duyphung.vocamemo.reponses.DefinitionResponse;
import org.duyphung.vocamemo.reponses.MeaningResponse;
import org.duyphung.vocamemo.reponses.PhoneticResponse;
import org.duyphung.vocamemo.reponses.WordResponse;
import org.duyphung.vocamemo.repositories.WordRepository;
import org.duyphung.vocamemo.utils.SectionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DictionaryService {
    private final WordRepository wordRepository;
    private static final String API_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/";

    public DictionaryService(@Autowired WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public WordResponse getWordResponse(String word) {
        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL + word;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        Gson gson = new Gson();
        JsonArray jsonArray = JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonArray();

        WordResponse[] responses = gson.fromJson(jsonArray, WordResponse[].class);
        return responses[0];
    }

    public void saveWord(WordResponse wordResponse) {
        WordEntity wordEntity = mapToWordEntity(wordResponse);
        wordEntity.addUser(SectionHelper.getUserFromSection());
        wordRepository.save(wordEntity);
    }

    public Set<WordEntity> getWord() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        return wordRepository.findTop5ByUsersIsOrderByCreatedAt(1);
    }

    public WordEntity mapToWordEntity(WordResponse wordResponse) {
        WordEntity wordEntity = new WordEntity();
        wordEntity.setText(wordResponse.getWord());
        wordEntity.setPhonetic(wordResponse.getPhonetic());

        wordResponse.getPhonetics().stream()
                .map(this::mapToPhoneticAndAudio)
                .filter(Objects::nonNull)
                .findFirst()
                .ifPresent(map -> {
                    wordEntity.setPhonetic(map.get("phonetic"));
                    wordEntity.setAudio(map.get("audio"));
                });

        // Map meanings
        Set<MeaningEntity> meaningEntities = wordResponse.getMeanings().stream()
                .map(this::mapToMeaningEntity)
                .peek(meaningEntity -> meaningEntity.setWordEntity(wordEntity))
                .collect(Collectors.toSet());
        wordEntity.setMeanings(meaningEntities);

        return wordEntity;
    }

    private Map<String, String> mapToPhoneticAndAudio(PhoneticResponse phoneticResponse) {
        if (phoneticResponse.getAudio().isEmpty() || phoneticResponse.getText().isEmpty())
            return null;
        else {
            Map<String, String> map = new HashMap<>();
            map.put("audio", phoneticResponse.getAudio());
            map.put("phonetic", phoneticResponse.getText());
            return map;
        }
    }

    private MeaningEntity mapToMeaningEntity(MeaningResponse meaningResponse) {
        MeaningEntity meaningEntity = new MeaningEntity();
        meaningEntity.setPartOfSpeech(meaningResponse.getPartOfSpeech());

        List<DefinitionEntity> definitionEntities = meaningResponse.getDefinitions().stream()
                .map(this::mapToDefinitionEntity)
                .peek(definitionEntity -> definitionEntity.setMeaningEntity(meaningEntity))
                .collect(Collectors.toList());
        meaningEntity.setDefinitions(definitionEntities);
        return meaningEntity;
    }

    private DefinitionEntity mapToDefinitionEntity(DefinitionResponse definitionResponse) {
        DefinitionEntity definitionEntity = new DefinitionEntity();
        definitionEntity.setDefinition(definitionResponse.getDefinition());
        definitionEntity.setExample(definitionResponse.getExample());
        return definitionEntity;
    }
}
