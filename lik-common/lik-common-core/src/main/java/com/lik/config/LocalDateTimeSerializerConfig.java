package com.lik.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;

@Configuration
public class LocalDateTimeSerializerConfig {

    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String pattern;

    @Bean
    public LocalDateTimeSerializer localDateTimeSerializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern));
    }

    @Bean
    public LocalDateTimeDeserializer localDateTimeDeserializer() {
        return new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(pattern));
    }

    @Bean
    @Primary
    public ObjectMapper serializingObjectMapper(LocalDateTimeSerializer localDateTimeSerializer, LocalDateTimeDeserializer localDateTimeDeserializer) {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, localDateTimeSerializer);
        javaTimeModule.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }

//    public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
//        @Override
//        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
//                throws IOException {
//            gen.writeString(value.format(DateTimeFormatter.ofPattern(pattern)));
//        }
//    }
//
//    public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
//        @Override
//        public LocalDateTime deserialize(JsonParser p, DeserializationContext deserializationContext)
//                throws IOException {
//            return LocalDateTime.parse(p.getValueAsString(), DateTimeFormatter.ofPattern(pattern));
//        }
//    }


}