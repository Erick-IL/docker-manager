package com.levandoski.docker_manager.Controller;

import com.github.dockerjava.api.exception.InternalServerErrorException;
import com.github.dockerjava.api.exception.NotModifiedException;
import com.levandoski.docker_manager.DTO.ContainerRequest;
import com.levandoski.docker_manager.Service.DockerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("")
public class DockerContainerController {

    private final DockerService dockerService;

    public DockerContainerController(DockerService dockerService) {
        this.dockerService = dockerService;
    }

    @GetMapping("/containers")
    public String showContainersPage(Model model,
                                     @RequestParam(required = false, defaultValue = "true") boolean showAll) {

        List<ContainerRequest> containers = dockerService.listContainers(showAll);
        model.addAttribute("containers", containers);
        return "containerPage";
    }

    @PostMapping("api/containers/stop")
    public String stopContainer(RedirectAttributes redirectAttributes,
                                @RequestParam("id") String containerId) {
        try {
            dockerService.stopContainer(containerId);
        } catch (NotModifiedException ignored) {
            redirectAttributes.addFlashAttribute("error", "Container já está parado");
        }
        return "redirect:/containers";
    }

    @PostMapping("api/containers/start")
    public String startContainer(RedirectAttributes redirectAttributes,
                                 @RequestParam("id") String containerId) {
        try {
            dockerService.startContainer(containerId);
        } catch (InternalServerErrorException error) {
            String errorMessage = error.getMessage();
            if (errorMessage.contains("port is already allocated")) {
                redirectAttributes.addFlashAttribute("error", "A Porta já está em uso.");
            } else {
                redirectAttributes.addFlashAttribute("error", errorMessage);
            }
        }
        return "redirect:/containers";
    }

    @PostMapping("api/containers/delete")
    public String deleteContainer(@RequestParam("id") String containerId) {
        dockerService.removeContainer(containerId);
        return "redirect:/containers";
    }

    @PostMapping("api/containers/create")
    public String createContainer(@RequestParam("imageName") String imageName, String containerName,
                                  Integer port, Integer exposedPort, String volumePath) {
        dockerService.createContainer(imageName, containerName, port, exposedPort);
        return "redirect:/containers";
    }


}

