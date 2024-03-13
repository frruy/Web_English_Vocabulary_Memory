package org.duyphung.vocamemo.reponses;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class PhoneticResponse {

	@SerializedName("sourceUrl")
	private String sourceUrl;

	@SerializedName("license")
	private LicenseResponse licenseResponse;

	@SerializedName("text")
	private String text;

	@SerializedName("audio")
	private String audio;
}