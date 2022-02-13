package cn.com.bio.one;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try {
            System.out.println("启动BIO客户端");
            //连接socket服务端
            Socket socket = new Socket("127.0.0.1",9999);
            //开启一个新的线程，用于接收服务端消息
            new ClientThread(socket).start();
            //获取输出流
            OutputStream outputStream = socket.getOutputStream();
            //转换成字符流
            PrintStream printStream = new PrintStream(outputStream);
            Scanner scanner = new Scanner(System.in);
            while (true){
                System.out.print("请输入内容：");
                String msg = scanner.nextLine();
                printStream.println(msg);
                printStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}