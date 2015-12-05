package de.qaware.qahappy.backend.web;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface HappinessRepository extends CrudRepository<Happiness, Date> {

    List<Happiness> findByDateBetween(Date start, Date end);

}
