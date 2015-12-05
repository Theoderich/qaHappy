package de.qaware.qahappy.backend.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestInterface {

    private static final Log LOG = LogFactory.getLog(RestInterface.class);


    private HappinessCounter counter;

    @Autowired
    public RestInterface(HappinessCounter counter) {
        this.counter = counter;
    }

    @RequestMapping(path = "/happiness", method = RequestMethod.GET)
    public Happiness getTodaysHappiness() {
        return counter.todaysHappiness();
    }


    @RequestMapping(path = "/happiness", method = RequestMethod.PUT)
    public ResponseEntity<?> addHappiness(@RequestBody int happiness) {
        HappinessType type = HappinessType.fromInt(happiness);
        if (type == null) {
            return ResponseEntity.badRequest().build();
        }
        counter.incrementHappiness(type);
        LOG.info("Added happiness of value " + happiness);
        return ResponseEntity.ok().build();
    }


}
