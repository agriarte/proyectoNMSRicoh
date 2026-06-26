package com.example.ricoh.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ricoh.dto.DiscoveredDevice;
import com.example.ricoh.service.DiscoveryService;


@RestController
@RequestMapping("/discovery")

public class DiscoveryController {

    private final DiscoveryService discoveryService;
    
    public DiscoveryController(DiscoveryService discoveryService) {
        this.discoveryService = discoveryService;
    }


    @GetMapping
    public List<DiscoveredDevice> scan(
            @RequestParam String subnet
    ) {
        return discoveryService.discover(subnet);
    }
}