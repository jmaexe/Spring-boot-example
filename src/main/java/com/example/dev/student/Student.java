package com.example.dev.student;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record Student(@NotEmpty String name, @Positive int age, int id) {
}
