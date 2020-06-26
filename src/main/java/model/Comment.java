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
public class Comment {
    private int id;
    private int taskId;
    private int userId;
    private String comment;
    private Date date;
    private User user;
    private Task task;
}
