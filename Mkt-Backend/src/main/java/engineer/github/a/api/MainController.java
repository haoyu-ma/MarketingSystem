package engineer.github.a.api;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import engineer.github.a.dto.LoginRequest;
import engineer.github.a.dto.RegistrationRequest;
import engineer.github.a.dto.TokenResponse;
import engineer.github.a.model.User;
import engineer.github.a.repository.UserRepository;
import engineer.github.a.security.JwtTokenUtil;
import engineer.github.a.service.imp.UserServiceImp;
import engineer.github.a.util.ApiPaths;

@RestController
@RequestMapping(ApiPaths.MainCtrl.CTRL)
@CrossOrigin
public class MainController {

	private final AuthenticationManager authenticationManager;

	private final UserRepository userRepository;
	
	private final UserServiceImp userServiceImp;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final ModelMapper modelMapper;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	public MainController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
			AuthenticationManager authenticationManager, ModelMapper modelMapper, UserServiceImp userServiceImp) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.modelMapper = modelMapper;
		this.authenticationManager = authenticationManager;
		this.userServiceImp = userServiceImp;
	}

	@RequestMapping(value = "/sign-in", method = RequestMethod.POST)
	public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) throws AuthenticationException {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		
		final User user = userRepository.findByUsername(request.getUsername());
		final String token = jwtTokenUtil.generateToken(user);
		return ResponseEntity.ok(new TokenResponse(user, token));
	}

	@RequestMapping(value = "/sign-up", method = RequestMethod.POST)
	public ResponseEntity<Boolean> signUp(@RequestBody RegistrationRequest registrationRequest) throws Exception {

		Boolean result = userServiceImp.register(registrationRequest);
		return ResponseEntity.ok(result);
	}
}
