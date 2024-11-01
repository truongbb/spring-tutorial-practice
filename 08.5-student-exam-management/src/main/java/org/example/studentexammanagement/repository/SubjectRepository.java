package org.example.studentexammanagement.repository;

import org.example.studentexammanagement.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
