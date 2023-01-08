package com.devsancabo.www.publicationsread.populator;

import com.devsancabo.www.publicationsread.dto.PublicationDTO;
import com.devsancabo.www.publicationsread.populator.inserter.AbstractDataInserter;
import com.devsancabo.www.publicationsread.populator.inserter.MongoInserter;

import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MongoPublicationPopulator extends AbstractPopulator<PublicationDTO> {

    private static final int AMOUNT_PER_INSERTER = 1000;
    private static final int TIMEOUT_IN_MILLIS = 1000;

    //TODO: Complete
    public MongoPublicationPopulator(Supplier<PublicationDTO> dataProducer, Consumer<PublicationDTO> dataConsoomer){
        super(dataProducer,dataConsoomer, AMOUNT_PER_INSERTER, TIMEOUT_IN_MILLIS);
    }
    @Override
    public AbstractDataInserter<PublicationDTO> getInserter(Integer amountToInsert,
                                                           Supplier<PublicationDTO> dataProducer,
                                                           Consumer<PublicationDTO> dataPersister,
                                                           CountDownLatch latch,
                                                           Boolean runForever) {
        return new MongoInserter(AMOUNT_PER_INSERTER, dataProducer, dataPersister, latch, runForever);
    }
}
