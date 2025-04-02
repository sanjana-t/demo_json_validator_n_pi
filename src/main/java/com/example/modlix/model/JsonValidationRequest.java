package com.example.modlix.model;
import java.util.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@SuppressWarnings("unused")
public class JsonValidationRequest {
	private Map<String, Object> schema;
    private Map<String, Object> data;
}
