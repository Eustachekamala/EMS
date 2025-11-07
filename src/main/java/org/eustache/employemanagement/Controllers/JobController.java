package org.eustache.employemanagement.Controllers;

import java.util.List;

import org.eustache.employemanagement.DTOs.Requests.JobRequestDTO;
import org.eustache.employemanagement.DTOs.Responses.JobResponseDTO;
import org.eustache.employemanagement.Services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/jobs")
@Tag(
    name = "Job Management",
    description = "APIs for managing job positions within the Employee Management System, including creation, retrieval, updating, and deletion of job records."
)
public class JobController {
    @Autowired
    private JobService jobService;

    @GetMapping("/{title}")
    public ResponseEntity<JobResponseDTO> getJobByTitle(
            @PathVariable String title
    ) {
        return ResponseEntity.ok(jobService.getByTitle(title));
    }

    @GetMapping("get/{id}")
    public ResponseEntity<JobResponseDTO> getJobById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(jobService.getById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<JobResponseDTO>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<String> createJob(
            @RequestBody JobRequestDTO job
    ) {
        return ResponseEntity.ok(jobService.createJob(job));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateJob(
            @PathVariable Integer id,
        @RequestBody JobRequestDTO job)
    {
        return ResponseEntity.ok(jobService.updateJob(id, job));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJob(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(jobService.deleteJob(id));
    }
}
