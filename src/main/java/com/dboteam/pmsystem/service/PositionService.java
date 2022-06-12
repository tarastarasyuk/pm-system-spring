package com.dboteam.pmsystem.service;

import com.dboteam.pmsystem.exception.NoSuchEntityException;
import com.dboteam.pmsystem.model.Position;
import com.dboteam.pmsystem.model.PositionName;
import com.dboteam.pmsystem.repository.PositionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    public Position getPositionByPositionName(PositionName positionName) {
        return positionRepository.findByPositionName(positionName).orElseThrow(NoSuchEntityException::new);
    }
}
