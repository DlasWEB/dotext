package DlasWEB.dotext.model;


public class IdsFromMongo {
    private String idMongo;

    public String getIdMongo() {
        return idMongo;
    }

    public void setIdMongo(String idMongo) {
        this.idMongo = idMongo;
    }

    public IdsFromMongo() {
    }

    public IdsFromMongo(String idMongo) {
        this.idMongo = idMongo;
    }

    @Override
    public String toString() {
        return "IdsFromMongo{" +
                "idMongo='" + idMongo + '\'' +
                '}';
    }
}
