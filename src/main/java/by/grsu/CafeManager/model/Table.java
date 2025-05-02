package by.grsu.CafeManager.model;

import by.grsu.CafeManager.model.enums.TableStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Table {
    private Long id;
    private String position;
    private TableStatus status;
}
