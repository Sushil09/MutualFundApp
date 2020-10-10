package com.mutualfund.logic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Datanumbers {
    public String date;
    public String nav;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNav() {
        return nav;
    }

    public void setNav(String nav) {
        this.nav = nav;
    }

    @Override
    public String toString() {
        return "Datanum{" +
                "date='" + date + '\'' +
                ", nav='" + nav + '\'' +
                '}';
    }
}
