package engineer.github.a.service;

import engineer.github.a.dto.EventDto;
import engineer.github.a.dto.EventSaveRequest;
import engineer.github.a.dto.EventUpdateRequest;
import engineer.github.a.util.TPage;
import javassist.NotFoundException;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;

public interface EventService {

	public Boolean save(EventSaveRequest eventSaveRequest);

	public TPage<EventDto> getAllPageable(Pageable pageable) throws NotFoundException;

	public Boolean updateAuditor(EventUpdateRequest eventUpdateRequest) throws NotFoundException;

	public Long countWaitingReview();

	public Long countWaitingITReview();

	public Long countRunning();

	public Long countFinished();

	List<Long> countYearlyNumber();

	List<Long> countMonthlyNumber();

	List<Long> countTodayNumber() throws ParseException;
}
