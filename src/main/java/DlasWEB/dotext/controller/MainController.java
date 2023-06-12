package DlasWEB.dotext.controller;

import DlasWEB.dotext.exeptions.NotFoundExeption;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MainController {
    private int counter = 4;
    private List<Map<String, String>> blocksWithText = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{ put("id", "1"); put("text", "Первый блок с текстом");}});
        add(new HashMap<String, String>() {{ put("id", "2"); put("text", "Второй блок с текстом");}});
        add(new HashMap<String, String>() {{ put("id", "3"); put("text", "Третий блок с текстом");}});
    }};

    @GetMapping
    public String helloFromApi() {
        return "Ответ по запросу localhost:8080/api = Привет из REST Api dotext";
    }

    @GetMapping("text")
    public List<Map<String, String>> getAllBlocksWithTextFromApi() {
        return blocksWithText;
    }

    @GetMapping("text/{id}")
    public Map<String, String> getOneBlockWithTextByIdFromApi(@PathVariable String id) {
        return getBlockWithText(id);
    }

    private Map<String, String> getBlockWithText(@PathVariable String id) {
        return blocksWithText.stream().filter(textBlocks -> textBlocks.get("id").equals(id)).findFirst().orElseThrow(NotFoundExeption::new);
    }

    @PostMapping
    public Map<String, String> createBlockWithText(@RequestBody Map<String, String> blockWithText) {
        blockWithText.put("id", String.valueOf(counter++));
        blocksWithText.add(blockWithText);
        return blockWithText;
    }

    @PutMapping("text/{id}")
    public Map<String, String> updateBlockWithText(@PathVariable String id, @RequestBody Map<String, String> blockWithText) {
        Map<String, String> blockTextFromDb = getBlockWithText(id);
        blockTextFromDb.putAll((Map<? extends String, ? extends String>) blocksWithText);
        blockTextFromDb.put("id", id);
        return blockTextFromDb;
    }

    @DeleteMapping("text/{id}")
    public void deleteBlockWithText(@PathVariable String id) {
        Map<String, String> blockTextFromDb = getBlockWithText(id);
        blocksWithText.remove(blockTextFromDb);
    }
}
