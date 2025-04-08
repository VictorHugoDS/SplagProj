package br.com.SplagProj.common;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RetornoContext<T> {
    HttpStatus status;
    String mensagem;
    T body;

    public ResponseEntity<Object> toResponseEntity(){
        if(Objects.nonNull(body)) {
            return ResponseEntity.status(this.status).body(body);
        }
        return ResponseEntity.status(this.status).body(mensagem);
    }
}
