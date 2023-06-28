package DlasWEB.dotext.service;

import DlasWEB.dotext.model.BlockForMySql;
import DlasWEB.dotext.model.UrlInMySql;
import DlasWEB.dotext.repo.BlockRepoMongoDb;
import DlasWEB.dotext.repo.BlockRepoMySql;
import DlasWEB.dotext.repo.UrlRepoMySql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class BlocksWithTextService {
    private final BlockRepoMySql blockRepoMySql;
    private final BlockRepoMongoDb blockRepoMongoDb;
    private final UrlRepoMySql urlRepoMySql;

    @Autowired
    public BlocksWithTextService(BlockRepoMySql blockRepoMySql, BlockRepoMongoDb blockRepoMongoDb, UrlRepoMySql urlRepoMySql) {
        this.blockRepoMySql = blockRepoMySql;
        this.blockRepoMongoDb = blockRepoMongoDb;
        this.urlRepoMySql = urlRepoMySql;
    }

    private Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }

    @Scheduled(fixedDelayString = "PT1H")
    public void deleteBlockWithTextWithTimelifeOneHour () {
        List<BlockForMySql> blocksWithTextForDeleteList = blockRepoMySql.findAllByLifeTime("1 час");
        if (blocksWithTextForDeleteList.size() != 0) {
            for(BlockForMySql block : blocksWithTextForDeleteList) {
                try {
                    if (TimeUnit.MILLISECONDS.toHours(
                            convertToDateViaSqlTimestamp(LocalDateTime.now()).getTime() -
                                    convertToDateViaSqlTimestamp(block.getCreationDate()).getTime()) > 1) {
                        blockRepoMongoDb.delete(blockRepoMongoDb.findById(block.getText()).get());
                        urlRepoMySql.delete(Optional.ofNullable(urlRepoMySql.findByUrl(
                                Base64.getEncoder().encodeToString(block.getId().toString().getBytes()))).get());
                        blockRepoMySql.delete(block);
                    }
                }
                catch (IllegalArgumentException | NoSuchElementException ignored) {}
                catch (Exception ignored) {}
            }
        }
    }
}
