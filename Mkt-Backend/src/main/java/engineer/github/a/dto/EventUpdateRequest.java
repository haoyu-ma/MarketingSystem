package engineer.github.a.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EventUpdateRequest {

	private Long id;

	private String auditor;

	private String itAuditor;
}
