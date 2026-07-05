package com.example.ricoh.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.ricoh.dto.DiscoveredDevice;
import com.example.ricoh.snmp.SnmpClient;

@Service
public class DiscoveryService {

	private final SnmpClient snmp;

	public DiscoveryService(SnmpClient snmp) {
		this.snmp = snmp;
	}

//	/**
//	 * Discovery clásico.
//	 */
//	public List<DiscoveredDevice> discover(String subnet) {
//
//		ExecutorService pool = Executors.newVirtualThreadPerTaskExecutor();
//
//		List<CompletableFuture<DiscoveredDevice>> tasks = IntStream.rangeClosed(1, 254)
//				.mapToObj(i -> subnet + "." + i)
//				.map(ip -> CompletableFuture.supplyAsync(() -> probe(ip), pool)).toList();
//
//		List<DiscoveredDevice> result = tasks.stream()
//				.map(CompletableFuture::join)
//				.filter(Objects::nonNull)
//				.toList();
//
//		pool.shutdown();
//
//		return result;
//	}

	/**
	 * Discovery mediante Server-Sent Events.
	 */
	public void discoverStream(
	        String subnet,
	        SseEmitter emitter) {

	    ExecutorService pool =
	        Executors.newVirtualThreadPerTaskExecutor();

	    try {

	        synchronized(emitter) {
	            emitter.send(
	                Map.of(
	                    "event",
	                    "START"));
	        }

	        for(int i=1;i<=254;i++) {

	            String ip =
	                subnet + "." + i;

	            CompletableFuture.runAsync(
	                () -> {

	                    try {

	                        synchronized(emitter) {
	                            emitter.send(
	                                Map.of(
	                                    "event",
	                                    "PROBING",
	                                    "ip", ip,
	                                    "time",
	                                    LocalTime.now()
	                                ));
	                        }

	                        String sys =
	                            snmp.getSysDescr(ip);

	                        if(sys != null &&
	                           !sys.isBlank()) {

	                            synchronized(emitter) {
	                                emitter.send(
	                                    Map.of(
	                                        "event",
	                                        "FOUND",
	                                        "ip", ip,
	                                        "sysDescr", sys
	                                    ));
	                            }
	                        }

	                    } catch(Exception e) {

	                    }

	                },
	                pool);
	        }

	        pool.shutdown();
	        pool.awaitTermination(
	                10,
	                TimeUnit.MINUTES);

	        synchronized(emitter) {
	            emitter.send(
	                Map.of(
	                    "event",
	                    "COMPLETE"));
	        }

	        emitter.complete();

	    } catch(Exception e) {

	        emitter.completeWithError(e);
	    }
	}
}