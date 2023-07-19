package travelfeeldog.global.file.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travelfeeldog.global.file.domain.model.ImageFile;


@Repository
public interface GlobalImageRepository extends JpaRepository<ImageFile,Long> {

}
