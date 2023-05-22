package org.filatov.handlers.util;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Component
public class ResourceHandler {

    public Flux<String> readFile(Path path) {
        return Flux.using(
                () -> Files.lines(path, StandardCharsets.UTF_8),
                Flux::fromStream,
                Stream::close
        );
    }
    public void close(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void write(BufferedWriter writer, String data) {
        try {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void nioWrite(File outFile, String data) {
        try (WritableByteChannel out = Channels.newChannel(new FileOutputStream(outFile))){
            byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            while (buffer.hasRemaining()) {
                out.write(buffer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String nioRead(File inFile) {
        StringBuilder builder = new StringBuilder();
        try (ReadableByteChannel in = Channels.newChannel(new FileInputStream(inFile))){
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (in.read(buffer) > 0) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    char ch = (char) buffer.get();
                    builder.append(ch);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }
}
