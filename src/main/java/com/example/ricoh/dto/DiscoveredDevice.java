package com.example.ricoh.dto;

public record DiscoveredDevice(
        String ip,
        String sysDescr,
        boolean snmpActive
) {}
