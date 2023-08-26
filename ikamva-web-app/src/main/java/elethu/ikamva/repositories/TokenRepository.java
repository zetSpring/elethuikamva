package elethu.ikamva.repositories;

import elethu.ikamva.domain.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query(
            value =
                    """
      SELECT t FROM Token t INNER JOIN User u\s
      ON t.user.id = u.id\s
      WHERE u.id = :id AND (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokensByUser(Long id);

    Optional<Token> findByToken(String token);
}
