package edu.anxolerd.inquisition.core.connection;


/**
 * Custom Interface for the basic Connection Pool
 */
public interface ExtendedConnectionPool extends ConnectionPool {
    /**
     * Method to calculate number of available connections in the pool.
     * @return number of available connections.
     */
    public int getNumberOfAvailableConnections();

    /**
     * Method to calculate number of busy connections in the pool.
     * @return number of busy connections.
     */
    public int getNumberOfBusyConnections();

    /**
     * Method to close all the connections.
     */
    public void closeAllConnections();
}
