package antifraud.repository;

import antifraud.model.Ip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IpRepository extends JpaRepository<Ip, Long> {
    Optional<Ip> findByIp(String ip);
    Boolean existsByIp(String ip);
}
