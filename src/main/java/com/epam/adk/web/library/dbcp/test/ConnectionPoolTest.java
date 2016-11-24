package com.epam.adk.web.library.dbcp.test;

import com.epam.adk.web.library.dbcp.ConnectionPool;
import com.epam.adk.web.library.exception.ConnectionPoolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ConnectionPoolTest class created on 18.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class ConnectionPoolTest {

    private static final Logger log = LoggerFactory.getLogger(ConnectionPoolTest.class);

    public static void main(String[] args) throws InterruptedException {

        final int NUMBERS_OF_THREAD = 45;

        final ExecutorService service = Executors.newFixedThreadPool(15);
        for (int i = 0; i < NUMBERS_OF_THREAD; i++) {
            service.execute(() -> {
                try {
                    Connection connection = ConnectionPool.getInstance().getConnection();
                    log.debug("ConnectionPoolTest Connection {}", connection);

                    Thread.sleep(5000);

                    ConnectionPool.getInstance().returnConnection(connection);

                } catch (ConnectionPoolException e) {
                    log.error("Error:  ConnectionPoolTest class. Called getConnection() method failed. {}", e);
                } catch (InterruptedException e) {
                    log.error("Error: ConnectionPoolTest class. {}", e);
                }
            });
        }

        service.shutdown();
        service.awaitTermination(5, TimeUnit.MINUTES);
        log.debug("ConnectionPool SIZE: {}", ConnectionPool.getInstance().freeConnectionsNumber());

    }
}