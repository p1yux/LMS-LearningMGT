package com.lms.repository;

import com.lms.model.FeePayment;
import com.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FeePaymentRepository extends JpaRepository<FeePayment, Long> {
    List<FeePayment> findByStudent(User student);
    
    @Query("SELECT SUM(fp.amountPaid) FROM FeePayment fp WHERE fp.student = ?1 AND fp.feeStructure.academicYear = ?2")
    BigDecimal getTotalPaidFeesByStudentAndAcademicYear(User student, String academicYear);
    
    @Query("SELECT fp FROM FeePayment fp WHERE fp.student = ?1 AND fp.status = ?2")
    List<FeePayment> findByStudentAndStatus(User student, String status);
} 