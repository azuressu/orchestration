package com.saga.orchestrator.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saga.orchestrator.model.OrderRequest;
import com.saga.orchestrator.service.OrchestratorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orchestrator")
public class OrchestratorController {  // Saga를 시작하는 API

	private final OrchestratorService orchestratorService;

	@PostMapping("/saga")
	public String startOrchestrator(@RequestBody OrderRequest orderRequest) {
		orchestratorService.startOrchestrator(orderRequest);
		return "Saga Started Successfully!";
	}

}
