package com.learn.javasrc.test;

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
