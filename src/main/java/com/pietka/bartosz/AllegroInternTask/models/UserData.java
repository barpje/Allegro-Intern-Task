package com.pietka.bartosz.AllegroInternTask.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserData {
    private String login;
    private int public_repos;
}
