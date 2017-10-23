package com.zzvcom.sysmag.pojo;

import java.util.List;

public class PagingResultDTO<T> {
    private List<T> resultList;
    private int totalCount;
    
    public List<T> getResultList() {
        return resultList;
    }
    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
