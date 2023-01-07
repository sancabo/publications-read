package com.devsancabo.www.publicationsread.populator.inserter;

import com.devsancabo.www.publicationsread.dto.InserterDTO;
import com.devsancabo.www.publicationsread.dto.PublicationDTO;
import com.devsancabo.www.publicationsread.populator.inserter.AbstractDataInserter;

import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MongoInserter extends AbstractDataInserter<PublicationDTO> {

    //TODO: Complete
    public MongoInserter(final Supplier<PublicationDTO> dataProducer,
                         final Consumer<PublicationDTO> dataPersister,
                         final CountDownLatch latch/*, boolean runForever*/){
        super(100, dataProducer, dataPersister, latch, false);
    }
    @Override
    public Consumer<Supplier<Boolean>> prepareDataForDataSaver() {
        return null;
    }

    @Override
    public PublicationDTO handleDataForDataSaver() {
        return null;
    }

    @Override
    public InserterDTO getDTORepresentation() {
        return null;
    }
}
