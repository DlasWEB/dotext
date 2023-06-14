package DlasWEB.dotext.controller;

import DlasWEB.dotext.model.Block;
import DlasWEB.dotext.model.Views;
import DlasWEB.dotext.repo.BlockRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MainController {
    private final BlockRepo blockRepo;

    @Autowired
    public MainController(BlockRepo blockRepo) {
        this.blockRepo = blockRepo;
    }

    @GetMapping
    public String helloFromApi() {
        return "Ответ по запросу localhost:8080/api = Привет из REST Api dotext";
    }

    @GetMapping("text")
    @JsonView(Views.IdName.class)
    public List<Block> getAllBlocksWithTextFromApi() {

        return blockRepo.findAll();
    }

    @GetMapping("text/{id}")
    public Block getOneBlockWithTextByIdFromApi(@PathVariable("id") Block block) {
        return block;
    }

    @PostMapping("/text")
    public Block createBlockWithText(@RequestBody Block block) {
        block.setCreationDate(LocalDateTime.now());
        return blockRepo.save(block);
    }

    @PutMapping("text/{id}")
    public Block updateBlockWithText(
            @PathVariable("id") Block blockFromDb,
            @RequestBody Block block
    ) {
        BeanUtils.copyProperties(block, blockFromDb, "id");
        return blockRepo.save(blockFromDb);
    }

    @DeleteMapping("text/{id}")
    public void deleteBlockWithText(
            @PathVariable("id") Block block
    ) {
        blockRepo.delete(block);
    }
}
