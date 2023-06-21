package DlasWEB.dotext.controller;

import DlasWEB.dotext.model.BlockForMongo;
import DlasWEB.dotext.model.BlockForMySql;
import DlasWEB.dotext.model.UrlInMySql;
import DlasWEB.dotext.repo.BlockRepoMongoDb;
import DlasWEB.dotext.repo.BlockRepoMySql;
import DlasWEB.dotext.repo.UrlRepoMySql;
import DlasWEB.dotext.service.BlockMongoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MainController {
    private final BlockRepoMySql blockRepoMySql;
    private final BlockRepoMongoDb blockRepoMongoDb;
    private final UrlRepoMySql urlRepoMySql;
    @Autowired
    private BlockMongoService blockMongoService;
    @Autowired
    public MainController(BlockRepoMySql blockRepoMySql, BlockRepoMongoDb blockRepoMongoDb, UrlRepoMySql urlRepoMySql) {
        this.blockRepoMySql = blockRepoMySql;
        this.blockRepoMongoDb = blockRepoMongoDb;
        this.urlRepoMySql = urlRepoMySql;
    }

    @GetMapping
    public String helloFromApi() {
        return "Ответ по запросу localhost:8080/api = Привет из REST Api dotext";
    }

//    // Get all doc from Mongo
//    @GetMapping("get-all")
//    public List<BlockForMongo> getAllBlocksWithTextFromApi() {
//        return blockRepoMongoDb.findAll();
//    }

//    // Get all row from MySQL
//    @GetMapping("text")
//    @JsonView(Views.FullText.class)
//    public List<BlockForMySql> getAllBlocksWithTextFromApi() {
//        return blockRepoMySql.findAll();
//    }
    //Get text from db
    @GetMapping("get-one/{text}")
    public String getOneBlockWithText(@PathVariable String text) throws NoSuchElementException {
        try {
            String id = new String(Base64.getDecoder().decode(text));
            Optional<BlockForMySql> byId = blockRepoMySql.findById(Long.valueOf(id));
            Optional<BlockForMongo> textFromMongo = blockRepoMongoDb.findById(byId.get().getText());
            return textFromMongo.get().getText();
        }
        catch (IllegalArgumentException i) {
            return "Неправильная ссылка";
        }
        catch (NoSuchElementException n) {
            return "Ссылка или привязанная к ней запись не сушествует!";
        }
        catch (Exception e) {
            return "Что-то еще пошло не так, но это не отсутствие ссылки или текста по ней и не неправильность ссылки!";
        }
    }
    // Create one new doc in db
    @PostMapping("/create-one")
    public String createBlockWithText(@RequestBody BlockForMongo blockForMongo) {
        blockRepoMongoDb.save(blockForMongo);
        BlockForMySql blockForMySql = new BlockForMySql();
        blockForMySql.setText(blockForMongo.getId());
        blockForMySql.setCreationDate(LocalDateTime.now());
        blockRepoMySql.save(blockForMySql);
        UrlInMySql urlInMySql = new UrlInMySql();
        urlInMySql.setUrl(Base64.getEncoder().encodeToString(blockForMySql.getId().toString().getBytes()));
        return urlRepoMySql.save(urlInMySql).getUrl();
    }
    // Update one doc in db
    @PutMapping("update-one/{text}")
    public String updateBlockWithText(@PathVariable("text") String text, @RequestBody BlockForMongo block) {
        try {
            String id = new String(Base64.getDecoder().decode(text));
            Optional<BlockForMySql> byId = blockRepoMySql.findById(Long.valueOf(id));
            Optional<BlockForMongo> textFromMongo = blockRepoMongoDb.findById(byId.get().getText());
            BeanUtils.copyProperties(block, textFromMongo.get(), "id");
            blockRepoMongoDb.save(textFromMongo.get());
            return textFromMongo.get().getText();
        }
        catch (IllegalArgumentException i) {
            return "Неправильная ссылка";
        }
        catch (NoSuchElementException n) {
            return "Ссылка или привязанная к ней запись не сушествует!";
        }
        catch (Exception e) {
            return "Что-то еще пошло не так, но это не отсутствие ссылки или текста по ней и не неправильность ссылки!";
        }
    }
      // Delete one doc from Mongo
    @DeleteMapping("delete-one/{text}")
    public String deleteBlockWithText(@PathVariable("text") String text) {
        try {
            String id = new String(Base64.getDecoder().decode(text));
            Optional<BlockForMySql> byId = blockRepoMySql.findById(Long.valueOf(id));
            Optional<BlockForMongo> textFromMongo = blockRepoMongoDb.findById(byId.get().getText());
            blockRepoMongoDb.delete(textFromMongo.get());
            urlRepoMySql.delete(urlRepoMySql.findByUrl(text));
            blockRepoMySql.delete(byId.get());
            return "Текст, распологавшийся по ссылке '" + text + "' был успешно удален из базы данных!";
        }
        catch (IllegalArgumentException i) {
            return "Неправильная ссылка";
        }
        catch (NoSuchElementException n) {
            return "Ссылка или привязанная к ней запись не сушествует!";
        }
        catch (Exception e) {
            return "Что-то еще пошло не так, но это не отсутствие ссылки или текста по ней и не неправильность ссылки!";
        }
    }
}
