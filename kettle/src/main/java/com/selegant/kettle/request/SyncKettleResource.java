package com.selegant.kettle.request;

import lombok.Data;

/**
 * @author selegant
 */
@Data
public class SyncKettleResource {

    private String objectType;

    private String[] objectIds;
}
