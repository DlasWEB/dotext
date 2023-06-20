package DlasWEB.dotext.repo;

import DlasWEB.dotext.model.BlockForMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepoMongoDb extends MongoRepository<BlockForMongo, String> {
}
