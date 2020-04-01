package com.selegant.datax.response;

import lombok.Data;

@Data
public class PageInfoResponse {

    private long totalPage;

    private long totalCount;

    private int pageNo;

    private int pageSize;

    private Object data;

}
