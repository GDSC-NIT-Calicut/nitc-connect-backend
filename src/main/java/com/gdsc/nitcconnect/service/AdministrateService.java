package com.gdsc.nitcconnect.service;

import com.gdsc.nitcconnect.model.Administrate;
import com.gdsc.nitcconnect.model.AdministrateId;
import com.gdsc.nitcconnect.repository.AdministrateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdministrateService {

    @Autowired
    private AdministrateRepository administrateRepository;

    // Create new administration relationship
    public Administrate assignAdministrator(Integer userId, Integer igId) {
        if (isUserAdministrator(userId, igId)) {
            throw new IllegalArgumentException("User is already an administrator of this interest group");
        }

        Administrate administrate = new Administrate(userId, igId, LocalDateTime.now());
        return administrateRepository.save(administrate);
    }

    // Remove administration relationship
    public void removeAdministrator(Integer userId, Integer igId) {
        if (!isUserAdministrator(userId, igId)) {
            throw new IllegalArgumentException("User is not an administrator of this interest group");
        }

        administrateRepository.deleteByUserIdAndIgId(userId, igId);
    }

    // Check if user is administrator of specific interest group
    public boolean isUserAdministrator(Integer userId, Integer igId) {
        return administrateRepository.existsByUserIdAndIgId(userId, igId);
    }

    // Get all administrations
    public List<Administrate> getAllAdministrations() {
        return administrateRepository.findAll();
    }

    // Get administration by composite key
    public Optional<Administrate> getAdministrationById(Integer userId, Integer igId) {
        AdministrateId id = new AdministrateId(userId, igId);
        return administrateRepository.findById(id);
    }

    // Get all interest groups administered by a user
    public List<Administrate> getInterestGroupsByUser(Integer userId) {
        return administrateRepository.findByUserId(userId);
    }

    // Get all administrators of an interest group
    public List<Administrate> getAdministratorsByInterestGroup(Integer igId) {
        return administrateRepository.findByIgId(igId);
    }

    // Count how many interest groups a user administrates
    public long countInterestGroupsByUser(Integer userId) {
        return administrateRepository.countByUserId(userId);
    }

    // Count how many administrators an interest group has
    public long countAdministratorsByInterestGroup(Integer igId) {
        return administrateRepository.countByIgId(igId);
    }

    // Update assignment date
    public Administrate updateAssignmentDate(Integer userId, Integer igId, LocalDateTime newDate) {
        Optional<Administrate> optionalAdministrate = getAdministrationById(userId, igId);

        if (optionalAdministrate.isPresent()) {
            Administrate administrate = optionalAdministrate.get();
            administrate.setAssignedAt(newDate);
            return administrateRepository.save(administrate);
        } else {
            throw new IllegalArgumentException("Administration relationship not found");
        }
    }

    // Check if user has any administrative roles
    public boolean hasAdministrativeRoles(Integer userId) {
        return countInterestGroupsByUser(userId) > 0;
    }

    // Check if interest group has any administrators
    public boolean hasAdministrators(Integer igId) {
        return countAdministratorsByInterestGroup(igId) > 0;
    }
}