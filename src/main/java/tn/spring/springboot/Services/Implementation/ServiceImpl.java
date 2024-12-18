package tn.spring.springboot.Services.Implementation;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.spring.springboot.Services.Interfaces.IServices;
import tn.spring.springboot.entities.*;
import tn.spring.springboot.repositories.ClientRepository;
import tn.spring.springboot.repositories.CuisinierRepository;
import tn.spring.springboot.repositories.PlatRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@Slf4j
public class ServiceImpl implements IServices {

    @Autowired
    PlatRepository platRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CuisinierRepository cuisinierRepository;


    @Override
    public Client ajouterClient(Client client) {
        log.info("Le client " + client.getNom() + " " + client.getPrenom() + " a été ajouté");
        return clientRepository.save(client);
    }

    @Override
    public void ajouterCuisinier(Cuisinier cuisinier) {
        log.info("Le cuisinier " + cuisinier.getNom() + " " + cuisinier.getPrenom() + " a été ajouté");
        cuisinierRepository.save(cuisinier);
    }

    @Override
    public void ajouterPlatAffecterClientEtCuisinier(Plat plat, Integer idClient, Integer idCuisinier) {

        Client client = clientRepository.findById(idClient).orElseThrow(
                () -> new RuntimeException("Client not found for this id :: " + idClient));

        Cuisinier cuisinier = cuisinierRepository.findById(idCuisinier).orElseThrow(
                () -> new RuntimeException("Cuisinier not found for this id :: " + idCuisinier));


        if (plat.getCategorie().equals(Categorie.PRINCIPAL) && platRepository.countByClientAndCategorie(client, Categorie.PRINCIPAL) >= 2)
            throw new RuntimeException("Client can't have more than 2 principal plats");

        plat.setClient(client);
        plat.setCuisiniers(Stream.of(cuisinier).collect(Collectors.toList()));
        log.info("Le plat " + plat.getCategorie() + " a été ajouté et affecté au client " + client.getNom() + " " + client.getPrenom() + " et au cuisinier " + cuisinier.getNom() + " " + cuisinier.getPrenom());

        platRepository.save(plat);
    }

    @Override
    public List<Plat> AfficherListePlatsParClient(String nom, String prenom) {
        return platRepository.findAllByClientNomAndClientPrenom(nom, prenom);
    }

    @Override
    public float MontantApayerParClient(Integer idClient) {

        Client client = clientRepository.findById(idClient).orElseThrow(
                () -> new RuntimeException("Client not found for this id :: " + idClient));
        float montant = 0;
        List<Plat> plats = platRepository.findAllByClientNomAndClientPrenom(client.getNom(), client.getPrenom());
        // 1ere methode
        // *****   montant += plats.stream().map(Plat::getPrix).reduce(0f, Float::sum);
        // 2eme methode
        for (int i = 0; i < plats.size(); i++) {
            montant += plats.get(i).getPrix();
        }
        log.info("Le montant à payer par le client " + client.getNom() + " " + client.getPrenom() + " est de " + montant);
        return montant;
    }

    @Override
    @Scheduled(fixedRate = 15000)
    public void AfficherListeCuisinier() {

        List<Cuisinier> cuisinierList = cuisinierRepository.findAllByPlatsCategorieAndPlatsCountGreaterThanEqual(Categorie.PRINCIPAL, 2L);
        log.info("liste des cuisinier ayant prepare au min deux plats de categorie PRINCIPAL: ");
        for (int i = 0; i < cuisinierList.size(); i++) {
            log.info(" nom du cuisinier : " + cuisinierList.get(i).getNom() + " **prenom du cuisnier : " + cuisinierList.get(i).getPrenom() + " **total de ses plats " + cuisinierList.get(i).getPlats().size());
        }

    }

    @Override
    public void ModifierImc(Integer idClient) {
        Client client = clientRepository.findById(idClient).orElseThrow(
                () -> new RuntimeException("Client not found for this id :: " + idClient));
        List<Plat> plats = platRepository.findAllByClientNomAndClientPrenom(client.getNom(), client.getPrenom());
        float sumCalories = platRepository.sumCaloriesByClient(idClient);
        if (sumCalories < 2000) {
            client.setImc(Imc.FAIBLE);
        } else if (sumCalories == 2000) {
            client.setImc(Imc.IDEAL);

        } else if (sumCalories > 2000) {
            client.setImc(Imc.FORT);
        }
        clientRepository.save(client);
    }
}
