package fachim.raphael.spring.learn.mongo.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "perfis")
@Data
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String firstName;

    @Column(name = "sobrenome")
    private String lastName;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_usuario")
    private User user;

    protected Profile() {
    }

    public Profile(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
