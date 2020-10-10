package com.mutualfund.logic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MetaInfo {
    public String fund_house;
    public String scheme_type;
    public String scheme_category;
    public int scheme_code;
    public String scheme_name;

    public String getFund_house() {
        return fund_house;
    }

    public void setFund_house(String fund_house) {
        this.fund_house = fund_house;
    }

    public String getScheme_type() {
        return scheme_type;
    }

    public void setScheme_type(String scheme_type) {
        this.scheme_type = scheme_type;
    }

    public String getScheme_category() {
        return scheme_category;
    }

    public void setScheme_category(String scheme_category) {
        this.scheme_category = scheme_category;
    }

    public int getScheme_code() {
        return scheme_code;
    }

    public void setScheme_code(int scheme_code) {
        this.scheme_code = scheme_code;
    }

    public String getScheme_name() {
        return scheme_name;
    }

    public void setScheme_name(String scheme_name) {
        this.scheme_name = scheme_name;
    }
}
