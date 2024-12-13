package com.lms.service;

import com.lms.model.Attendance;
import com.lms.model.Subject;
import com.lms.model.User;
import com.lms.repository.AttendanceRepository;
import com.lms.repository.SubjectRepository;
import com.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    @Transactional
    public Attendance markAttendance(Long studentId, Long subjectId, boolean present, String remarks) {
        User student = userRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new RuntimeException("Subject not found"));

        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setSubject(subject);
        attendance.setDateTime(LocalDateTime.now());
        attendance.setPresent(present);
        attendance.setRemarks(remarks);

        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getStudentAttendance(Long studentId, Long subjectId, 
                                               LocalDateTime startDate, LocalDateTime endDate) {
        User student = userRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new RuntimeException("Subject not found"));

        return attendanceRepository.findByStudentAndSubjectBetweenDates(
            student, subject, startDate, endDate);
    }

    public double calculateAttendancePercentage(Long studentId, Long subjectId) {
        User student = userRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new RuntimeException("Subject not found"));

        long presentCount = attendanceRepository
            .countPresentAttendanceByStudentAndSubject(student, subject);
        List<Attendance> totalAttendance = attendanceRepository
            .findByStudentAndSubject(student, subject);

        if (totalAttendance.isEmpty()) {
            return 0.0;
        }

        return (presentCount * 100.0) / totalAttendance.size();
    }
} 