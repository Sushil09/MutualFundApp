package com.mutualfund.logic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WholeData {
    public MetaInfo metaInfo;
    public List<Datanumbers> data;
    public String status;

    public MetaInfo getMeta() {
        return metaInfo;
    }

    public void setMeta(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }

    public List<Datanumbers> getData() {
        return data;
    }

    public void setData(List<Datanumbers> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Root{" +
                "meta=" + metaInfo +
                ", data=" + data +
                ", status='" + status + '\'' +
                '}';
    }
}
