package com.properties.estated.controller;

import com.properties.estated.model.Property;
import com.properties.estated.repository.InvestorRepository;
import com.properties.estated.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class PropertyController {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private InvestorRepository investorRepository;

    @GetMapping("/investor/{investorId}/properties")
    public List<Property> getPropertiesByInvestorId(@PathVariable String investorId) {
        return propertyRepository.findByInvestorId(investorId);
    }

    @PostMapping("/investor/{investorId}/properties")
    public Property addProperty(@PathVariable String investorId,
                                @Valid @RequestBody Property property) {
        return investorRepository.findById(investorId)
            .map(investor -> {
                property.setInvestor(investor);
                property.setRoomCount(property.getBedroomCount() + property.getBathroomCount());
                return propertyRepository.save(property);
            }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Investor not found with id " + investorId));
    }

    @DeleteMapping("/investor/{investorId}/properties/{propertyId}")
    public ResponseEntity<?> deleteProperty(@PathVariable String investorId,
                                            @PathVariable Long propertyId) {
        if(!investorRepository.existsById(investorId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Investor not found with id " + investorId);

        return propertyRepository.findById(propertyId)
            .map(property -> {
                propertyRepository.delete(property);
                return ResponseEntity.ok().build();
            }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found with id " + propertyId));
    }


}
