package com.subhasmith.indee.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TestData {
    @JsonProperty("TestData")
    private List<Data> dataList;

    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }


}
