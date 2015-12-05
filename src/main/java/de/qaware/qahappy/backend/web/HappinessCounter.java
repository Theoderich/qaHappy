package de.qaware.qahappy.backend.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HappinessCounter {

    private static final Log LOG = LogFactory.getLog(HappinessCounter.class);

    private HappinessRepository repository;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public HappinessCounter(HappinessRepository repository) {
        this.repository = repository;
    }

    public Happiness todaysHappiness() {
        return repository.findOne(new Date());
    }

    public synchronized void incrementHappiness(HappinessType type) {
        Happiness todaysHappiness = repository.findOne(new Date());
        if (todaysHappiness == null) {
            LOG.info("No Happiness data found for today, creating new one!");
            todaysHappiness = new Happiness(0, 0, 0);
        }
        todaysHappiness.increment(type);
        repository.save(todaysHappiness);
    }
}
