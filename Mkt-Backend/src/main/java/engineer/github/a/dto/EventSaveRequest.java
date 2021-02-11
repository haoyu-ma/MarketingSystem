package engineer.github.a.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EventSaveRequest {

	private String eventName;

	private String frequency;

	private String frequencyType;

	private String creator;
}
