package server.admin.action.impl;

import entity.transport.TransportEntity;
import server.admin.action.Action;
import server.admin.block.PessimisticBlockEnum;
import server.exceptions.DateConsecutionException;
import server.exceptions.DateFormatException;
import server.exceptions.DbAccessException;
import server.exceptions.WrongServiceTypeException;

public class ExitAction implements Action {
    @Override
    public TransportEntity perform(TransportEntity request, String userId) throws DbAccessException, WrongServiceTypeException, DateFormatException, DateConsecutionException {
        for(PessimisticBlockEnum blockEnum:PessimisticBlockEnum.values()) {
            blockEnum.clearBlocks(userId);
        }
        TransportEntity responce = new TransportEntity();
        responce.setMessage("exit ok");
        return responce;
    }
}
