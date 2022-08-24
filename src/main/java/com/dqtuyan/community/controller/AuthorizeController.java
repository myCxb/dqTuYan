package com.dqtuyan.community.controller;

import com.dqtuyan.community.dto.AccessTokenDto;
import com.dqtuyan.community.dto.GithubUser;
import com.dqtuyan.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    GithubProvider githubProvider;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code , @RequestParam("state") String state){
        AccessTokenDto ace = new AccessTokenDto();
        ace.setClient_id("f7c99348b20d4cc55a02");
        ace.setClient_secret("23b5cc62fbea00b2f249739d3dfa4f418ecf0e9e");
        ace.setCode(code);
        ace.setRedirect_uri("http://localhost:8080/callback");
        ace.setState(state);
        String token = githubProvider.getAccessToken(ace);
//        System.out.println(token);
        GithubUser githubUser = githubProvider.getUser(token);
        System.out.println(githubUser.toString());
        return "index";
    }
}
