package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    private long id;
    private String name;
    private String desc;
    private Date deadline;
    private Status status;
    private int user_id;
}
