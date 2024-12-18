package tn.spring.springboot.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data

public class Cuisinier implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCuisinier;
    private String nom;
    private String prenom;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "cuisiniers", fetch = FetchType.EAGER)
    @JsonIgnore
    @ToString.Exclude
    private List<Plat> plats;

}