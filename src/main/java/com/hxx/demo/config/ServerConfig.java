package com.hxx.demo.config;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Hxx
 */
@Component
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {
    private int serverPort;

    /**
     * @return java.lang.String
     * @Author Hxx
     * @Description //TODO 获取ip地址
     * @Date 9:04 2019/12/25
     * @Param []
     **/
    public String getUrl() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "http://" + address.getHostAddress() + ":" + this.serverPort;
    }

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 获取端口号
     * @Date 9:04 2019/12/25
     * @Param [event]
     **/
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
    }

}
