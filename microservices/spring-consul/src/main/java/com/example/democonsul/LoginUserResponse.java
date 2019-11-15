package com.example.democonsul;

public class LoginUserResponse {
	private String userName;
	private String token;
	private String refreshToken;

	public LoginUserResponse() {
	}

	public LoginUserResponse(String userName, String token, String refreshToken) {
		this.userName = userName;
		this.token = token;
		this.refreshToken = refreshToken;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
