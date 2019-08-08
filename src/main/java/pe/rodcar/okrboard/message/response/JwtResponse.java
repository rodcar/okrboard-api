package pe.rodcar.okrboard.message.response;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class JwtResponse {
	private String email;
	private String token;
	private String type = "Bearer";
	private Long id;
	private Collection<? extends GrantedAuthority> authorities;

	public JwtResponse(String accessToken, Long id, String email, Collection<? extends GrantedAuthority> authorities) {
		this.token = accessToken;
		this.id = id;
		this.authorities = authorities;
		this.email = email;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}