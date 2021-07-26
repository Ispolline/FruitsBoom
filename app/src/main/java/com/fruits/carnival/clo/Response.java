package com.fruits.carnival.clo;

import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("status")
	private String status;

	public String getStatus(){
		return status;
	}
}