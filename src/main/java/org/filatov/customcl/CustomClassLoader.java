package org.filatov.customcl;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CustomClassLoader extends ClassLoader{


    @Override
    public Class<?> findClass(String name) {
        String fileName = "target/classes/" + name.replace('.', '/') + ".class";
        byte[] bytecode;
        try {
            bytecode = Files.newInputStream(Path.of(fileName)).readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return defineClass(name, bytecode, 0, bytecode.length);
    }
}
