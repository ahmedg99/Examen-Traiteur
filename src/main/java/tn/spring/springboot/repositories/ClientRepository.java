package tn.spring.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import tn.spring.springboot.entities.Client;


@EnableJpaRepositories
public interface ClientRepository extends JpaRepository<Client, Integer> {


}
