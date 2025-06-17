package com.gdsc.nitcconnect.routes;

import com.gdsc.nitcconnect.model.InterestGroup;
import com.gdsc.nitcconnect.service.InterestGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/test/interest-groups")
public class InterestGroupTest {

    @Autowired
    private InterestGroupService interestGroupService;

    @GetMapping
    public ResponseEntity<List<InterestGroup>> getAllInterestGroups() {
        return ResponseEntity.ok(interestGroupService.getAllInterestGroups());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterestGroup> getInterestGroupById(@PathVariable Integer id) {
        return ResponseEntity.ok(interestGroupService.getInterestGroupById(id));
    }

    @PostMapping
    public ResponseEntity<InterestGroup> createInterestGroup(@RequestBody InterestGroup interestGroup) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(interestGroupService.createInterestGroup(interestGroup));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterestGroup> updateInterestGroup(@PathVariable Integer id, @RequestBody InterestGroup interestGroup) {
        return ResponseEntity.ok(interestGroupService.updateInterestGroup(id, interestGroup));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterestGroup(@PathVariable Integer id) {
        interestGroupService.deleteInterestGroup(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<InterestGroup>> searchInterestGroups(@RequestParam String name) {
        return ResponseEntity.ok(interestGroupService.searchInterestGroupsByName(name));
    }

    @GetMapping("/creator/{createdBy}")
    public ResponseEntity<List<InterestGroup>> getInterestGroupsByCreator(@PathVariable Integer createdBy) {
        return ResponseEntity.ok(interestGroupService.getInterestGroupsByCreator(createdBy));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<InterestGroup> getInterestGroupByName(@PathVariable String name) {
        return ResponseEntity.ok(interestGroupService.getInterestGroupByName(name));
    }
}