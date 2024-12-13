package com.lms.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "fee_structures")
@Data
@NoArgsConstructor
public class FeeStructure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;

    private BigDecimal tuitionFee;
    private BigDecimal labFee;
    private BigDecimal libraryFee;
    private BigDecimal otherFees;
    private String academicYear;
} 