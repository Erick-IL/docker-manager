package com.levandoski.docker_manager.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
public class ContainerRequest { // classe responsável por converter o container numa classe java
    private String containerId;
    private String image;
    private String name;
    private String status;
    private String createdAt;
    private String Port;
    private String command;
    private String state;


    public ContainerRequest(String containerId, String image, String name, String status, Long createdAt, String command, String state, int privatePort, int publicPort) {
        this.containerId = containerId;
        this.image = image;
        this.name = name;
        this.status = status.replaceAll("\\(.*?\\)", "");
        this.createdAt = formatCreatedDate(createdAt);
        this.Port = String.format("%d:%d", publicPort, privatePort).equals("0:0") ? "Desligado" : String.format("%d:%d", publicPort, privatePort);
        this.command = command;
        this.state = state;
    }


    public static String formatCreatedDate(Long created) {
        Instant instant = Instant.ofEpochSecond(created);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return localDateTime.format(formatter);
    }

}
