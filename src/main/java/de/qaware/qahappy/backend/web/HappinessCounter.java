package de.qaware.qahappy.backend.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

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

    public List<Happiness> happinessBetween(Date start, Date end) {

        return repository.findByDateBetween(start, end);
    }

    public synchronized void incrementHappiness(HappinessType type) {
        amendHappiness(type, new Date());

    }

    public void amendHappiness(HappinessType type, Date date) {
        Happiness happyness = repository.findOne(date);
        if (happyness == null) {
            LOG.info("No Happiness data found for " + date + ", creating new one!");
            happyness = new Happiness(0, 0, 0, date);
        }
        happyness.increment(type);
        repository.save(happyness);
    }
}
