package org.eustache.employemanagement.Services;

import org.eustache.employemanagement.DAOs.JobRepository;
import org.eustache.employemanagement.DTOs.JobDTO;
import org.eustache.employemanagement.models.Job;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;

    public Job createJob(JobDTO job) {
        Job newJob = new Job();
        newJob.setTitle(job.title());
        newJob.setDescription(job.description());
        newJob.setSalary(job.salary());
        return jobRepository.save(newJob);
    }

    public Job updateJob(Integer id, JobDTO job) {
        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
        existingJob.setTitle(job.title());
        existingJob.setDescription(job.description());
        existingJob.setSalary(job.salary());
        return jobRepository.save(existingJob);
    }

    public  void deleteJob(Integer id) {
        jobRepository.deleteById(id);
    }

    public JobDTO getByTitle(String title) {
        Job job = jobRepository.findByTitle(title);
        if (job == null) {
            throw new RuntimeException("Job not found with title: " + title);
        }
        return new JobDTO(
                job.getTitle(),
                job.getDescription(),
                job.getSalary()
        );
    }

    public JobDTO getAll(){
        Job job = jobRepository.findAll()
            .stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No jobs found"));
        return new JobDTO(
                job.getTitle(),
                job.getDescription(),
                job.getSalary()
        );
    }
}
