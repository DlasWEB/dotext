package DlasWEB.dotext.repo;

import DlasWEB.dotext.model.BlockForMySql;
import DlasWEB.dotext.model.UrlInMySql;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepoMySql extends JpaRepository<UrlInMySql, Long> {
    UrlInMySql findByUrl(String url);
}
