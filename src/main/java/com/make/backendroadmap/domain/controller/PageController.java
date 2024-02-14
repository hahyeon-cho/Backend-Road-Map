package com.make.backendroadmap.domain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/internet")
    public String internet() {
        return "/DataSet/01_Internet/Internet";
    }

    @GetMapping("/fe")
    public String basicFE() {
        return "/DataSet/02_Basic-FE/Basic-FE";
    }


    @GetMapping("/os")
    public String os() {
        return "/DataSet/03_OS-work/OS";
    }


    @GetMapping("/language")
    public String language() {
        return "/DataSet/04_Language/Language";
    }


    @GetMapping("/algorithm")
    public String algorithm() {
        return "/DataSet/05_Algorithm/Algorithm";
    }


    @GetMapping("/git")
    public String git() {
        return "/DataSet/06_Git/Git";
    }


    @GetMapping("/git/repo")
    public String gitHubGitLab() {
        return "/DataSet/07_GitHub-GitLab/GitHub-GitLab";
    }


    @GetMapping("/rdb")
    public String RDB() {
        return "/DataSet/08_RDB/RDB";
    }


    @GetMapping("/nosql")
    public String noSQL() {
        return "/DataSet/09_NoSQL/NoSQL-DB";
    }


    @GetMapping("/db/knowledge")
    public String DbKnowledge() {
        return "/DataSet/10_DB-Knowledge/DB-Knowledge";
    }


    @GetMapping("/apis")
    public String APIs() {
        return "/DataSet/11_APIs/APIs";
    }


    @GetMapping("/framework")
    public String framework() {
        return "/DataSet/12_Framework/Framework";
    }


    @GetMapping("/caching")
    public String caching() {
        return "/DataSet/13_Caching/caching";
    }


    @GetMapping("/security")
    public String WebSecurity() {
        return "/DataSet/14_Web-Security/Web-Security";
    }


    @GetMapping("/test")
    public String test() {
        return "/DataSet/15_Test/Test";
    }


    @GetMapping("/ci/cd")
    public String CICD() {
        return "/DataSet/16_CI-CD/CI-CD";
    }


    @GetMapping("/design/pattern")
    public String DesignPattern() {
        return "/DataSet/17_Design-Pattern/Design-Pattern";
    }


    @GetMapping("/search/engines")
    public String SearchEngines() {
        return "/DataSet/18_SearchEngines/SearchEngines";
    }


    @GetMapping("/container")
    public String container() {
        return "/DataSet/19_Container/Container";
    }


    @GetMapping("/server")
    public String WebServer() {
        return "/DataSet/20_Web-Server/Web-Server";
    }
}
