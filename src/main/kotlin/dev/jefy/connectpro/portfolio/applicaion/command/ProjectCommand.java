package dev.jefy.connectpro.portfolio.applicaion.command;

import org.jspecify.annotations.NullMarked;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import dev.jefy.connectpro.portfolio.applicaion.dtos.ProjectRequest;
import dev.jefy.connectpro.shared.domain.vo.ImageUrl;

/**
 * @author Jôph Yamba
 */
@NullMarked
public interface ProjectCommand {
    ProjectId create(ProjectRequest request);
    ProjectId update(ProjectId projectId, ProjectRequest request);
    ProjectId addImage(ProjectId projectId, MultipartFile image) throws IOException;
    ProjectId removeImage(ProjectId projectId, ImageUrl imageUrl) throws IOException;
}

