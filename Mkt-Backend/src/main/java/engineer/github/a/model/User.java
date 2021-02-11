package engineer.github.a.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users", indexes = { @Index(name = "idx_username", columnList = "uname") })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "uname", length = 100, unique = true)
	private String username;

	@Column(name = "pwd", length = 300)
	private String password;

	@Column(name = "realpwd", length = 300)
	private String realPassword;

	@Column(name = "firstname", length = 50)
	private String firstName;

	@Column(name = "lastname", length = 50)
	private String lastName;

	@Column(name = "email", length = 100, unique = true)
	private String email;

	@Column(name = "address", length = 300)
	private String address;

	@Column(name = "city", length = 50)
	private String city;

	@Column(name = "country", length = 50)
	private String country;

	@Column(name = "postalCode", length = 10)
	private String postalCode;

	@Column(name = "aboutMe", length = 500)
	private String aboutMe;

	@Column(name = "position", length = 30)
	private String position;

	@Column(name = "department", length = 30)
	private String department;
}
