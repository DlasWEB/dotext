package DlasWEB.dotext.controller;

import DlasWEB.dotext.model.Block;
import DlasWEB.dotext.model.Views;
import DlasWEB.dotext.repo.BlockRepoMySql;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {
    private final BlockRepoMySql blockRepoMySql;

    @Autowired
    public MainController(BlockRepoMySql blockRepoMySql) {
        this.blockRepoMySql = blockRepoMySql;
    }

    @GetMapping
    public String helloFromApi() {
        return "Ответ по запросу localhost:8080/api = Привет из REST Api dotext";
    }

    @GetMapping("text")
    @JsonView(Views.FullText.class)
    public List<Block> getAllBlocksWithTextFromApi() {

        return blockRepoMySql.findAll();
    }

    @GetMapping("text/{id}")
    @JsonView(Views.FullText.class)
    public Block getOneBlockWithTextByIdFromApi(@PathVariable("id") Block block) {
        return block;
    }

    @PostMapping("/text")
    @JsonView(Views.FullText.class)
    public Block createBlockWithText(@RequestBody Block block) {
        block.setCreationDate(LocalDateTime.now());
        return blockRepoMySql.save(block);
    }

    @PutMapping("text/{id}")
    public Block updateBlockWithText(
            @PathVariable("id") Block blockFromDb,
            @RequestBody Block block
    ) {
        BeanUtils.copyProperties(block, blockFromDb, "id");
        return blockRepoMySql.save(blockFromDb);
    }

    @DeleteMapping("text/{id}")
    public void deleteBlockWithText(
            @PathVariable("id") Block block
    ) {
        blockRepoMySql.delete(block);
    }
}
