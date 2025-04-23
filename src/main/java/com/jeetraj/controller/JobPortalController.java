package com.jobportal.controller;

import com.jobportal.model.*;
import com.jobportal.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


// @RestController
// @RequestMapping("/api/auth")
// public class AuthController {

//     @Autowired
//     private UserService userService;

//     @Autowired
//     private PasswordEncoder passwordEncoder;

//     @PostMapping("/register")
//     public ResponseEntity<?> registerUser(@RequestBody User user) {
//         user.setPassword(passwordEncoder.encode(user.getPassword()));
//         return ResponseEntity.ok(userService.saveUser(user));
//     }
// }


@RestController
@RequestMapping("/api/auth")
public class JobPortalController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private JobService jobService;

    @Autowired
    private JobApplicationService jobApplicationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(userService.saveUser(user));
    }
    
    // Roles
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/roles")
    public Role createRole(@Valid @RequestBody Role role) {
        return roleService.saveRole(role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    // Users
    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Companies
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYER')")
    @PostMapping("/companies")
    public Company createCompany(@Valid @RequestBody Company company) {
        return companyService.saveCompany(company);
    }

    @GetMapping("/companies")
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    // Jobs
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYER')")
    @PostMapping("/jobs")
    public Job createJob(@Valid @RequestBody Job job) {
        return jobService.saveJob(job);
    }

    @GetMapping("/jobs")
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    // Job Applications
    @PreAuthorize("hasRole('JOBSEEKER')")
    @PostMapping("/applications")
    public JobApplication createApplication(@Valid @RequestBody JobApplication application) {
        return jobApplicationService.saveApplication(application);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYER')")
    @GetMapping("/applications")
    public List<JobApplication> getAllApplications() {
        return jobApplicationService.getAllApplications();
    }
}
