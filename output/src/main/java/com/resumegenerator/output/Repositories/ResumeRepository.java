package com.resumegenerator.output.Repositories;

import com.resumegenerator.output.Models.ResumeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<ResumeModel, Long> {

}
