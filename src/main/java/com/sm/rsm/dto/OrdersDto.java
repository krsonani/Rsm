package com.sm.rsm.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDto {

	private int oid;

    @Positive(message="Id must be a positive value")
    private int userid;

    @Size(min = 1, message = "At least one table must be selected")
    private List<Integer> tableIds;

    @Size(min = 1, message = "At least one food item must be selected")
    private Map<Integer,Integer> foodItem;

    @Positive(message = "Total price must be a positive value")
    private double totalPrice;

}
