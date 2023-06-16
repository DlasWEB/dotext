package DlasWEB.dotext.model;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document("bloksWithText")
public class BlockForMongo {
    @Id
    private String id;
    private String text;

    public BlockForMongo() {
    }

    public BlockForMongo(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = String.valueOf(id);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "BlockForMongo{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockForMongo that = (BlockForMongo) o;
        return id.equals(that.id) && text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }
}
