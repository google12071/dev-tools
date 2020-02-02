package com.learn.javasrc.util;

import lombok.Data;

/**
 * @author lfq
 */
@Data
public class SortDTO {
    /**
     * 排序值
     */
    private String sortValue;

    public SortDTO(String sortValue) {
        this.sortValue = sortValue;
    }
}
