package tn.spring.springboot.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.spring.springboot.Services.Interfaces.IServices;
import tn.spring.springboot.entities.Client;
import tn.spring.springboot.entities.Cuisinier;
import tn.spring.springboot.entities.Plat;

import java.util.List;

@RestController
@RequestMapping("/Rest")
public class RestApiController {


    @Autowired
    IServices iServices;


    @PostMapping("/ajouterClient")
    @ResponseBody
    public Client ajouterClient(@RequestBody Client client) {
        return iServices.ajouterClient(client);
    }


    @PostMapping("/ajouterCuisinier")
    @ResponseBody
    public void ajouterCuisinier(@RequestBody Cuisinier cuisinier) {
        iServices.ajouterCuisinier(cuisinier);
    }

    @PutMapping("/ajouterPlatAffecterClientEtCuisinier/{idClient}/{idCuisinier}")
    @ResponseBody
    public void affecterApprenantFormation(@RequestBody Plat plat, @PathVariable("idClient") Integer idClient, @PathVariable("idCuisinier") Integer idCuisinier) {
        iServices.ajouterPlatAffecterClientEtCuisinier(plat, idClient, idCuisinier);
    }


    @GetMapping("/AfficherListePlatsParClient/{nom}/{prenom}")
    @ResponseBody
    public List<Plat> AfficherListePlatsParClient(@PathVariable("nom") String nom, @PathVariable("prenom") String prenom) {
        return iServices.AfficherListePlatsParClient(nom, prenom);
    }


    @GetMapping("/MontantApayerParClient/{idClient}")
    @ResponseBody
    public float getContrat(@PathVariable("idClient") Integer idClient) {
        return iServices.MontantApayerParClient(idClient);
    }

    @PutMapping("/ModifierImc/{idClient}/{idFormation}")
    @ResponseBody
    public void ModifierImc(@PathVariable("idClient") Integer idClient) {
        iServices.ModifierImc(idClient);
    }


}
