package com.example.modlix.service;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import java.util.regex.Pattern;

@Service
@Slf4j
public class JsonService {

    public String validateJson(Map<String, Object> schema, Map<String, Object> data) {
    	log.info("Inside Service Method validateJson");
        List<String> errors = new ArrayList<>();
        validateObject(schema, data, errors, "root");
        return errors.isEmpty() ? "Valid JSON" : "Error: " + String.join(", ", errors);
    }
    @SuppressWarnings("unchecked")
    private void validateObject(Map<String, Object> schema, Map<String, Object> data, List<String> errors, String path) {
    	log.info("Inside Method validateObject");
    	if (!"object".equals(schema.get("type"))) {
        	log.error("Json Schema must be of type Object");
        	errors.add("Json "+path+" Schema must be of type Object");
            return;
        }

        Map<String, Object> properties = (Map<String, Object>) schema.get("properties");
        List<String> requiredFields = (List<String>) schema.get("required");

        for (String key : requiredFields) {
            if (!data.containsKey(key)) {
            	log.error("Missing required field:"+key);
                errors.add("Missing required field: " + "data" + "." + key);
            }
        }

        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            String field = entry.getKey();
            Map<String, Object> fieldSchema = (Map<String, Object>) entry.getValue();
            Object fieldValue = data.get(field);

            if (fieldValue != null) {
                validateField(fieldSchema, fieldValue, errors, "data" + "." + field);
            }
        }
    }

    @SuppressWarnings("unchecked")
	private void validateField(Map<String, Object> schema, Object value, List<String> errors, String path) {
    	log.info("Inside Method validateField");
    	String type = (String) schema.get("type");
        
        if ("string".equals(type)) {
            if (!(value instanceof String)) {
            	log.error(path + " should be a string");
                errors.add(path + " should be a string");
                return;
            }
            if (schema.containsKey("pattern")) {
                String pattern = (String) schema.get("pattern");
                if (!Pattern.matches(pattern, (String) value)) {
                	log.error(path + " does not match pattern " + "email"+ pattern);
                    errors.add(path + " does not match pattern email" + pattern);
                }
            }
        } else if ("integer".equals(type)) {
            if (!(value instanceof Integer)) {
            	log.error(path +" should be an integer");
                errors.add(path + " should be an integer");
                return;
            }
            Integer intValue = (Integer) value;
            if (schema.containsKey("minimum") && intValue < (int) schema.get("minimum")) {
            	log.error(path +" should be >= "+schema.get("minimum"));
                errors.add(path + " should be >= " + schema.get("minimum"));
            }
            if (schema.containsKey("maximum") && intValue > (int) schema.get("maximum")) {
            	log.error(path +" should be <= "+schema.get("maximum"));
            	errors.add(path + " should be <= " + schema.get("maximum"));
            }
        } else if ("boolean".equals(type)) {
            if (!(value instanceof Boolean)) {
            	log.error(path +" should be a boolean");
                errors.add(path + " should be a boolean");
            }
        } else if ("array".equals(type)) {
            if (!(value instanceof List)) {
            	log.error(path +" should be an array");
                errors.add(path + " should be an array");
                return;
            }
            List<Object> array = (List<Object>) value;
            if (schema.containsKey("items")) {
                Map<String, Object> itemSchema = (Map<String, Object>) schema.get("items");
                for (int i = 0; i < array.size(); i++) {
                    validateField(itemSchema, array.get(i), errors, path + "[" + i + "]");
                }
            }
        } else if ("object".equals(type)) {
            if (!(value instanceof Map)) {
            	log.error(path + " should be an object");
                errors.add(path + " should be an object");
                return;
            }
            validateObject(schema, (Map<String, Object>) value, errors, path);
        } else {
        	log.error(path + " has an unsupported type: " + type);
            errors.add(path + " has an unsupported type: " + type);
        }
    }
    
    @SuppressWarnings("unused")
	private boolean validateType(Object value, String expectedType) {
        switch (expectedType) {
            case "string": return value instanceof String;
            case "integer": return value instanceof Integer;
            case "boolean": return value instanceof Boolean;
            case "object": return value instanceof Map;
            case "array": return value instanceof List;
            default: return false;
        }
    }

}
