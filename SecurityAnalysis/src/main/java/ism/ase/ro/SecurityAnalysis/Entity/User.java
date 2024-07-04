package ism.ase.ro.SecurityAnalysis.Entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "Users")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 3313439502727470091L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;
}