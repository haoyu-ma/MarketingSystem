package engineer.github.a.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Data
public class EventDto {

	private Long id;

	@NotNull
	private String status;

	@NotNull
	private String eventName;

	@NotNull
	private String frequency;

	@NotNull
	private String frequencyType;

	@NotNull
	private String creator;

	private String auditor;

	private String itAuditor;

	@NotNull
	private String completion;

	@NotNull
	private Date createDate;
}
