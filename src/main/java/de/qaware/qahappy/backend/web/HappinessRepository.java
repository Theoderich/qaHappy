package de.qaware.qahappy.backend.web;

import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface HappinessRepository extends CrudRepository<Happiness, Date> {
}
