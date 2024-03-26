package org.capstone.repository;

import org.capstone.entity.Leave;
import org.capstone.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
}