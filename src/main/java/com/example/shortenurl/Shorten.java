package com.example.shortenurl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Shorten {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String url;
    private String shortcode;

    @Column(name = "redirect_count")
    private Integer redirectCount;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "last_seen_date")
    private Date lastSeenDate;
    
    public Shorten() {}

    public Shorten(Long id, String url, String shortcode, Integer redirectCount, Date startDate, Date lastSeenDate) {
        this.id = id;
        this.url = url;
        this.shortcode = shortcode;
        this.redirectCount = redirectCount;
        this.startDate = startDate;
        this.lastSeenDate = lastSeenDate;
    }

    public Shorten(String url, String shortcode) {
        this.url = url;
        this.shortcode = shortcode;
        this.redirectCount = 0;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShortcode() {
        return this.shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    public Integer getRedirectCount() {
        return this.redirectCount;
    }

    public void setRedirectCount(Integer redirectCount) {
        this.redirectCount = redirectCount;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getLastSeenDate() {
        return this.lastSeenDate;
    }

    public void setLastSeenDate(Date lastSeenDate) {
        this.lastSeenDate = lastSeenDate;
    }
}