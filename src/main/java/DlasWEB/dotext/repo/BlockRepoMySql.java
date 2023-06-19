package DlasWEB.dotext.repo;

import DlasWEB.dotext.model.BlockForMySql;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepoMySql extends JpaRepository<BlockForMySql, Long> {
    BlockForMySql findByText(String text);
}
