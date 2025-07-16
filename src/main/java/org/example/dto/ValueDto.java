package org.example.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ValueDto {
    private String interval;
    private long timestamp;
    private long high;
    private long low;
    private long open;
    private long close;
}