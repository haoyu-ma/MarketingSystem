package engineer.github.a.api;

import engineer.github.a.dto.EventDto;
import engineer.github.a.dto.EventSaveRequest;
import engineer.github.a.dto.EventUpdateRequest;
import engineer.github.a.service.imp.EventServiceImp;
import engineer.github.a.util.ApiPaths;
import engineer.github.a.util.TPage;
import javassist.NotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(ApiPaths.EventCtrl.CTRL)
@CrossOrigin
public class EventController {

    private final EventServiceImp eventServiceImp;

    public EventController(EventServiceImp eventServiceImp) {
        this.eventServiceImp = eventServiceImp;
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<Boolean> save(@RequestBody EventSaveRequest request) {
        Boolean result = this.eventServiceImp.save(request);
        return ResponseEntity.ok(result);
    }

    // localhost:8182/api/event/pagination?page=1&size=3
    @GetMapping("/pagination")
    public ResponseEntity<TPage<EventDto>> getAllByPagination(Pageable pageable) throws NotFoundException {
        TPage<EventDto> data = eventServiceImp.getAllPageable(pageable);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/audit/pagination")
    public ResponseEntity<TPage<EventDto>> getAllByPaginationForAudit(Pageable pageable) throws NotFoundException {
        TPage<EventDto> data = eventServiceImp.getAllPageableForAudit(pageable);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/itaudit/pagination")
    public ResponseEntity<TPage<EventDto>> getAllByPaginationForItAudit(Pageable pageable) throws NotFoundException {
        TPage<EventDto> data = eventServiceImp.getAllPageableForItAudit(pageable);
        return ResponseEntity.ok(data);
    }

    @RequestMapping(value = "/update/auditor", method = RequestMethod.POST)
    public ResponseEntity<Boolean> updateAuditor(@RequestBody EventUpdateRequest request) throws NotFoundException {
        Boolean result = this.eventServiceImp.updateAuditor(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/count/audit")
    public ResponseEntity<Long> countWaitingReview() {
        Long result = eventServiceImp.countWaitingReview();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/count/itaudit")
    public ResponseEntity<Long> countWaitingITReview() {
        Long result = eventServiceImp.countWaitingITReview();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/count/running")
    public ResponseEntity<Long> countRunning() {
        Long result = eventServiceImp.countRunning();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/count/finished")
    public ResponseEntity<Long> countFinished() {
        Long result = eventServiceImp.countFinished();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/number/yearly")
    public ResponseEntity<List<Long>> countYearlyNumber() {
        List<Long> result = eventServiceImp.countYearlyNumber();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/number/monthly")
    public ResponseEntity<List<Long>> countMonthlyNumber() {
        List<Long> result = eventServiceImp.countMonthlyNumber();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/number/today")
    public ResponseEntity<List<Long>> countTodayNumber() throws ParseException {
        List<Long> result = eventServiceImp.countTodayNumber();
        return ResponseEntity.ok(result);
    }
}
