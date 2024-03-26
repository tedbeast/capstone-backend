package org.capstone.dto;


public class PerformanceStatsDto {
    private double averageScore;
    private long count;

    public PerformanceStatsDto(double averageScore, long count) {
        this.averageScore = averageScore;
        this.count = count;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

//    @Override
//    public String toString() {
//        return "PerformanceStatsDto{" +
//                "averageScore=" + averageScore +
//                ", count=" + count +
//                '}';
//    }
}
