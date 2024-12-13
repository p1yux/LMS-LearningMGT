package com.lms.service;

import com.lms.model.*;
import com.lms.repository.FeePaymentRepository;
import com.lms.repository.FeeStructureRepository;
import com.lms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeeService {
    private final FeePaymentRepository feePaymentRepository;
    private final FeeStructureRepository feeStructureRepository;
    private final UserRepository userRepository;

    @Transactional
    public FeePayment makePayment(Long studentId, Long feeStructureId, 
                                 BigDecimal amount, String paymentMethod) {
        User student = userRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        FeeStructure feeStructure = feeStructureRepository.findById(feeStructureId)
            .orElseThrow(() -> new RuntimeException("Fee structure not found"));

        FeePayment payment = new FeePayment();
        payment.setStudent(student);
        payment.setFeeStructure(feeStructure);
        payment.setAmountPaid(amount);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus("COMPLETED");
        
        return feePaymentRepository.save(payment);
    }

    public BigDecimal getTotalPaidFees(Long studentId, String academicYear) {
        User student = userRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        return feePaymentRepository
            .getTotalPaidFeesByStudentAndAcademicYear(student, academicYear);
    }

    public List<FeePayment> getPaymentHistory(Long studentId) {
        User student = userRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        return feePaymentRepository.findByStudent(student);
    }

    public BigDecimal calculatePendingFees(Long studentId, Long feeStructureId) {
        FeeStructure feeStructure = feeStructureRepository.findById(feeStructureId)
            .orElseThrow(() -> new RuntimeException("Fee structure not found"));
        
        BigDecimal totalFees = feeStructure.getTuitionFee()
            .add(feeStructure.getLabFee())
            .add(feeStructure.getLibraryFee())
            .add(feeStructure.getOtherFees());

        BigDecimal paidFees = getTotalPaidFees(studentId, feeStructure.getAcademicYear());
        return totalFees.subtract(paidFees);
    }
} 