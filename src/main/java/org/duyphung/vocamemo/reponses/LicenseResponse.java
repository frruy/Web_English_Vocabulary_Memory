package org.duyphung.vocamemo.reponses;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class LicenseResponse {

	@SerializedName("name")
	private String name;

	@SerializedName("url")
	private String url;
}