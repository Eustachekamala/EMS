package org.eustache.employemanagement.Mappers;

import org.eustache.employemanagement.DTOs.Requests.JobRequestDTO;
import org.eustache.employemanagement.DTOs.Responses.JobResponseDTO;
import org.eustache.employemanagement.models.Job;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

@Component
@Comment("Mapper class for converting between Job entities and Job DTOs")
public class JobMapper {
    // Convert Job entity to JobDTO
    public Job toEntity(JobRequestDTO jobDTO) {
        if (jobDTO == null) return null;
        Job job = new Job();
        job.setTitle(jobDTO.title());
        job.setDescription(jobDTO.description());
        job.setSalary(jobDTO.salary());
        return job;
    }

    // Convert JobDTO to Job entity
    public JobResponseDTO toDTO(Job job) {
        if (job == null) return null;
        return new JobResponseDTO(
                job.getJobId(),
                job.getTitle(),
                job.getDescription()
        );
    }
}
