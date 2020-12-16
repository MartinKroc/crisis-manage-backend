package com.crisis.management.dto;

import com.crisis.management.models.Employee;
import lombok.Value;

@Value
public class EmployeeDto {

    private Long id;

    private UserDto userDto;

    private String role;

    public static EmployeeDto build(Employee employee) {
        if (employee != null)
            return new EmployeeDto(employee.getId(), UserDto.build(employee.getUserId()), employee.getRole());
        else
            return null;
    }

}
