package com.sm.rsm.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;
import com.sm.rsm.model.Food;
import com.sm.rsm.model.Tables;
import com.sm.rsm.model.Users;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDto {
	@NotNull(message = "Order Id is required")
    private int oid;

    @NotNull(message = "User is required")
    private int userid;

    @Size(min = 1, message = "At least one table must be selected")
    private List<Integer> tableIds;

    @Size(min = 1, message = "At least one food item must be selected")
    private List<Integer> foodIds;

    @Positive(message = "Total price must be a positive value")
    private double totalPrice;

}
