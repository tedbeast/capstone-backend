package org.capstone.repository;

import org.capstone.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {
}