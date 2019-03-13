package com.ichanskyi.luxoft.dto;

import com.ichanskyi.luxoft.entity.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeDto {
    private Long id;
    private String email;
    private String name;
    private boolean married;
    private Date birthday;
    private Position position;
    private Long departmentId;
}
