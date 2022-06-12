package com.dboteam.pmsystem.repository;

import com.dboteam.pmsystem.model.Position;
import com.dboteam.pmsystem.model.PositionName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    Optional<Position> findByPositionName(PositionName positionName);
}
