package com.dboteam.pmsystem.repository;

import com.dboteam.pmsystem.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Position, Long> {
}
