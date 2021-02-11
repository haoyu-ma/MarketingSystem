package engineer.github.a.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "event")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(name = "status", length = 100)
	private String status;

	@NotNull
	@Column(name = "eventName", length = 100)
	private String eventName;

	@NotNull
	@Column(name = "frequency", length = 10)
	private String frequency;

	@NotNull
	@Column(name = "frequencyType", length = 10)
	private String frequencyType;

	@NotNull
	@Column(name = "creator", length = 10)
	private String creator;

	@Column(name = "auditor", length = 10)
	private String auditor;

	@Column(name = "itAuditor", length = 10)
	private String itAuditor;

	@NotNull
	@Column(name = "completion", length = 5)
	private String completion;

	@NotNull
	@Column(name = "createDate", length = 50)
	private Date createDate;

	@NotNull
	@Column(name = "createYear", length = 5)
	private String createYear;

	@NotNull
	@Column(name = "createMonth", length = 5)
	private String createMonth;

	@NotNull
	@Column(name = "createDay", length = 5)
	private String createDay;
}
