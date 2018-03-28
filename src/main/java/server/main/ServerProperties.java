package server.main;

import java.io.IOException;
import java.util.Properties;

public class ServerProperties {
    private static ServerProperties instance;
    private Properties serverProperties;

    private ServerProperties() {
        try {
            //ileInputStream propFile = new FileInputStream("./config.properties");
            serverProperties = new Properties();

            serverProperties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
            //propFile.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static ServerProperties getInstance(){
        if (instance == null)
            synchronized (ServerProperties.class) {
                if (instance == null)
                    instance = new ServerProperties();
            }
        return instance;
    }

    public String getTxtDatabasePath() {
        return serverProperties.getProperty("db.txt.path");
    }

    public String getDateFormat() {
        return serverProperties.getProperty("db.date.format");
    }

    public String getPostgresDatabaseURL() {
        return "jdbc:postgresql://" + serverProperties.getProperty("db.postgres.url");
    }

    public String getPostgresLogin() {
        return serverProperties.getProperty("db.postgres.login");
    }

    public String getPostgresPass() {
        return serverProperties.getProperty("db.postgres.pass");
    }


    public Integer getThreadCoreSize() {
        return Integer.parseInt(serverProperties.getProperty("thread.number.core"));
    }

    public Integer getServerPort() {
        return Integer.parseInt(serverProperties.getProperty("server.socket.port"));
    }

    public Integer getThreadMaxSize() {
        return Integer.parseInt(serverProperties.getProperty("thread.number.max"));
    }

    public Integer getThreadQueueSize() {
        return Integer.parseInt(serverProperties.getProperty("thread.number.queue"));
    }

    public Long getThreadExecTimeout() {
        return Long.parseLong(serverProperties.getProperty("thread.exec.timeout"));
//        return 40000L;
    }

    public Long getThreadWaitingBlock() {
        return Long.parseLong(serverProperties.getProperty("thread.block.wait.timeout"));
    }

    public Long getThreadPerformBlock() {
        return Long.parseLong(serverProperties.getProperty("thread.block.perf.timeout"));
    }

    public Boolean isDBTxt() {
        String isTxt = serverProperties.getProperty("db.txt");
        if (isTxt != null) {
            return Boolean.valueOf(isTxt);
        } else {
            return false;
        }
    }
}
