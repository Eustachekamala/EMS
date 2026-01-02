package org.eustache.employemanagement.Services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eustache.employemanagement.DAOs.DepartmentRepository;
import org.eustache.employemanagement.DAOs.JobRepository;
import org.eustache.employemanagement.DTOs.Requests.JobRequestDTO;
import org.eustache.employemanagement.DTOs.Responses.JobResponseDTO;
import org.eustache.employemanagement.Exceptions.NotFoundException;
import org.eustache.employemanagement.Mappers.JobMapper;
import org.eustache.employemanagement.models.Department;
import org.eustache.employemanagement.models.DepartmentType;
import org.eustache.employemanagement.models.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobMapper jobMapper;
    @Autowired
    private DepartmentRepository departmentRepository;

    // Create a new Job
    public String createJob(JobRequestDTO job) {
        DepartmentType departmentType;

        // Validate and fetch or create Department
        try {
            departmentType = DepartmentType.valueOf(job.departmentName().name().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("Invalid department name: " + job.departmentName());
        }

        // Fetch existing department or create a new one
        Department department = departmentRepository.findByName(departmentType)
                .orElseGet(() -> {
                    Department newDept = new Department();
                    newDept.setName(departmentType);
                    return departmentRepository.save(newDept);
                });

        Job newJob = jobMapper.toEntity(job);
        newJob.setDepartment(department);
        jobRepository.save(newJob);

        return "Job created successfully";
    }

    // Update an existing Job
    public String updateJob(Integer id, JobRequestDTO job) {
        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Job not found with id: " + id));
        Optional.ofNullable(job.title()).ifPresent(existingJob::setTitle);
        Optional.ofNullable(job.description()).ifPresent(existingJob::setDescription);
        Optional.ofNullable(job.salary()).ifPresent(existingJob::setSalary);
        jobRepository.save(existingJob);
        return "Job updated successfully";
    }

    // Delete a Job
    public String deleteJob(Integer id) {
        jobRepository.deleteById(id);
        return "Job deleted successfully";
    }

    // Get a Job by title
    public JobResponseDTO getByTitle(String title) {
        Job job = jobRepository.findByTitle(title);
        if (job == null) {
            throw new NotFoundException("Job not found with title: " + title);
        }
        return jobMapper.toDTO(job);
    }

    // Get a Job by ID
    public JobResponseDTO getById(Integer id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Job not found with id: " + id));
        return jobMapper.toDTO(job);
    }

    // Get all Jobs
    public List<JobResponseDTO> getAll() {
        List<Job> jobs = jobRepository.findAll();
        if (jobs.isEmpty()) {
            return List.of();
        }
        return jobs.stream()
                .map(jobMapper::toDTO)
                .collect(Collectors.toList());
    }
}
