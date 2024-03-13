package org.duyphung.vocamemo.reponses;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class WordResponse {

	@SerializedName("license")
	private LicenseResponse licenseResponse;

	@SerializedName("phonetic")
	private String phonetic;

	@SerializedName("phonetics")
	private List<PhoneticResponse> phonetics;

	@SerializedName("word")
	private String word;

	@SerializedName("meanings")
	private List<MeaningResponse> meanings;

	@SerializedName("sourceUrls")
	private List<String> sourceUrls;
}