package com.example.ricoh.controller;

import com.example.ricoh.service.PrinterMonitorService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/devices")
public class PrinterController {

    private final PrinterMonitorService service;

    public PrinterController(PrinterMonitorService service) {
        this.service = service;
    }

    @GetMapping("ricoh/{ip}")
    public Map<String, String> get(@PathVariable String ip) throws Exception {
        return Map.of(
            "model", service.getModel(ip),
            "serial", service.getSerial(ip),
            "status", service.getStatus(ip),
            "tonerBlack", service.getTonerBlack(ip)
        );
    }
    
    @GetMapping("win/{ip}")
    public Map<String, String> getTest(@PathVariable String ip) throws Exception {
        return Map.of(
            "Test windows", service.getSysDescr(ip),
            "Nombre", service.getName(ip)
            );
    }
}

// test 2
