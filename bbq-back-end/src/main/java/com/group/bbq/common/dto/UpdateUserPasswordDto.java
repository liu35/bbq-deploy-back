package com.group.bbq.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateUserPasswordDto {

    @NotNull(message = "id can't be empty")
    private Long id;
    @NotBlank(message = "password can't be empty")
    private String password;
}
