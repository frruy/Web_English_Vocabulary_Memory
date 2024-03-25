package org.duyphung.vocamemo.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.duyphung.vocamemo.entities.*;
import org.duyphung.vocamemo.reponses.DefinitionResponse;
import org.duyphung.vocamemo.reponses.MeaningResponse;
import org.duyphung.vocamemo.reponses.PhoneticResponse;
import org.duyphung.vocamemo.reponses.WordResponse;
import org.duyphung.vocamemo.repositories.WordRepository;
import org.duyphung.vocamemo.utils.SectionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WordService {
    private final WordRepository wordRepository;
    @Autowired
    private UserService userService;
    private static final String API_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/";

    public WordService(@Autowired WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Transactional
    public WordEntity getOrCreateWordEntityAndUpdateTime(String word) {
        WordEntity wordEntity = wordRepository.findByText(word);
        if (wordEntity == null) {
            wordEntity = createWordEntityFromResponse(word);
        }
        var user = SectionHelper.getUserFromSection();
        assert user != null;
        if (!wordEntity.getUsers().contains(user)) {
            wordEntity.addUser(user);
            wordRepository.save(wordEntity);
        }
        wordRepository.updateUpdatedTime(wordEntity.getId(), user.getId());
        return wordEntity;
    }

    public List<WordEntity> getWordsByTexts(List<String> words) {
        return wordRepository.findByTextIn(words);
    }

    private WordEntity createWordEntityFromResponse(String word) {
        WordResponse wordResponse = getWordResponse(word);
        return saveWord(wordResponse);
    }

    public void changeWordHighlightStatus(int wordId, boolean isHighlight) {
        int userId = Objects.requireNonNull(SectionHelper.getUserFromSection()).getId();
        wordRepository.updateWordHighlightStatus(wordId, userId, isHighlight);
    }

    private WordResponse getWordResponse(String word) {
        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL + word;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        Gson gson = new Gson();
        JsonArray jsonArray = JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonArray();

        WordResponse[] responses = gson.fromJson(jsonArray, WordResponse[].class);
        return responses[0];
    }

    private WordEntity saveWord(WordResponse wordResponse) {
        WordEntity wordEntity = mapToWordEntity(wordResponse);
        UserEntity user = SectionHelper.getUserFromSection();
        assert user != null;
        wordEntity.addUser(user);
        wordRepository.save(wordEntity);
        return wordEntity;
    }

    public List<WordEntity> getWordsByUserOrderedBySearchTime() {
        int userId = Objects.requireNonNull(SectionHelper.getUserFromSection()).getId();
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("updatedTime").descending());
//        Page<WordEntity> wordPage = wordRepository.findWordsByUserIdOrderByUpdatedTimeDesc(userId, pageable);
        List<Object[]> resultList = wordRepository.findWordsByUserIdOrderByLastSearchTimeDesc(userId);
        List<WordEntity> words = new ArrayList<>();

        for (Object[] result : resultList) {
            WordEntity word = (WordEntity) result[0];
            Boolean isHighlight = (Boolean) result[1];

            word.setHighlight(isHighlight != null && isHighlight);
            words.add(word);
        }
        return words;
    }

    public List<WordEntity> getTopWordsPriorityToReview() {
        UserEntity userEntity = SectionHelper.getUserFromSection();
        assert userEntity != null;
        int userId = userEntity.getId();
        int pageSize = userEntity.getTargetWordsPerDay(); // Specify the desired number of words per page
        Pageable pageable = PageRequest.of(0, 3);
        return wordRepository.findWordsHighlightByUserIdOrderByReviewedAt(userId, pageable);
    }

    private WordEntity mapToWordEntity(WordResponse wordResponse) {
        WordEntity wordEntity = new WordEntity();
        wordEntity.setText(wordResponse.getWord());

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
        if (phoneticResponse.getAudio() == null || phoneticResponse.getText() == null)
            return null;
        if (phoneticResponse.getAudio().isEmpty() || phoneticResponse.getText().isEmpty()) {
            return null;
        } else {
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

    public List<WordUser> getWordUserByWords(List<WordEntity> words) {
        List<Integer> wordIdSet = words.stream()
                .map(WordEntity::getId)
                .collect(Collectors.toList());
        int userId = Objects.requireNonNull(SectionHelper.getUserFromSection()).getId();

        return wordRepository.findWordUsersByWords(wordIdSet, userId);
    }

    public void updateReviewTime(List<WordEntity> words) {
        List<Integer> wordIds = words.stream().map(WordEntity::getId).collect(Collectors.toList());
        int userId = Objects.requireNonNull(SectionHelper.getUserFromSection()).getId();
        wordRepository.updateReviewTime(wordIds, userId);
    }

    public void deleteWordUserByWordId(int wordId) {
        int userId = Objects.requireNonNull(SectionHelper.getUserFromSection()).getId();
        wordRepository.deleteWordUserBy(wordId, userId);
    }
}
