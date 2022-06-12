package com.dboteam.pmsystem.service;

import com.dboteam.pmsystem.model.Artifact;
import com.dboteam.pmsystem.model.Attachment;
import com.dboteam.pmsystem.model.Task;
import com.dboteam.pmsystem.repository.AttachmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AttachmentService {

    private final ArtifactService artifactService;
    private final AttachmentRepository attachmentRepository;

    public Attachment createAttachment(Attachment attachment, Task task) {
        attachment.setTask(task);
        Set<Artifact> artifactSet = attachment.getArtifacts();
        // to avoid errors
        attachment.setArtifacts(new HashSet<>());
        Attachment savedAttachment = attachmentRepository.save(attachment);
        if (artifactSet != null) {
            artifactSet.forEach(artifact -> artifactService.createArtifact(artifact, savedAttachment));
        }
        savedAttachment.setArtifacts(artifactSet);
        return attachmentRepository.save(savedAttachment);
    }

    public Attachment updateAttachment(Attachment sourceAttachment, Attachment targetAttachment, Task task) {
        targetAttachment.setComment(sourceAttachment.getComment());
        Set<Artifact> artifactSet = sourceAttachment.getArtifacts();
        if (artifactSet != null) {
            artifactSet.forEach(artifact -> artifactService.createArtifact(artifact, targetAttachment));
        }
        targetAttachment.setArtifacts(artifactSet);
        return attachmentRepository.save(targetAttachment);
    }

    public void deleteAttachment(Attachment attachment) {
        attachmentRepository.delete(attachment);
    }

}
