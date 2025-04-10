package com.example.myreviewer.domain;

public enum Level {
   BEGINNER("Beginner"), INTERMEDIATE("Intermediate"), ADVANCED("Advanced");

   private String name;

    Level(String name) {
        this.name = name;
    }
}
