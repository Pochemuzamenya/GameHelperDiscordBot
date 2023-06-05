package org.filatov.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.filatov.handlers.util.ResourceHandler;
import org.filatov.model.Item;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileConstantUtils {

    private final ResourceHandler resourceHandler;

    public Map<String, String > getItemIdsConstant(String path) {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String , String>> typeRef = new TypeReference<HashMap<String , String >>() {};

        HashMap<String , String > map;
        try(Stream<String> lines = Files.lines(Path.of(path))) {
            String json = lines.collect(Collectors.joining());
            map = mapper.readValue(json, typeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    public Map<String, Item> getItemConstant(String path) {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Item>> typeRef = new TypeReference<HashMap<String, Item>>() {};
        Flux<String> readFile = resourceHandler.readFile(Path.of(path));
        String json = readFile.toStream().collect(Collectors.joining());
        HashMap<String, Item> map;
        try {
            map = mapper.readValue(json, typeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}
