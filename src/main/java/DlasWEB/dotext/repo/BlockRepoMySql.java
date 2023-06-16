package DlasWEB.dotext.repo;

import DlasWEB.dotext.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepoMySql extends JpaRepository<Block, Long> {
}
