package pe.rodcar.okrboard.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.rodcar.okrboard.entities.Role;
import pe.rodcar.okrboard.entities.RoleName;
import pe.rodcar.okrboard.entities.User;
import pe.rodcar.okrboard.message.request.LoginForm;
import pe.rodcar.okrboard.message.request.SignUpForm;
import pe.rodcar.okrboard.message.response.JwtResponse;
import pe.rodcar.okrboard.message.response.ResponseMessage;
import pe.rodcar.okrboard.repository.RoleRepository;
import pe.rodcar.okrboard.repository.UserRepository;
import pe.rodcar.okrboard.security.jwt.JwtProvider;
import pe.rodcar.okrboard.security.services.UserPrinciple;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail(), userDetails.getAuthorities()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			// TODO We can use a Global Error Handler
			return new ResponseEntity<>(new ResponseMessage("Error -> The email is already in use"),
					HttpStatus.BAD_REQUEST);
		}
		
		User user = new User(signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
		Set<Role> roles = new HashSet<>();		
		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("¡Fallo! -> Causa: El rol del usuario no se encuentra."));
		roles.add(userRole);
		user.setRoles(roles);
		userRepository.save(user);

		return new ResponseEntity<>(new ResponseMessage("Usuario registrado correctamente!"), HttpStatus.OK);
	}
}