package com.example.ricoh.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.ricoh.dto.DiscoveredDevice;
import com.example.ricoh.service.DiscoveryService;

@RestController
@RequestMapping("/discovery")

public class DiscoveryController {

	private final DiscoveryService discoveryService;

	public DiscoveryController(DiscoveryService discoveryService) {
		this.discoveryService = discoveryService;
	}

	/**
	 * EndPoint clásico.
	 *
	 * Devuelve todos los dispositivos encontrados cuando termina el escaneo.
	 */
	@GetMapping
	public List<DiscoveredDevice> scan(@RequestParam String subnet) {
		return discoveryService.discover(subnet);
	}

	/**
	 * Endpoint SSE.
	 *
	 * Va enviando eventos según se descubren.
	 */
	@GetMapping("/stream")
	public SseEmitter stream(@RequestParam String subnet) {

		SseEmitter emitter = new SseEmitter(0L);

		discoveryService.discoverStream(subnet, emitter);

		return emitter;
	}
}