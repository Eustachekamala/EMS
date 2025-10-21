package org.eustache.employemanagement.Controllers;

import org.eustache.employemanagement.DTOs.JobDTO;
import org.eustache.employemanagement.Services.JobService;
import org.eustache.employemanagement.models.Job;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @GetMapping("/{title}")
    public JobDTO getJobByTitle(
            @PathVariable String title
    ) {
        return jobService.getByTitle(title);
    }

    @PostMapping("/create")
    public Job createJob(
            @RequestBody JobDTO job
    ) {
        return jobService.createJob(job);
    }

    @PatchMapping("/update/{id}")
    public Job updateJob(
            @PathVariable Integer id,
        @RequestBody JobDTO job)
    {
        return jobService.updateJob(id, job);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteJob(
            @PathVariable Integer id
    ) {
        jobService.deleteJob(id);
    }
}
