package org.duyphung.vocamemo.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.duyphung.vocamemo.entities.DefinitionEntity;
import org.duyphung.vocamemo.entities.MeaningEntity;
import org.duyphung.vocamemo.entities.WordEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordEntityTypeAdapter extends TypeAdapter<WordEntity> {
    @Override
    public void write(JsonWriter out, WordEntity word) throws IOException {
        out.beginObject();
        out.name("id").value(word.getId());
        out.name("audio").value(word.getAudio());
        out.name("text").value(word.getText());
        out.name("phonetic").value(word.getPhonetic());
        out.name("isHighlight").value(word.isHighlight());
//        out.name("createdAt").value(word.getCreatedAt().toString());
        out.name("meanings").beginArray();
        for (MeaningEntity meaning : word.getMeanings()) {
            out.beginObject();
            out.name("id").value(meaning.getId());
            out.name("partOfSpeech").value(meaning.getPartOfSpeech());

            // Write definitions
            out.name("definitions").beginArray();
            for (DefinitionEntity definition : meaning.getDefinitions()) {
                out.beginObject();
                out.name("id").value(definition.getId());
                out.name("definition").value(definition.getDefinition());
                out.name("example").value(definition.getExample());
                out.endObject();
            }
            out.endArray();

            out.endObject();
        }
        out.endArray();

        out.endObject();
    }

    @Override
    public WordEntity read(JsonReader in) throws IOException {
        WordEntity word = new WordEntity();

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "id":
                    word.setId(in.nextInt());
                    break;
                case "audio":
                    word.setAudio(in.nextString());
                    break;
                case "text":
                    word.setText(in.nextString());
                    break;
                case "phonetic":
                    word.setPhonetic(in.nextString());
                    break;
                case "isHighlight":
                    word.setHighlight(in.nextBoolean());
                    break;
                case "meanings":
                    word.setMeanings(readMeanings(in));
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        return word;
    }

    private Set<MeaningEntity> readMeanings(JsonReader in) throws IOException {
        Set<MeaningEntity> meanings = new HashSet<>();

        in.beginArray();
        while (in.hasNext()) {
            MeaningEntity meaning = new MeaningEntity();

            in.beginObject();
            while (in.hasNext()) {
                String name = in.nextName();
                switch (name) {
                    case "id":
                        meaning.setId(in.nextInt());
                        break;
                    case "partOfSpeech":
                        meaning.setPartOfSpeech(in.nextString());
                        break;
                    case "definitions":
                        meaning.setDefinitions(readDefinitions(in));
                        break;
                    default:
                        in.skipValue();
                        break;
                }
            }
            in.endObject();

            meanings.add(meaning);
        }
        in.endArray();

        return meanings;
    }

    private List<DefinitionEntity> readDefinitions(JsonReader in) throws IOException {
        List<DefinitionEntity> definitions = new ArrayList<>();

        in.beginArray();
        while (in.hasNext()) {
            DefinitionEntity definition = new DefinitionEntity();

            in.beginObject();
            while (in.hasNext()) {
                String name = in.nextName();
                switch (name) {
                    case "id":
                        definition.setId(in.nextInt());
                        break;
                    case "definition":
                        definition.setDefinition(in.nextString());
                        break;
                    case "example":
                        definition.setExample(in.nextString());
                        break;
                    default:
                        in.skipValue();
                        break;
                }
            }
            in.endObject();

            definitions.add(definition);
        }
        in.endArray();

        return definitions;
    }
}
