package DlasWEB.dotext.service;

import DlasWEB.dotext.model.BlockForMongo;
import DlasWEB.dotext.repo.BlockRepoMongoDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockMongoService {
    @Autowired
    private BlockRepoMongoDb blockRepoMongoDb;

    public BlockForMongo updateBlockMongo(BlockForMongo blockMongoRequest){
        BlockForMongo existingBlockMongo = blockRepoMongoDb.findById(blockMongoRequest.getId()).get();
        existingBlockMongo.setText(blockMongoRequest.getText());
        return blockRepoMongoDb.save(existingBlockMongo);
    }
}
