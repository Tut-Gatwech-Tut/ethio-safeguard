package com.ethiosafeguard.repository;

import com.ethiosafeguard.model.LocationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<LocationData, Long> {
    
    List<LocationData> findByTruckIdOrderByTimestampDesc(String truckId);
    
    @Query("SELECT l FROM LocationData l WHERE l.truckId = :truckId AND l.timestamp >= :since")
    List<LocationData> findRecentLocations(@Param("truckId") String truckId, 
                                           @Param("since") LocalDateTime since);
    
    @Query("SELECT DISTINCT l.truckId FROM LocationData l WHERE l.timestamp >= :since")
    List<String> findActiveTrucks(@Param("since") LocalDateTime since);
}
