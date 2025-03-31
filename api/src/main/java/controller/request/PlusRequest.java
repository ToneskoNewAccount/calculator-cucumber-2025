package controller.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlusRequest {
    public String operation;
    public double []  numbers;

}
