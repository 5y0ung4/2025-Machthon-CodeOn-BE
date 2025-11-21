package com.codeon.sweet_choice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // 값이 없으면 JSON에 표시 안 함
public class SugarRecordResponseDto {

    private Long recordId;
    private String foodName;
    private Integer count;
    private Double calcSugar;

    private LocalDateTime recordDate;
    private List<DailyRecordResponseDto.Detail> details;
}