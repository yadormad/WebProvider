package admin.block.model;

import java.util.Date;
import java.util.UUID;

public class PessimisticBlock {
    private int blockedEntityId; // id заблокированной сущности
    private long unblockTime; //время блокировки
    private UUID blockId;

    public PessimisticBlock(int blockedEntityId, long blockTimeout, UUID blockId) {
        unblockTime = new Date().getTime() + blockTimeout;
        this.blockedEntityId = blockedEntityId;
        this.blockId = blockId;
    }

    public UUID getBlockId() {
        return blockId;
    }

    public boolean isBlocked(){
        return new Date().getTime() <= unblockTime;
    }

    public int getBlockedEntityId() {
        return blockedEntityId;
    }

    public void updateUnblockTime(long blockTimeout){
        unblockTime = new Date().getTime() + blockTimeout;
    }
}
