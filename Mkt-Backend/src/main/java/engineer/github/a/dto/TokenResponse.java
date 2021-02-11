package engineer.github.a.dto;

import engineer.github.a.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TokenResponse {

	private String token;
	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private String city;
	private String country;
	private String postalCode;
	private String aboutMe;
	private String position;
	private String department;

    public TokenResponse(User user, String token) {
		this.token = token;
		this.id = user.getId();
		this.username = user.getUsername();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.address = user.getAddress();
		this.city = user.getCity();
		this.country = user.getCountry();
		this.postalCode = user.getPostalCode();
		this.aboutMe = user.getAboutMe();
		this.position = user.getPosition();
		this.department = user.getDepartment();
    }

}
