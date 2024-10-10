package com.project.bridgetalkbackend.repository;

import com.project.bridgetalkbackend.domain.Schools;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SchoolsRepository extends JpaRepository<Schools, UUID> {
    public Schools findBySchoolName(String SchoolName);
    public boolean existsBySchoolName(String SchoolName);
}
