package org.example.studentexammanagement.repository;

import org.example.studentexammanagement.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByNameContainingIgnoreCase(String name);

    Page<Student> findByNameContainingIgnoreCase(String name, Pageable pageable);

//    Page<Student> findByNameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndPhoneContainingIgnoreCaseAndGpaGreaterThanEqualAndGpaLessThanEqual(String name, Pageable pageable);

    @Modifying
    @Query("update Student s set s.name = ?1 where s.id = ?2")
    void updateName(String name, Long id);

//    @Query(value = "update students s set s.name = :ten where s.id = :id", nativeQuery = true)
//    void updateNameVer2(String ten, Long id);

    @Modifying
    @Query("delete Student s where s.name like ?1")
    void deleteStudentByName(String name);

}
