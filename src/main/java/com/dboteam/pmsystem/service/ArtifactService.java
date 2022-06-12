package com.dboteam.pmsystem.service;

import com.dboteam.pmsystem.model.Artifact;
import com.dboteam.pmsystem.model.Attachment;
import com.dboteam.pmsystem.repository.ArtifactRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArtifactService {

    private final ArtifactRepository artifactRepository;

    public Artifact createArtifact(Artifact artifact, Attachment attachment) {
        artifact.setAttachment(attachment);
        return artifactRepository.save(artifact);
    }

    public Artifact updateArtifact(Artifact sourceArtifact, Artifact targetArtifact) {
        BeanUtils.copyProperties(sourceArtifact, targetArtifact, "id", "attachment");
        return artifactRepository.save(targetArtifact);
    }

    public void deleteArtifact(Artifact artifact) {
        artifactRepository.delete(artifact);
    }
}
