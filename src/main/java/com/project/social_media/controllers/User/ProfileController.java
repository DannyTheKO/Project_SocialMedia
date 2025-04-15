package com.project.social_media.controllers.User;

import com.project.social_media.application.IService.IPostService;
import com.project.social_media.application.IService.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/profile")
public class ProfileController {
    private final IUserService userService;
    private final IPostService postService;
}
