package com.devsancabo.www.publicationsread.populator.inserter;

import com.devsancabo.www.LoremIpsum;
import com.devsancabo.www.publicationsread.dto.InserterDTO;
import com.devsancabo.www.publicationsread.dto.PublicationDTO;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MongoInserter extends AbstractDataInserter<PublicationDTO> {

    private static final int USER_RATIO = 100;
    private PublicationDTO publication = super.dataProducer.get();

    //TODO: Complete
    public MongoInserter(final Integer amountToInsert,
                         final Supplier<PublicationDTO> dataProducer,
                         final Consumer<PublicationDTO> dataPersister,
                         final CountDownLatch latch,
                         final Boolean runForever){
        super(amountToInsert, dataProducer, dataPersister, latch, runForever);
    }
    @Override
    public Consumer<Supplier<Boolean>> prepareDataForDataSaver() {
        return dataSaverFunction -> {
            this.publication = super.dataProducer.get();
            boolean calledWithError = false;
            for(int j = 0; j < USER_RATIO && !super.finished && !calledWithError; j++){
                calledWithError = dataSaverFunction.get();
            }
        };
    }

    @Override
    public PublicationDTO handleDataForDataSaver() {
        this.publication.setContent(LoremIpsum.getRandomSentence(25));
        return this.publication;
    }

    @Override
    public InserterDTO getDTORepresentation() {
        var dto = new InserterDTO();
        dto.setProperties(new HashMap<>());
        dto.getProperties().put("userRatio", Integer.toString(USER_RATIO));
        dto.setInserterClassName(this.getClass().getSimpleName());
        dto.setDescription(
                "Inserts Publications. userRatio tells how many times to reuse one user before creating a new one.");
        return dto;
    }
}
