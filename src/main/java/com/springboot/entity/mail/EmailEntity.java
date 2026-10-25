package com.springboot.entity.mail;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailEntity {

    private String toEmail;
    @NotBlank(message = "subject must not be blank")
    private String subject;
    private String messageBody;
    private String attachment;
}
