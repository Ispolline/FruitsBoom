package com.fruits.carnival.clo;

import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("response")
	private String response;

	public String getResponse(){
		return response;
	}
}