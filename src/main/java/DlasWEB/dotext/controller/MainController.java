package DlasWEB.dotext.controller;

import DlasWEB.dotext.model.BlockForMongo;
import DlasWEB.dotext.model.BlockForMySql;
import DlasWEB.dotext.repo.BlockRepoMongoDb;
import DlasWEB.dotext.repo.BlockRepoMySql;
import DlasWEB.dotext.service.BlockMongoService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {
    private final BlockRepoMySql blockRepoMySql;
    private final BlockRepoMongoDb blockRepoMongoDb;

    @Autowired
    private BlockMongoService blockMongoService;

    @Autowired
    public MainController(BlockRepoMySql blockRepoMySql, BlockRepoMongoDb blockRepoMongoDb) {
        this.blockRepoMySql = blockRepoMySql;
        this.blockRepoMongoDb = blockRepoMongoDb;
    }

    @GetMapping
    public String helloFromApi() {
        return "Ответ по запросу localhost:8080/api = Привет из REST Api dotext";
    }

    // Get all doc from Mongo
    @GetMapping("text")
    public List<BlockForMongo> getAllBlocksWithTextFromApi() {

        return blockRepoMongoDb.findAll();
    }

//    // Get all row from MySQL
//    @GetMapping("text")
//    @JsonView(Views.FullText.class)
//    public List<BlockForMySql> getAllBlocksWithTextFromApi() {
//
//        return blockRepoMySql.findAll();
//    }

    //Get one doc from Mongo
    @GetMapping("text/{id}")
    public BlockForMongo getOneBlockWithTextByIdFromApi(@PathVariable("id") BlockForMongo block) {
        return block;
    }

//    //Get one row from MySQL
//    @GetMapping("text/{id}")
//    @JsonView(Views.FullText.class)
//    public BlockForMySql getOneBlockWithTextByIdFromApi(@PathVariable("id") BlockForMySql block) {
//        return block;
//    }

    // Create one new doc in Mongo
    @PostMapping("/text")
    public BlockForMySql createBlockWithText(@RequestBody BlockForMongo blockForMongo) {
        blockRepoMongoDb.save(blockForMongo);
        String md5Hex = DigestUtils.md5Hex(blockForMongo.getId());
        BlockForMySql blockForMySql = new BlockForMySql();
        blockForMySql.setText(md5Hex);
        blockForMySql.setCreationDate(LocalDateTime.now());
        return blockRepoMySql.save(blockForMySql);
    }

    // Create row for Mysql
//    @PostMapping("/text")
//    @JsonView(Views.FullText.class)
//    public BlockForMySql createBlockWithText(@RequestBody BlockForMySql blockForMySql) {
//        blockForMySql.setCreationDate(LocalDateTime.now());
//        return blockRepoMySql.save(blockForMySql);
//    }

    // Update one doc in Mongo
    @PutMapping("text/{id}")
    public BlockForMongo updateBlockWithText(
            @PathVariable("id") BlockForMongo blockFromDb,
            @RequestBody BlockForMongo block
    ) {
            BeanUtils.copyProperties(block, blockFromDb, "id");
            return blockRepoMongoDb.save(blockFromDb);
    }

//    // Update one row in Mysql
//    @PutMapping("text/{id}")
//    public BlockForMySql updateBlockWithText(
//            @PathVariable("id") BlockForMySql blockFromDb,
//            @RequestBody BlockForMySql block
//    ) {
//        BeanUtils.copyProperties(block, blockFromDb, "id");
//        return blockRepoMySql.save(blockFromDb);
//    }

    @DeleteMapping("text/{id}")
    public void deleteBlockWithText(
            @PathVariable("id") BlockForMongo block
    ) {
        blockRepoMongoDb.delete(block);
    }

//      // Delete one doc from Mongo
//    @DeleteMapping("text/{id}")
//    public void deleteBlockWithText(
//            @PathVariable("id") BlockForMySql block
//    ) {
//        blockRepoMySql.delete(block);
//    }
}
