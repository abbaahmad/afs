package antifraud.repository;

import antifraud.model.Card;
import antifraud.model.Ip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByNumber(String number);
    Boolean existsByNumber(String number);
}
