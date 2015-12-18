package de.qaware.qahappy.backend.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class RestInterface {

    private static final Log LOG = LogFactory.getLog(RestInterface.class);


    private HappinessCounter counter;

    @Autowired
    public RestInterface(HappinessCounter counter) {
        this.counter = counter;
    }

    @RequestMapping(path = "/happiness/today", method = RequestMethod.GET)
    public Happiness getTodaysHappiness() {
        return counter.todaysHappiness();
    }

    @RequestMapping(path = "/happiness", method = RequestMethod.GET)
    public List<Happiness> getHappinessForDateRange(@RequestParam(value = "fromDate", defaultValue = "1970-01-01")
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                    Date fromDate,
                                                    @RequestParam(value = "toDate", required = false)
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate) {
        Date safeToDate = toDate;
        if (safeToDate == null) {
            safeToDate = new Date();
        }

        return counter.happinessBetween(fromDate, safeToDate);
    }


    @RequestMapping(path = "/happiness", method = RequestMethod.PUT)
    public ResponseEntity<?> addHappiness(@RequestBody int happiness,
                                          @RequestParam(value = "date", required = false)
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                          Date date) {
        HappinessType type = HappinessType.fromInt(happiness);
        if (type == null) {
            return ResponseEntity.badRequest().build();
        }
        if (date == null) {
            counter.incrementHappiness(type);
        } else {
            counter.amendHappiness(type, date);
        }

        LOG.info("Added happiness of value " + happiness);
        return ResponseEntity.ok().build();
    }


}
