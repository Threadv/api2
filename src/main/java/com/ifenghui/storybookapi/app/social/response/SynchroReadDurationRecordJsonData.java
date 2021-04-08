package com.ifenghui.storybookapi.app.social.response;

import java.util.List;

public class SynchroReadDurationRecordJsonData {

    List<ReadDurationRecordResponse> readDurationRecordList;

    public List<ReadDurationRecordResponse> getReadDurationRecordList() {
        return readDurationRecordList;
    }

    public void setReadDurationRecordList(List<ReadDurationRecordResponse> readDurationRecordList) {
        this.readDurationRecordList = readDurationRecordList;
    }
}
