package com.dboteam.pmsystem.repository;

import com.dboteam.pmsystem.model.Collaboration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollaborationRepository extends JpaRepository<Collaboration, Long> {
}
