package org.capstone.repository;

import org.capstone.entity.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceReviewRepository extends JpaRepository <PerformanceReview, Integer> {
}
