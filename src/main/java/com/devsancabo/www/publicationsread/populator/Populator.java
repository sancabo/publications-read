package com.devsancabo.www.publicationsread.populator;


import com.devsancabo.www.publicationsread.dto.GetPopulatorResponseDTO;

public sealed interface Populator<T> permits AbstractPopulator{
    GetPopulatorResponseDTO startPopulator(Integer intensity, Boolean runForever);
    void stopPopulator();
    GetPopulatorResponseDTO getPopulatorDTO();

    GetPopulatorResponseDTO resetPopulator();

}
