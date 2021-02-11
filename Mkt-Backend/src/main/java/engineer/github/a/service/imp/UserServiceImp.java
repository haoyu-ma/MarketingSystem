package engineer.github.a.service.imp;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import engineer.github.a.dto.UserPasswordDto;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import engineer.github.a.dto.RegistrationRequest;
import engineer.github.a.dto.UserDto;
import engineer.github.a.model.User;
import engineer.github.a.repository.UserRepository;

import javassist.NotFoundException;

@Service
public class UserServiceImp {

	private final AuthenticationManager authenticationManager;
	
	private final ModelMapper modelMapper;
	
	private final UserRepository userRepository;
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserServiceImp(ModelMapper modelMapper, UserRepository userRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder,
			AuthenticationManager authenticationManager) {
		super();
		this.modelMapper = modelMapper;
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.authenticationManager = authenticationManager;
	}

	public List<UserDto> getAll() throws NotFoundException {
		List<User> users = userRepository.findAll();
		
		if (users.size() < 1) {
			throw new NotFoundException("Project don't already exist");
		}
		
		UserDto[] userdto = modelMapper.map(users, UserDto[].class);
		return Arrays.asList(userdto);
	}

	@Transactional
	public Boolean register(RegistrationRequest registrationRequest) throws Exception {

		List<User> existUserByEmail = userRepository.getByEmail(registrationRequest.getEmail());
		if (existUserByEmail.size() > 0) {
			throw new Exception("User exist with this : " + registrationRequest.getEmail());
		}

		List<User> existUserByUsername = userRepository.getByUsername(registrationRequest.getUsername());
		if (existUserByUsername.size() > 0) {
			throw new Exception("User exist with this name called : " + registrationRequest.getUsername());
		}

		User user = new User();
		user.setRealPassword(registrationRequest.getPassword());
		registrationRequest.setPassword(bCryptPasswordEncoder.encode(registrationRequest.getPassword()));
		// user = modelMapper.map(registirationRequest, User.class);
		user.setUsername(registrationRequest.getUsername());
		user.setEmail(registrationRequest.getEmail());
		user.setPassword(registrationRequest.getPassword());
		userRepository.save(user);

		return true;
	}

	public UserDto findByUserName(String username) throws NotFoundException {
		try {
			
			User user = userRepository.findByUsername(username);
			UserDto userDto = modelMapper.map(user, UserDto.class);
			return userDto;
			
		} catch (Exception e) {
			
			throw new NotFoundException("User dosen't exist with this name called : " + username);
		}
	}

	public Boolean update(String username, @Valid UserDto userDto) throws NotFoundException {

		List<User> userlist = userRepository.getByUsername(username);
		
		if (userlist.size() < 0) {
			throw new NotFoundException("User dosen't exist with this name called : " + username);
		}
		
		User user = modelMapper.map(userDto, User.class);
		user.setId(userlist.get(0).getId());
		user.setRealPassword(userlist.get(0).getRealPassword());
		user.setPassword(userlist.get(0).getPassword());
		userRepository.save(user);
		return true;
	}

	public Boolean changePassword(UserPasswordDto userPasswordDto) throws NotFoundException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(!userPasswordDto.getUsername().equals(auth.getName())) {
			return false;
		}
		
		List<User> userlist = userRepository.getByUsername(userPasswordDto.getUsername());
		
		if (userlist.size() < 0) {
			throw new NotFoundException("User dosen't exist with this name called : " + userPasswordDto.getUsername());
		}
		
		boolean control=bCryptPasswordEncoder.matches( userPasswordDto.getPassword(),userlist.get(0).getPassword());
		
		if (!control) {
			throw new NotFoundException("Mevcut şifreniz yanlıştır.");
		}
		
		userlist.get(0).setRealPassword(userPasswordDto.getNewpassword());
		userlist.get(0).setPassword(bCryptPasswordEncoder.encode(userPasswordDto.getNewpassword()));
		userRepository.save(userlist.get(0));
		return true;

	}
}
