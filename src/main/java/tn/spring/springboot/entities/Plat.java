package tn.spring.springboot.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data

public class Plat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int idPlat;
    private String label;
    private float prix;
    private float calories;
    @Enumerated(EnumType.STRING)
    private Categorie categorie;


    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Cuisinier> cuisiniers;

    @ManyToOne
    @JsonIgnore
    Client client;


}