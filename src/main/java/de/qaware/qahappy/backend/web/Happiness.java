package de.qaware.qahappy.backend.web;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class Happiness {

    @Id
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private int unhappy;
    private int ok;
    private int happy;

    /**
     * Constructor for JPA
     */
    public Happiness() {
    }

    public Happiness(int unhappy, int ok, int happy, Date date) {
        this.unhappy = unhappy;
        this.ok = ok;
        this.happy = happy;
        this.date = date;
    }

    public int getUnhappy() {
        return unhappy;
    }

    public int getOk() {
        return ok;
    }

    public int getHappy() {
        return happy;
    }

    public Date getDate() {
        return date;
    }

    public void increment(HappinessType type) {
        switch (type) {
            case UNHAPPY:
                ++unhappy;
                break;
            case OK:
                ++ok;
                break;
            case HAPPY:
                ++happy;
                break;
        }
    }
}
