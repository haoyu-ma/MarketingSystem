package engineer.github.a.service.imp;

import engineer.github.a.dto.EventDto;
import engineer.github.a.dto.EventSaveRequest;
import engineer.github.a.dto.EventUpdateRequest;
import engineer.github.a.model.Event;
import engineer.github.a.repository.EventRepository;
import engineer.github.a.service.EventService;
import engineer.github.a.util.TPage;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EventServiceImp implements EventService {

	private final ModelMapper modelMapper;

	private final EventRepository eventRepository;

	public EventServiceImp(ModelMapper modelMapper, EventRepository eventRepository) {
		super();
		this.modelMapper = modelMapper;
		this.eventRepository = eventRepository;
	}

	public Boolean save(EventSaveRequest eventSaveRequest) {
		Event event = new Event();
		event.setEventName(eventSaveRequest.getEventName());
		event.setFrequency(eventSaveRequest.getFrequency());
		event.setFrequencyType(eventSaveRequest.getFrequencyType());
		event.setCreator(eventSaveRequest.getCreator());
		event.setStatus("Waiting for review");
		event.setCompletion("0");
		event.setCreateDate(new Date());
		Calendar cal = Calendar.getInstance();
		event.setCreateYear(String.valueOf(cal.get(Calendar.YEAR)));
		event.setCreateMonth(String.valueOf(cal.get(Calendar.MONTH) + 1));
		event.setCreateDay(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
		eventRepository.save(event);

		return true;
	}

	public TPage<EventDto> getAllPageable(Pageable pageable) throws NotFoundException {
		try {
			Page<Event> page = eventRepository.findAll(PageRequest.of(pageable.getPageNumber(),
					pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "id")));
			TPage<EventDto> tPage = new TPage<>();
			EventDto[] eventDtos = modelMapper.map(page.getContent(), EventDto[].class);

			tPage.setStat(page, Arrays.asList(eventDtos));
			return tPage;
		
		} catch (Exception e) {
			throw new NotFoundException("getAllPageable error : " + e);
		}
	}

	public TPage<EventDto> getAllPageableForAudit(Pageable pageable) throws NotFoundException {
		Specification<Event> queryCondition = (Specification<Event>) (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> predicateList = new ArrayList<>();
			predicateList.add(criteriaBuilder.equal(root.get("status"), "Waiting for review"));

			return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
		};

		try {
			Page<Event> page = eventRepository.findAll(queryCondition, PageRequest.of(pageable.getPageNumber(),
					pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "id")));
			TPage<EventDto> tPage = new TPage<>();
			EventDto[] eventDtos = modelMapper.map(page.getContent(), EventDto[].class);

			tPage.setStat(page, Arrays.asList(eventDtos));
			return tPage;

		} catch (Exception e) {
			throw new NotFoundException("getAllPageable error : " + e);
		}
	}

	public TPage<EventDto> getAllPageableForItAudit(Pageable pageable) throws NotFoundException {
		Specification<Event> queryCondition = (Specification<Event>) (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> predicateList = new ArrayList<>();
			predicateList.add(criteriaBuilder.equal(root.get("status"), "Waiting for IT review"));

			return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
		};

		try {
			Page<Event> page = eventRepository.findAll(queryCondition, PageRequest.of(pageable.getPageNumber(),
					pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "id")));
			TPage<EventDto> tPage = new TPage<>();
			EventDto[] eventDtos = modelMapper.map(page.getContent(), EventDto[].class);

			tPage.setStat(page, Arrays.asList(eventDtos));
			return tPage;

		} catch (Exception e) {
			throw new NotFoundException("getAllPageable error : " + e);
		}
	}

	@Override
	public Boolean updateAuditor(EventUpdateRequest eventUpdateRequest) throws NotFoundException {
		Optional<Event> eventOpt = eventRepository.findById(eventUpdateRequest.getId());
		if (!eventOpt.isPresent()) {
			throw new NotFoundException("Event doesn't exist : " + eventUpdateRequest.getId());
		}

		Event event = eventOpt.get();
		if (!StringUtils.isEmpty(eventUpdateRequest.getAuditor())) {
			event.setAuditor(eventUpdateRequest.getAuditor());
			event.setStatus("Waiting for IT review");
		}
		if (!StringUtils.isEmpty(eventUpdateRequest.getItAuditor())) {
			event.setItAuditor(eventUpdateRequest.getItAuditor());
			event.setStatus("Running");
		}
		eventRepository.save(event);
		return true;
	}

	@Override
	public Long countWaitingReview() {
		Long result = eventRepository.countByCondition("Waiting for review");
		if (result == null) {
			return 0L;
		}
		return result;
	}

	@Override
	public Long countWaitingITReview() {
		Long result = eventRepository.countByCondition("Waiting for IT review");
		if (result == null) {
			return 0L;
		}
		return result;
	}

	@Override
	public Long countRunning() {
		Long result = eventRepository.countByCondition("Running");
		if (result == null) {
			return 0L;
		}
		return result;
	}

	@Override
	public Long countFinished() {
		Long result = eventRepository.countByCondition("Finished");
		if (result == null) {
			return 0L;
		}
		return result;
	}

	@Override
	public List<Long> countYearlyNumber() {
		Calendar cal = Calendar.getInstance();
		List<Event> events = eventRepository.findAllByYear(String.valueOf(cal.get(Calendar.YEAR)));
		Map<Long, Long> eventMap = new TreeMap<>(Comparator.naturalOrder());
		for (long i=1; i<=12; i++) {
			eventMap.put(i, 0L);
		}
		for (Event event : events) {
			long currentMonth = Long.parseLong(event.getCreateMonth());
			Long currentMonthNumber = 0L;
			if (eventMap.containsKey(currentMonth)) {
				currentMonthNumber = eventMap.get(currentMonth);
			}
			currentMonthNumber = currentMonthNumber + 1;
			eventMap.put(currentMonth, currentMonthNumber);
		}

		return new ArrayList<>(eventMap.values());
	}

	@Override
	public List<Long> countMonthlyNumber() {
		Calendar cal = Calendar.getInstance();
		List<Event> events = eventRepository.findAllByMonth(String.valueOf(cal.get(Calendar.MONTH)+1));
		Map<Long, Long> eventMap = new TreeMap<>(Comparator.naturalOrder());
		for (long i=1; i<=31; i++) {
			eventMap.put(i, 0L);
		}
		for (Event event : events) {
			Long currentDay = Long.parseLong(event.getCreateDay());
			Long currentDayNumber = 0L;
			if (eventMap.containsKey(currentDay)) {
				currentDayNumber = eventMap.get(currentDay);
			}
			currentDayNumber = currentDayNumber + 1;
			eventMap.put(currentDay, currentDayNumber);
		}

		return new ArrayList<>(eventMap.values());
	}

	@Override
	public List<Long> countTodayNumber() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateStr = sdf.format(new Date());
		Date currentDate = sdf.parse(currentDateStr);
		List<Event> events = eventRepository.findAllByCreateDay(currentDate);
		Map<String, Long> eventMap = new HashMap<>();
		eventMap.put("Waiting for review", 0L);
		eventMap.put("Waiting for IT review", 0L);
		eventMap.put("Running", 0L);
		eventMap.put("Finished", 0L);
		for (Event event : events) {
			String status = event.getStatus();
			Long currentNumber = 0L;
			if (eventMap.containsKey(status)) {
				currentNumber = eventMap.get(status);
			}
			currentNumber = currentNumber + 1;
			eventMap.put(status, currentNumber);
		}

		List<Long> result = new ArrayList<>();
		result.add(eventMap.get("Waiting for review"));
		result.add(eventMap.get("Waiting for IT review"));
		result.add(eventMap.get("Running"));
		result.add(eventMap.get("Finished"));
		return result;
	}
}
