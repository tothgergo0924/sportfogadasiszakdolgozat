package org.example.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationCreateDTO {
    @Size(min = 10,message = "Content must be at least 10 letters long")
    private String content;

    @NotEmpty(message = "Usernames mustn't be empty")
    private List<String> usernames;
}
