package server.admin.block.model;

import java.util.Date;

public class PessimisticBlock {
    private int blockedEntityId; // id заблокированной сущности
    private long unblockTime; //время блокировки

    public PessimisticBlock(int blockedEntityId, long blockTimeout) {
        unblockTime = new Date().getTime() + blockTimeout;
        this.blockedEntityId = blockedEntityId;
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
