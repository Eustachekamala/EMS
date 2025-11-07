package org.eustache.employemanagement.Controllers;

import java.util.List;

import org.eustache.employemanagement.DTOs.JobDTO;
import org.eustache.employemanagement.Services.JobService;
import org.eustache.employemanagement.models.Job;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @GetMapping("/{title}")
    public ResponseEntity<JobDTO> getJobByTitle(
            @PathVariable String title
    ) {
        return ResponseEntity.ok(jobService.getByTitle(title));
    }

    @GetMapping("/all")
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        return ResponseEntity.ok(List.of(jobService.getAll()));
    }

    @PostMapping("/create")
    public ResponseEntity<Job> createJob(
            @RequestBody JobDTO job
    ) {
        return ResponseEntity.ok(jobService.createJob(job));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Job> updateJob(
            @PathVariable Integer id,
        @RequestBody JobDTO job)
    {
        return ResponseEntity.ok(jobService.updateJob(id, job));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJob(
            @PathVariable Integer id
    ) {
        jobService.deleteJob(id);
        return ResponseEntity.ok("Job deleted successfully");
    }
}
