package org.eustache.employemanagement.DAOs;

import org.eustache.employemanagement.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    public Job findByTitle(String title);
}
