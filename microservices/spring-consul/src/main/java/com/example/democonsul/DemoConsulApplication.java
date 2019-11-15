package com.example.democonsul;

import com.example.democonsul.entity.User;
import com.example.democonsul.model.UserContext;
import com.example.democonsul.model.token.JwtToken;
import com.example.democonsul.model.token.JwtTokenFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
public class DemoConsulApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoConsulApplication.class, args);
	}


	@Autowired
	private JwtTokenFactory tokenFactory;

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String home() {
		return "Hello world";
	}

    @PostMapping("/api/auth/login")
    public LoginUserResponse login(@RequestBody LoginUserRequest request) {
		UserContext userContext = authenticate(request);
		JwtToken accessToken = tokenFactory.createAccessJwtToken(userContext);
		JwtToken refreshToken = tokenFactory.createRefreshToken(userContext);

		return new LoginUserResponse(request.getUserName(), accessToken.getToken(), refreshToken.getToken());
    }

	private UserContext authenticate(@RequestBody LoginUserRequest request) {
		User user = userService.getByUsername(request.getUserName()).orElseThrow(() -> new UsernameNotFoundException("User not found: " + request.getUserName()));

		if (user.getRoles() == null) throw new InsufficientAuthenticationException("User has no roles assigned");
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
				.collect(Collectors.toList());

		return UserContext.create(user.getUsername(), authorities);
	}

	@PostMapping("/api/key/register")
	public User registerKey(@RequestBody RegisterUserRequest request) {
		return new User(1L, request.getUserName(), UUID.randomUUID().toString(), null);
	}

	@PostMapping("/api/jwt/register")
	public User registerJwt(@RequestBody RegisterUserRequest request) {
		return new User(1L, request.getUserName(), UUID.randomUUID().toString(), null);
	}

}


class RegisterUserRequest {
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}

class LoginUserRequest {
	private String userName;
	private String password;

	public LoginUserRequest() {
	}

	public LoginUserRequest(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}


