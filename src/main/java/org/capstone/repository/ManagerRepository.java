package org.capstone.repository;

import org.capstone.entity.Employee;
import org.capstone.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
}
