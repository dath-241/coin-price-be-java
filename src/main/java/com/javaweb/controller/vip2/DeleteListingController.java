package com.javaweb.controller.vip2;

import com.javaweb.service.trigger.CRUD.ListingTriggerService;
import com.javaweb.service.trigger.CRUD.DelistingTriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/vip2")
public class DeleteListingController {

    @Autowired
    private ListingTriggerService listingTriggerService;

    @Autowired
    private DelistingTriggerService delistingTriggerService;

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTriggerCondition(@RequestParam("triggerType") String triggerType) {
        switch (triggerType.toLowerCase()) {
            case "listing":
                listingTriggerService.disableNotifications(); // Tắt tất cả thông báo cho các symbol mới
                return ResponseEntity.ok("All notifications for new listings are now disabled.");
            case "delisting":
                delistingTriggerService.disableNotifications(); // Tắt tất cả thông báo cho các delistings
                return ResponseEntity.ok("All notifications for delistings are now disabled.");
            default:
                return ResponseEntity.badRequest().body("Invalid trigger type");
        }
    }
}

