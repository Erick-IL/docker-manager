package com.levandoski.docker_manager.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ContainerPort;
import com.github.dockerjava.api.model.Image;
import com.levandoski.docker_manager.DTO.ContainerRequest;
import org.springframework.stereotype.Service;

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

    public void createContainer(String imageName) {
        dockerClient.createContainerCmd(imageName).exec();
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

    // Funções para images

    public List<Image> listImages() {
        return dockerClient.listImagesCmd().exec();
    }

    public String getImageName(String containerId) {
        return dockerClient.inspectContainerCmd(containerId).exec().getConfig().getImage();
    }
}
