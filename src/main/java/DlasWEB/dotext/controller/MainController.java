package DlasWEB.dotext.controller;

import DlasWEB.dotext.model.BlockForMongo;
import DlasWEB.dotext.model.BlockForMySql;
import DlasWEB.dotext.repo.BlockRepoMongoDb;
import DlasWEB.dotext.repo.BlockRepoMySql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MainController {
    private final BlockRepoMySql blockRepoMySql;
    private final BlockRepoMongoDb blockRepoMongoDb;

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
    public BlockForMongo createBlockWithText(@RequestBody BlockForMongo blockForMongo) {
        return blockRepoMongoDb.save(blockForMongo);
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
            @PathVariable("id") BlockForMongo blockFromDb
           , @RequestBody BlockForMongo block
    ) { 
            return blockForMongo.setText(block.getText());
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
            @PathVariable("id") BlockForMySql block
    ) {
        blockRepoMySql.delete(block);
    }
}
