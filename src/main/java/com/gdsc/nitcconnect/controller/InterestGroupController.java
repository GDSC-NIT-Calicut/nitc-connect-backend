package com.gdsc.nitcconnect.controller;

import com.gdsc.nitcconnect.dto.AdminDTO;
import com.gdsc.nitcconnect.model.Administrate;
import com.gdsc.nitcconnect.model.InterestGroup;
import com.gdsc.nitcconnect.service.AdministrateService;
import com.gdsc.nitcconnect.service.InterestGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class InterestGroupController {

    private final InterestGroupService interestGroupService;
    private final AdministrateService administrateService;


    public InterestGroupController(InterestGroupService interestGroupService, AdministrateService administrateService) {
        this.interestGroupService = interestGroupService;
        this.administrateService = administrateService;
    }

    // Get all groups
    @GetMapping
    public ResponseEntity<List<InterestGroup>> getAllGroups() {
        return ResponseEntity.ok(interestGroupService.getAllInterestGroups());
    }

    // Get group info and posts
    @GetMapping("/{groupId}")
    public ResponseEntity<InterestGroup> getGroupById(@PathVariable Integer groupId) {
        return ResponseEntity.ok(interestGroupService.getInterestGroupById(groupId));
    }

    // 3. Create group
    @PostMapping
    public ResponseEntity<InterestGroup> createGroup(@RequestBody InterestGroup group) {
        Integer currentUserId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Incoming group: " + group + ", created by: " + currentUserId);

        InterestGroup saved = interestGroupService.createInterestGroup(group, currentUserId);
        System.out.println("Saved group: " + saved);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Edit group
    @PutMapping("/{groupId}")
    public ResponseEntity<InterestGroup> updateGroup(
            @PathVariable Integer groupId,
            @RequestBody InterestGroup interestGroup
    ) {
        System.out.println("Updating group " + groupId + " with " + interestGroup);
        return ResponseEntity.ok(interestGroupService.updateInterestGroup(groupId, interestGroup));
    }

    // Delete group
    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Integer groupId) {
        System.out.println("Deleting group with ID: " + groupId);
        interestGroupService.deleteInterestGroup(groupId);
        return ResponseEntity.noContent().build();
    }

    // Search route
    @GetMapping("/search")
    public ResponseEntity<List<InterestGroup>> searchGroups(@RequestParam String name) {
        return ResponseEntity.ok(interestGroupService.searchInterestGroupsByName(name));
    }

    // Get groups created by a specific user
    @GetMapping("/creator/{userId}")
    public ResponseEntity<List<InterestGroup>> getGroupsByCreator(@PathVariable Integer userId) {
        return ResponseEntity.ok(interestGroupService.getInterestGroupsByCreator(userId));
    }

    // Get admins for a specific group
    @GetMapping("/{groupId}/admins")
    public ResponseEntity<List<AdminDTO>> getAdmins(@PathVariable Integer groupId) {
        List<AdminDTO> admins = administrateService.getAdminDTOsByInterestGroup(groupId);
        return ResponseEntity.ok(admins);
    }

    // Add an admin for a specific group
    @PostMapping("/{groupId}/add-admin/{userId}")
    public ResponseEntity<Administrate> addAdmin(@PathVariable Integer groupId, @PathVariable Integer userId) {
        Integer currentUserId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!interestGroupService.isUserGroupOwner(groupId, currentUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only group owner can add admins");
        }

        Administrate admin = administrateService.assignAdministrator(userId, groupId);
        return ResponseEntity.status(HttpStatus.CREATED).body(admin);
    }


    // Remove an admin for a specific group
    @DeleteMapping("/{groupId}/remove-admin/{userId}")
    public ResponseEntity<Void> removeAdmin(@PathVariable Integer groupId, @PathVariable Integer userId) {
        Integer currentUserId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Removing admin with ID: " + userId + " by " + currentUserId);
        if (!interestGroupService.isUserGroupOwner(groupId, currentUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only group owner can remove admins");
        }

        administrateService.removeAdministrator(userId, groupId);
        return ResponseEntity.noContent().build();
    }


}
