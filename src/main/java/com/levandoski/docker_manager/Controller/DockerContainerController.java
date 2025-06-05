package com.levandoski.docker_manager.Controller;

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
        return "index";
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
    public String startContainer(@RequestParam("id") String containerId) {
        dockerService.startContainer(containerId);
        return "redirect:/containers";
    }

    @PostMapping("api/containers/delete")
    public String deleteContainer(@RequestParam("id") String containerId) {
        dockerService.removeContainer(containerId);
        return "redirect:/containers";
    }

    @PostMapping("api/containers/create")
    public String createContainer(@RequestParam("imageName") String imageName) {
        dockerService.createContainer(imageName);
        return "redirect:/containers";
    }


}

