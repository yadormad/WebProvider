package server.admin.action.impl;

import entity.transport.TransportEntity;
import server.admin.action.Action;
import server.admin.block.PessimisticBlockEnum;

public class ExitAction implements Action {
    @Override
    public TransportEntity perform(TransportEntity request, String userId) {
        for(PessimisticBlockEnum blockEnum:PessimisticBlockEnum.values()) {
            blockEnum.clearBlocks(userId);
        }
        TransportEntity responce = new TransportEntity();
        responce.setMessage("exit ok");
        return responce;
    }
}
