package com.example.modlix.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.modlix.service.PiDigitsSearchService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PiDigitsSearchController {
	private PiDigitsSearchService piSearchService;

	public PiDigitsSearchController(PiDigitsSearchService piSearchService) {
	        this.piSearchService = piSearchService;
	    }
	
    @GetMapping("/search")
    public int searchPi(@RequestParam String sequence) {
    	log.info("Inside controller searchPi ");
        return piSearchService.searchInPi(sequence);
    }
}
