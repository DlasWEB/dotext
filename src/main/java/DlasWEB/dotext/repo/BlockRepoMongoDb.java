package DlasWEB.dotext.repo;

import DlasWEB.dotext.model.BlockForMongo;
import DlasWEB.dotext.model.IdsFromMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface BlockRepoMongoDb extends MongoRepository<BlockForMongo, String> {
    @Query({}, fields = "{_id: 1}")
    public List<BlockForMongo> findAllIdsOnly();
}
