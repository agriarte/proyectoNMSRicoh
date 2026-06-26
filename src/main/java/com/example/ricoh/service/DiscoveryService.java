package com.example.ricoh.service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.example.ricoh.dto.DiscoveredDevice;
import com.example.ricoh.snmp.SnmpClient;



@Service
public class DiscoveryService {

    private final SnmpClient snmp;
    
    public DiscoveryService(SnmpClient snmp) {
        this.snmp = snmp;
    }

    public List<DiscoveredDevice> discover(String subnet) {

        ExecutorService pool =
                Executors.newVirtualThreadPerTaskExecutor();

        List<CompletableFuture<DiscoveredDevice>> tasks =
                IntStream.rangeClosed(1, 254)
                        .mapToObj(i -> subnet + "." + i)
                        .map(ip -> CompletableFuture.supplyAsync(
                                () -> probe(ip),
                                pool))
                        .toList();

        List<DiscoveredDevice> result = tasks.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .toList();

        pool.shutdown();
        return result;
    }

    private DiscoveredDevice probe(String ip) {

        try {
            String sys = snmp.getSysDescr(ip);

            if (sys == null || sys.isBlank()) {
                return null; // ❌ no SNMP
            }

            return new DiscoveredDevice(
                    ip,
                    sys,
                    true
            );

        } catch (Exception e) {
            return null;
        }
    }
}