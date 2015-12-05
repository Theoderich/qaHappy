package de.qaware.qahappy.backend.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;
import java.util.concurrent.atomic.AtomicIntegerArray;

@Component
public class HappinessCounter {

    private static final Log LOG = LogFactory.getLog(HappinessCounter.class);

    private AtomicIntegerArray happinessCounter;

    private HappinessRepository repository;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public HappinessCounter(HappinessRepository repository) {
        this.repository = repository;
        happinessCounter = new AtomicIntegerArray(3);
    }

    @PostConstruct
    public void loadTodayFromDb() {
        Happiness today = repository.findOne(new Date());

        if (today != null) {
            happinessCounter.set(0, today.getUnhappy());
            happinessCounter.set(1, today.getOk());
            happinessCounter.set(2, today.getHappy());
        }
    }

    @PreDestroy
    public void persistTodayToDb() {
        LOG.info("persisting current happiness on close");
        repository.save(todaysHappiness());
    }

    @Scheduled(fixedRate = 60_000)
    public void autoSave() {
        LOG.info("auto saving current happiness counter");
        repository.save(todaysHappiness());
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void resetCounterForNewDay() {
        LOG.info("It's a new day! Happiness reset");
        happinessCounter = new AtomicIntegerArray(3);
    }


    public Happiness todaysHappiness() {
        return new Happiness(happinessCounter.get(0), happinessCounter.get(1), happinessCounter.get(2));
    }

    public void incrementHappiness(HappinessType type) {
        happinessCounter.incrementAndGet(type.ordinal());
    }
}
