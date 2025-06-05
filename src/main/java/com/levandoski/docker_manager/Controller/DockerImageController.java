package com.levandoski.docker_manager.Controller;

import com.github.dockerjava.api.model.Image;
import com.levandoski.docker_manager.Service.DockerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class DockerImageController {

    private final DockerService dockerService;

    public DockerImageController(DockerService dockerService) {
        this.dockerService = dockerService;
    }

    @GetMapping("")
    public List<Image> listImages() {
        return dockerService.listImages();
    }

}
