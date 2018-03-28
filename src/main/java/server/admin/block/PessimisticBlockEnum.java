package server.admin.block;

import server.admin.block.model.PessimisticBlock;

import java.util.*;

public enum PessimisticBlockEnum {
    DELETE_CLIENT_BLOCK(new HashMap<>()),
    ADD_SERVICE_TO_CLIENT_BLOCK(new HashMap<>()),
    DELETE_AND_UPDATE_SERVICE_BLOCK(new HashMap<>());

    private Map<String, HashMap<Integer, PessimisticBlock>> userBlockMapping;

    PessimisticBlockEnum(Map<String, HashMap<Integer, PessimisticBlock>> userBlockMapping) {
        this.userBlockMapping = userBlockMapping;
    }

    public synchronized boolean checkPermission(int entityId, String userId) {

        if (userBlockMapping.get(userId).containsKey(entityId)) {
            return true;
        }
        Collection<HashMap<Integer, PessimisticBlock>> allBlocks = userBlockMapping.values();
        for (HashMap<Integer, PessimisticBlock> userBlocks:allBlocks) {
            Set<Integer> userBlocksIdSet = new HashSet<>(userBlocks.keySet());
            for(Integer blockedEntityId:userBlocksIdSet) {
                if(userBlocks.get(entityId).isBlocked()) {
                    return false;
                }
                if (!userBlocks.get(blockedEntityId).isBlocked()) {
                    userBlocks.remove(blockedEntityId);
                }
            }
        }
        return true;
    }

    public void addBlock(String userId, int entityId, long timeOut) {
        userBlockMapping.get(userId).put(entityId, new PessimisticBlock(entityId, timeOut));
    }

    public void clearBlocks(String userId) {
        userBlockMapping.get(userId).clear();
    }

    public void removeUser(String userId) {
        userBlockMapping.remove(userId);
    }

    public void removeBlock(String userId, int entityId) {
        userBlockMapping.get(userId).remove(entityId);
    }

    public void addUser(String userId) {
        userBlockMapping.put(userId, new HashMap<>());
    }
}
