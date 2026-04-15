package dev.jefy.connectpro.shared.infrastructure.file_storage;

import org.jspecify.annotations.NullMarked;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import dev.jefy.connectpro.shared.domain.vo.ImageUrl;

/**
 * @author Jôph Yamba
 */
@NullMarked
public interface ImageService {

    ImageUrl save(MultipartFile imageFile) throws IOException;

    void delete(ImageUrl imageUrl) throws IOException;


}
