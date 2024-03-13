package org.duyphung.vocamemo.reponses;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class MeaningResponse {

	@SerializedName("synonyms")
	private List<Object> synonyms;

	@SerializedName("partOfSpeech")
	private String partOfSpeech;

	@SerializedName("antonyms")
	private List<Object> antonyms;

	@SerializedName("definitions")
	private List<DefinitionResponse> definitions;
}