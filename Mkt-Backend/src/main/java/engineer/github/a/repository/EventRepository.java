package engineer.github.a.repository;

import engineer.github.a.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findAll(Specification<Event> specification, Pageable pageable);

    @Query("select count(e) from Event e where e.status like %:status%")
    Long countByCondition(String status);

    @Query("select e from Event e where e.createYear like %:year%")
    List<Event> findAllByYear(String year);

    @Query("select e from Event e where e.createMonth like %:month%")
    List<Event> findAllByMonth(String month);

    @Query("select e from Event e where e.createDate = :date")
    List<Event> findAllByCreateDay(Date date);
}
