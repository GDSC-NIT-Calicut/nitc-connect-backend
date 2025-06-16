package com.gdsc.nitcconnect.routes;

import com.gdsc.nitcconnect.model.Administrate;
import com.gdsc.nitcconnect.service.AdministrateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test/administrate")
@CrossOrigin(origins = "*")
public class AdministrateTest {

    @Autowired
    private AdministrateService administrateService;

    // Assign administrator
    @PostMapping("/assign")
    public ResponseEntity<Administrate> assignAdministrator(
            @RequestParam Integer userId,
            @RequestParam Integer igId) {
        try {
            Administrate administrate = administrateService.assignAdministrator(userId, igId);
            return new ResponseEntity<>(administrate, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Remove administrator
    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeAdministrator(
            @RequestParam Integer userId,
            @RequestParam Integer igId) {
        try {
            administrateService.removeAdministrator(userId, igId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Check if user is administrator
    @GetMapping("/check")
    public ResponseEntity<Boolean> isUserAdministrator(
            @RequestParam Integer userId,
            @RequestParam Integer igId) {
        boolean isAdmin = administrateService.isUserAdministrator(userId, igId);
        return new ResponseEntity<>(isAdmin, HttpStatus.OK);
    }

    // Get all administrations
    @GetMapping("/all")
    public ResponseEntity<List<Administrate>> getAllAdministrations() {
        List<Administrate> administrations = administrateService.getAllAdministrations();
        return new ResponseEntity<>(administrations, HttpStatus.OK);
    }

    // Get interest groups by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Administrate>> getInterestGroupsByUser(@PathVariable Integer userId) {
        List<Administrate> administrations = administrateService.getInterestGroupsByUser(userId);
        return new ResponseEntity<>(administrations, HttpStatus.OK);
    }

    // Get administrators by interest group
    @GetMapping("/ig/{igId}")
    public ResponseEntity<List<Administrate>> getAdministratorsByInterestGroup(@PathVariable Integer igId) {
        List<Administrate> administrations = administrateService.getAdministratorsByInterestGroup(igId);
        return new ResponseEntity<>(administrations, HttpStatus.OK);
    }
}