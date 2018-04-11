package admin.block;

import admin.block.model.PessimisticBlock;

import java.util.*;

public enum PessimisticBlockEnum {
    DELETE_CLIENT_BLOCK(new HashMap<>()),
    ADD_SERVICE_TO_CLIENT_BLOCK(new HashMap<>()),
    DELETE_AND_UPDATE_SERVICE_BLOCK(new HashMap<>());

    private Map<String, HashMap<Integer, PessimisticBlock>> userBlockMapping;

    PessimisticBlockEnum(Map<String, HashMap<Integer, PessimisticBlock>> userBlockMapping) {
        this.userBlockMapping = userBlockMapping;
    }

    public synchronized UUID checkPermission(int entityId, String userId, UUID blockId, long timeout) {
        System.out.println("Check block for " + userId);
        /*if (userBlockMapping.get(userId).containsKey(entityId)) {
            return true;
        }*/
        Set<String> userIdKeySet = userBlockMapping.keySet();
        for (String userIdFromKeySet:userIdKeySet) {
            Map<Integer, PessimisticBlock> userBlocks = userBlockMapping.get(userIdFromKeySet);
            Set<Integer> entityIdKeySet = userBlocks.keySet();
            for (Integer entityIdFromKeySet:entityIdKeySet) {
                if(userBlocks.get(entityId) != null && userBlocks.get(entityId).isBlocked()) {
                    if (userIdFromKeySet.equals(userId)) {
                        if(userBlocks.get(entityId).getBlockId().equals(blockId)) {
                            userBlocks.get(entityId).updateUnblockTime(timeout);
                            return blockId;
                        } else {
                            return null;
                        }
                    } else {
                        return null;
                    }
                }
                if (!userBlocks.get(entityIdFromKeySet).isBlocked()) {
                   removeBlock(userIdFromKeySet, entityIdFromKeySet);
                }
            }
        }
        return addBlock(userId, entityId, timeout);
    }

    public UUID addBlock(String userId, int entityId, long timeOut) {
        UUID blockId = UUID.randomUUID();
        userBlockMapping.get(userId).put(entityId, new PessimisticBlock(entityId, timeOut, blockId));
        return blockId;
    }

    public void clearBlocks(String userId) {
        userBlockMapping.get(userId).clear();
    }

    public void removeUser(String userId) {
        userBlockMapping.remove(userId);
    }

    private void removeBlock(String userId, int entityId) {
        userBlockMapping.get(userId).remove(entityId);
    }

    public void removeBlock(String userId, int entityId, UUID blockId) {
        if (userBlockMapping.get(userId).get(entityId).getBlockId().equals(blockId)) {
            userBlockMapping.get(userId).remove(entityId);
        }
    }

    public void addUser(String userId) {
        System.out.println(userId + " added to block manager.");
        userBlockMapping.put(userId, new HashMap<>());
    }
}
