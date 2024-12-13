package com.lms.repository;

import com.lms.model.Attendance;
import com.lms.model.Subject;
import com.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudent(User student);
    List<Attendance> findBySubject(Subject subject);
    List<Attendance> findByStudentAndSubject(User student, Subject subject);
    
    @Query("SELECT a FROM Attendance a WHERE a.student = ?1 AND a.subject = ?2 AND a.dateTime BETWEEN ?3 AND ?4")
    List<Attendance> findByStudentAndSubjectBetweenDates(User student, Subject subject, 
                                                        LocalDateTime startDate, LocalDateTime endDate);
                                                        
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student = ?1 AND a.subject = ?2 AND a.present = true")
    long countPresentAttendanceByStudentAndSubject(User student, Subject subject);
} 