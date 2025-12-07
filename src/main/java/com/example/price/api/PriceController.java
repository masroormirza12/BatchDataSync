package com.example.price.api;

import com.example.price.model.PriceRecord;
import com.example.price.service.LastPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/prices")
public class PriceController {

    @Autowired
    private  LastPriceService service;

    @PostMapping("/batch/start")
    public UUID startBatch(){
        return service.startBatch();
    }

    @PostMapping("/batch/{batchId}/chunk")
    public ResponseEntity<?> uploadChunk(@PathVariable UUID batchId, @RequestBody List<PriceRecord> records){
       return service.uploadChunk(batchId, records);
    }

    @PostMapping("/batch/{batchId}/complete")
    public ResponseEntity<?> complete(@PathVariable UUID batchId){
       return service.completeBatch(batchId);
    }

    @PostMapping("/batch/{batchId}/cancel")
    public ResponseEntity<?> cancel(@PathVariable UUID batchId){
        return service.cancelBatch(batchId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLast(@PathVariable String id){
        return service.getLastPrice(id);
    }
}
