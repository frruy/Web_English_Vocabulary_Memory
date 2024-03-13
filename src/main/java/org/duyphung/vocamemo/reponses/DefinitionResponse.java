package org.duyphung.vocamemo.reponses;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DefinitionResponse {

	@SerializedName("synonyms")
	private List<Object> synonyms;

	@SerializedName("antonyms")
	private List<Object> antonyms;

	@SerializedName("definition")
	private String definition;

	@SerializedName("example")
	private String example;
}