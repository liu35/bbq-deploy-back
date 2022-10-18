package com.group.bbq.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class LoginDto implements Serializable {

    @NotBlank(message = "The mailbox cannot be empty")
    private String email;

    @NotBlank(message = "The password cannot be empty")
    private String password;
}
