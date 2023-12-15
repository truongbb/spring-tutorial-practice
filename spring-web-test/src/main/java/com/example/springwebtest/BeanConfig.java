package com.example.springwebtest;

import com.google.gson.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class BeanConfig {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {

                    @Override
                    public JsonElement serialize(LocalDate date, Type type, JsonSerializationContext jsonSerializationContext) {
                        return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE)); //'yyyy-MM-dd'
                    }
                })
                .registerTypeAdapter(LocalDate.class, new JsonDeserializer<>() {

                    @Override
                    public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                        return LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }
                })
                .create();
    }

}
