package com.example.modlix.service;

import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Service
@Slf4j
public class PiDigitsSearchService {
    private static final String PI_FILE_PATH = "static/one-million.txt"; 
    private static final int BUFFER_SIZE = 1024 * 1024; // 1MB buffer
    
    // Knuth-Morris-Pratt (KMP) Algorithm for fast substring searching
    private int[] computeLPSArray(String pattern) {
    		log.info("Inside computeLPSArray");
            int m = pattern.length();
            int[] lps = new int[m];
            int len = 0;
            int i = 1;

            while (i < m) {
                if (pattern.charAt(i) == pattern.charAt(len)) {
                    len++;
                    lps[i] = len;
                    i++;
                } else {
                    if (len != 0) {
                        len = lps[len - 1];
                    } else {
                        lps[i] = 0;
                        i++;
                    }
                }
            }
            return lps;
        }

     public int searchInPi(String sequence) {
        		log.info("Inside Service searchInPi");
                try (InputStream inputStream = new ClassPathResource(PI_FILE_PATH).getInputStream();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream), BUFFER_SIZE)) {

                    int[] lps = computeLPSArray(sequence);
                    int m = sequence.length();
                    int totalIndex = 0;
                    int j = 0;
                    char[] buffer = new char[BUFFER_SIZE];
                    int bytesRead;
                    String previousChunk = "";

                    while ((bytesRead = reader.read(buffer)) != -1) {
                        String chunk = previousChunk + new String(buffer, 0, bytesRead);
                        int i = 0;

                        while (i < chunk.length()) {
                            if (sequence.charAt(j) == chunk.charAt(i)) {
                                j++;
                                i++;
                                if (j == m) {
                                    return totalIndex + i - m; // Found match
                                }
                            } else {
                                if (j != 0) {
                                    j = lps[j - 1];
                                } else {
                                    i++;
                                }
                            }
                        }

                        previousChunk = chunk.substring(Math.max(0, chunk.length() - m));
                        totalIndex += bytesRead;
                    }
                } catch (IOException e) {
                	log.error("IO Exception"+ e.getMessage());
                    return -1; 
                }
                return -1;
        }
}