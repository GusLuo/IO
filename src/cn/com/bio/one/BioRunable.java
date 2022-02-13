package cn.com.bio.one;

import java.io.*;
import java.net.Socket;

public class BioRunable implements Runnable {

    private Socket socket;

    public BioRunable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //1、获取客户端的输入流
            InputStream inputStream = socket.getInputStream();
            //2、将输入流转换成缓冲字符流
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String msg;
            while ((msg=bufferedReader.readLine()) != null){
                System.out.println("接收到客户端消息："+msg);
                sendMsg(msg);
            }
        } catch (IOException e) {
            System.out.println("客户端断开链接");
            try {
                socket.shutdownOutput();
            } catch (IOException e1) {

            }
        }
    }

    /**
     * 转发消息到其他客户端中
     * @param msg
     */
    private void sendMsg(String msg) {
        BioServer.socketList.forEach(sockets->{
            try {
                OutputStream outputStream = sockets.getOutputStream();
                PrintStream printStream = new PrintStream(outputStream);
                printStream.println(msg);
                System.out.println("发送消息到所有客户端："+msg);
                printStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}