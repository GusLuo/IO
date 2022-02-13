package cn.com.bio.one;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class BioServer {

    public static List<Socket> socketList = new ArrayList<>();

    public static void main(String[] args) {
        try {
            System.out.println("启动BIO服务端");
            //1、，注册端口，新建socket的服务端
            ServerSocket serverSocket = new ServerSocket(9999);
            //2、循环获取客户端链接
            //把线程放入到线程池中
            BioThreadPool bioThreadPool = new BioThreadPool(5,8,5);
            while (true){
                //获取到客户端的连接
                Socket accept = serverSocket.accept();
                //把客户端放入到socketList中，用于转发消息给到所有在线客户端
                socketList.add(accept);
                //把当前连接要执行的操作放入线程中
                BioRunable bioRunable = new BioRunable(accept);
                bioThreadPool.execute(bioRunable);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}