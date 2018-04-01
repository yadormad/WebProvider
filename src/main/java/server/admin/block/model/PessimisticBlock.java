package server.admin.block.model;

import java.util.Date;

public class PessimisticBlock {
    private int blockedEntityId; // id заблокированной сущности
    private long unblockTime; //время блокировки
    private boolean isPrepared;

    public PessimisticBlock(int blockedEntityId, long blockTimeout, boolean isPrepared) {
        this.isPrepared = isPrepared;
        unblockTime = new Date().getTime() + blockTimeout;
        this.blockedEntityId = blockedEntityId;
    }

    public boolean isPrepared() {
        return isPrepared;
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
