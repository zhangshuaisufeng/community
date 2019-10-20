package com.springboot.controller;

import com.springboot.dto.AccessToken;
import com.springboot.dto.GitHubUser;
import com.springboot.dto.User;
import com.springboot.provider.GitHubProvider;
import com.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;
    @Resource
    private UserService userService;

    @Value("${github.client.id}")  //读取配置文件信息
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String uri;

    @GetMapping("/callback")
    public String callBack(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpSession session){
        AccessToken accessToken=new AccessToken();
        accessToken.setClient_id(clientId);
        accessToken.setClient_secret(clientSecret);
        accessToken.setCode(code);
        accessToken.setRedirect_uri(uri);
        accessToken.setState(state);
        String token=gitHubProvider.getAccessToken(accessToken);
        System.out.println(token);
        GitHubUser gitHubUser=gitHubProvider.getGitHubUser(token);
        if(gitHubUser !=null){
            User user=new User();
            user.setAccount_id(String.valueOf(gitHubUser.getId()));
            user.setName(gitHubUser.getName());
            //获取不重复的字符作为token
            user.setToken(UUID.randomUUID().toString());
            userService.insert(user);
            session.setAttribute("user",gitHubUser);
            return "redirect:/index";
        }else{
            return "redirect:/index";
        }
    }
}
