package com.properties.estated.controller;

import com.properties.estated.model.Investor;
import com.properties.estated.repository.InvestorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class InvestorController {

    @Autowired
    private InvestorRepository investorRepository;

    @GetMapping("/investor")
    public List<Investor> getInvestors() {
        return investorRepository.findAll();
    }

    @GetMapping("/investor/{investorId}")
    public Optional<Investor> getInvestor(@PathVariable String investorId) {
        return investorRepository.findById(investorId);
    }

    @PostMapping("/investor")
    public Investor createInvestor(@Valid @RequestBody Investor investor) {
        return investorRepository.save(investor);
    }

    @DeleteMapping("/investor/{investorId}")
    public ResponseEntity<?> deleteInvestor(@PathVariable String investorId) {
        return investorRepository.findById(investorId)
            .map(investor -> {
                investorRepository.delete(investor);
                return ResponseEntity.ok().build();
            }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Investor not found with id " + investorId));
    }

}
