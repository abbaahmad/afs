package antifraud.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Accessors(chain = true)
public class Ip {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ip_sequence")
    @SequenceGenerator(name = "ip_sequence", allocationSize = 1)
    private Long id;
    private String ip;
}
