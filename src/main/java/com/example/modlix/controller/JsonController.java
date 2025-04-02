package com.example.modlix.controller;
import com.example.modlix.model.JsonValidationRequest;
import com.example.modlix.service.JsonService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class JsonController {

	    @Autowired
	    private JsonService jsonValidationService;

	    @PostMapping("/validate")
	    public String validateJson(@RequestBody JsonValidationRequest request) {
	    	log.info("Inside Controller Method validateJson");
	    	return jsonValidationService.validateJson(request.getSchema(),request.getData());
	    }

}
