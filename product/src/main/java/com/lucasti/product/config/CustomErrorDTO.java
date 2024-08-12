package com.lucasti.product.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.ZoneId;

@AllArgsConstructor
@Getter
@Setter
public class CustomErrorDTO {

    public CustomErrorDTO(String message){
        this.message = message;
        this.localDateTime = LocalDateTime.now(ZoneId.of("UTC"));
    }

    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;

}
