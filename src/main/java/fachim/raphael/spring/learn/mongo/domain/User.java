package fachim.raphael.spring.learn.mongo.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "usuarios")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(name = "usuario")
    private String user;

    @Column(name = "senha")
    private String password;

    @Column(name = "ativo")
    private Boolean active;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Profile profile;

    protected User(){

    }
    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.user = getUserByEmail();
        this.active = Boolean.TRUE;
    }

    public String getUserByEmail() {
        return this.email.split("@")[0];
    }

    public void addProfile(Profile profile){
        setProfile(profile);
    }
}
