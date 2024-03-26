package org.capstone.repository;
//import org.capstone.dto.PerformanceStatsDto;
import org.capstone.dto.PerformanceStatsProjection;
import org.capstone.entity.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//import java.util.List;

public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview,Integer> {
    @Query("SELECT avg(pr.averageScore) as averageScore, count(pr) as count FROM PerformanceReview pr")
    PerformanceStatsProjection findPerformanceStats();
    //PerformanceStatsDto findPerformanceStats();
    //List<PerformanceReview> findByAverageScore(Double averageScore);

}
