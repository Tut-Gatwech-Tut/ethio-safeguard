package com.ethiosafeguard.controller;

import com.ethiosafeguard.model.LocationData;
import com.ethiosafeguard.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/locations")
@CrossOrigin(origins = "*")
public class LocationController {
    
    @Autowired
    private LocationService locationService;
    
    @PostMapping
    public ResponseEntity<LocationData> updateLocation(@RequestBody LocationData location) {
        try {
            LocationData saved = locationService.saveLocation(location);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{truckId}")
    public ResponseEntity<List<LocationData>> getTruckLocations(
            @PathVariable String truckId,
            @RequestParam(defaultValue = "10") int limit) {
        List<LocationData> locations = locationService.getRecentLocations(truckId, limit);
        return ResponseEntity.ok(locations);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<String>> getActiveTrucks() {
        List<String> activeTrucks = locationService.getActiveTrucks();
        return ResponseEntity.ok(activeTrucks);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Backend is healthy - " + 
            System.getenv().getOrDefault("NODE_ID", "standalone"));
    }
}
