package tn.spring.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import tn.spring.springboot.entities.Categorie;
import tn.spring.springboot.entities.Cuisinier;

import java.util.List;


@EnableJpaRepositories
public interface CuisinierRepository extends JpaRepository<Cuisinier, Integer> {


    public List<Cuisinier> findAllByPlatsCategorie(Categorie plats_categorie);

    @Query("SELECT c FROM Cuisinier c JOIN c.plats p WHERE p.categorie = :plats_categorie GROUP BY c HAVING COUNT(p) >= :count")
    List<Cuisinier> findAllByPlatsCategorieAndPlatsCountGreaterThanEqual(@Param("plats_categorie") Categorie plats_categorie, @Param("count") Long count);


}
