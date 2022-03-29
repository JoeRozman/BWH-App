package edu.wpi.energetic_easter_bunnies.database;

import java.sql.SQLException;
import java.util.List;

public interface LocationDAO {
    List<Location> getAllLocations();

    Location getLocation(int numID);

    void printLocations();

    void addLocation(Location location) throws SQLException;

    void updateLocation(Location location, String newFloor, String newNodeType) throws SQLException;

    void deleteLocation(Location location) throws SQLException;
}
