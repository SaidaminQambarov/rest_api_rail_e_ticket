package com.example.rail_e_ticket_api.dto;

import com.example.rail_e_ticket_api.entity.Destination;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StationDto {

    @NotNull
    private Destination destination;

    @NotNull
    private String name;
}
