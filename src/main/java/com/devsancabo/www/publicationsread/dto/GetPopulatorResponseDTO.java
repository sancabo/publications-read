package com.devsancabo.www.publicationsread.dto;

import com.devsancabo.www.publicationsread.populator.AbstractPopulator;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPopulatorResponseDTO {
    private AbstractPopulator.Status status;
    private Integer insetionsPerThread;
    private Boolean runForever;
    private Integer intensity;
    private Integer inserterCount;
    private Long activeInserterCount;

    private InserterDTO inserterSpec;
}
