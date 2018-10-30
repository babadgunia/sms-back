package org.test.sms.common.filter.general;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class AbstractFilter implements Serializable {

    private String fields;

    private String orderBy;

    private Integer offset;

    private Integer numRows;

    private Long id;
}