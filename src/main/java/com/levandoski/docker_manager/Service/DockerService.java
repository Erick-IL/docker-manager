package com.levandoski.docker_manager.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import com.levandoski.docker_manager.DTO.ContainerRequest;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class DockerService {

    private final DockerClient dockerClient;

    public DockerService(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }


    // Funções para containers

    public List<ContainerRequest> listContainers(boolean all) {
        List<Container> containers = dockerClient.listContainersCmd().withShowAll(all).exec();
        List<ContainerRequest> containerRequests = new ArrayList<>();
        for (Container container : containers) {
            String name = container.getNames() != null ? container.getNames()[0].replaceFirst("/", "") : "Desconhecido";

            ContainerPort[] ports = container.getPorts();

            int privatePort = 0;
            int publicPort = 0;

            if (ports != null && ports.length > 0) {
                privatePort = ports[0].getPrivatePort() != null ? ports[0].getPrivatePort() : 0;
                publicPort = ports[0].getPublicPort() != null ? ports[0].getPublicPort() : 0;
            }

            ContainerRequest dto = new ContainerRequest(
                    container.getId(),
                    container.getImage(),
                    name,
                    container.getStatus(),
                    container.getCreated(),
                    container.getCommand(),
                    container.getState(),
                    privatePort,
                    publicPort
            );
            containerRequests.add(dto);
        }

        return containerRequests;
    }

    public void createContainer(String imageName, String containerName, Integer port, Integer exposedPort) {
        CreateContainerCmd cmd = dockerClient.createContainerCmd(imageName)
                .withName(containerName);

        HostConfig newHostConfig = HostConfig.newHostConfig()
                .withSecurityOpts(List.of("apparmor:docker-default"));

        if (exposedPort != null && port != null && exposedPort >= 1 && port >= 1
                && exposedPort <= 65535 && port <= 65535) {
            newHostConfig.withPortBindings(new PortBinding(
                    Ports.Binding.bindPort(port), ExposedPort.tcp(exposedPort)));
            cmd.withExposedPorts(ExposedPort.tcp(exposedPort));
        }

        cmd.withHostConfig(newHostConfig);
        cmd.exec();
    }


    public void startContainer(String containerId) {
        dockerClient.startContainerCmd(containerId).exec();
    }

    public void stopContainer(String container) {
        dockerClient.stopContainerCmd(container).exec();
    }

    public void removeContainer(String containerId) {
        dockerClient.removeContainerCmd(containerId).exec();
    }

//    public String getContainerLog(String containerId) {
//        dockerClient.logContainerCmd(containerId).withStdErr(true).withStdOut(true).withFollowStream(false).withTailAll();
//    }

}
