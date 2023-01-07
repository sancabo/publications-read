package com.devsancabo.www.publicationsread.service;

import com.devsancabo.www.publicationsread.dto.PublicationDTO;
import com.devsancabo.www.publicationsread.populator.AbstractPopulator;
import com.devsancabo.www.publicationsread.populator.inserter.AbstractDataInserter;
import com.devsancabo.www.publicationsread.populator.inserter.MongoInserter;

import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MongoPublicationPopulator extends AbstractPopulator<PublicationDTO> {
    //TODO: Complete
    public MongoPublicationPopulator(){
        super(() -> null,p -> p.toString(),100,1000);
    }
    @Override
    public AbstractDataInserter<PublicationDTO> getInserter(Integer amountToInsert,
                                                           Supplier<PublicationDTO> dataProducer,
                                                           Consumer<PublicationDTO> dataPersister,
                                                           CountDownLatch latch,
                                                           Boolean runForever) {
        return new MongoInserter(dataProducer, dataPersister, latch);
    }
}
