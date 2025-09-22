package com.social.network.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    private String nickname;
    private String email;
    private String password;
    private String role;
    private String image;
    private String created_at;
}
