package server.controller.sequence;

import entity.ProviderEntity;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public class TxtIdSequence {
    private AtomicInteger maxId;

    public TxtIdSequence(Collection<? extends ProviderEntity> entities) {
        if(entities.isEmpty()){
            maxId = new AtomicInteger(0);
        } else {
            maxId = new AtomicInteger(Integer.MIN_VALUE);
            Integer id;
            for (ProviderEntity entity : entities) {
                id = entity.getId();
                if(id.compareTo(maxId.get()) > 0)
                    maxId.set(id);
            }
        }
    }

    public int getNextId(){
        return maxId.incrementAndGet();
    }
}
