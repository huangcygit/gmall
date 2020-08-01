package com.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LogController {

    @Autowired
    LogService logService;

    @RequestMapping("/log")
    public String log(String method,String version){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.setAttribute(LogUtils.DEBUG_LOG, LogUtils.initLog(method + ' ' + version)); //初始化日志

        LogUtils.startLogWeb("111111111111");
        System.out.println("sssssssssss");
        logService.log();

        LogUtils.endLogWeb();
        LogStack root = LogUtils.getRootLog();
        if(root != null) {
            LogUtils.endWholeLog(root);
            System.out.println(root.getMillis());
        }
        return "success";
    }
}
