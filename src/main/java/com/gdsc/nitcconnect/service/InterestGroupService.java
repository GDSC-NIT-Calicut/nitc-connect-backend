package com.gdsc.nitcconnect.service;

import com.gdsc.nitcconnect.model.InterestGroup;
import com.gdsc.nitcconnect.repository.InterestGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InterestGroupService {

    @Autowired
    private InterestGroupRepository interestGroupRepository;

    public List<InterestGroup> getAllInterestGroups() {
        return interestGroupRepository.findAllByOrderByCreatedAtDesc();
    }

    public InterestGroup getInterestGroupById(Integer igId) {
        return interestGroupRepository.findById(igId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Interest Group not found with id: " + igId
                ));
    }

    public InterestGroup createInterestGroup(InterestGroup group, Integer currentUserId) {
        if (interestGroupRepository.existsByNameIgnoreCase(group.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Interest Group already exists");
        }

        group.setCreatedAt(LocalDateTime.now());
        group.setCreatedBy(currentUserId);

        return interestGroupRepository.save(group);
    }

    public InterestGroup updateInterestGroup(Integer igId, InterestGroup updatedInterestGroup) {
        InterestGroup existingGroup = getInterestGroupById(igId);

        // Check if new name conflicts with existing groups (excluding current one)
        if (!existingGroup.getName().equalsIgnoreCase(updatedInterestGroup.getName()) &&
                interestGroupRepository.existsByNameIgnoreCase(updatedInterestGroup.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Interest Group already exists"
            );
        }

        existingGroup.setName(updatedInterestGroup.getName());
        existingGroup.setDescription(updatedInterestGroup.getDescription());

        return interestGroupRepository.save(existingGroup);
    }

    public void deleteInterestGroup(Integer igId) {
        if (!interestGroupRepository.existsById(igId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Interest Group not found with id: " + igId
            );
        }
        interestGroupRepository.deleteById(igId);
    }

    public List<InterestGroup> searchInterestGroupsByName(String name) {
        return interestGroupRepository.findByNameContainingIgnoreCase(name);
    }

    public List<InterestGroup> getInterestGroupsByCreator(Integer createdBy) {
        return interestGroupRepository.findByCreatedBy(createdBy);
    }

    public InterestGroup getInterestGroupByName(String name) {
        return interestGroupRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Interest Group not found with name: " + name
        ));
    }

    // Business method - check if user can join group
    public boolean canUserJoinGroup(Integer igId, Integer userId) {
        InterestGroup group = getInterestGroupById(igId);
        // Add your business logic here
        // For example: check if user is already a member, check group capacity, etc.
        return true; // Placeholder
    }

    public boolean isUserGroupOwner(Integer groupId, Integer currentUserId) {
        InterestGroup group = getInterestGroupById(groupId);
        return group.getCreatedBy().equals(currentUserId);
    }
}