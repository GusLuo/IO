package cn.com.nio.one;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * NIO 客户端
 */
public class NioClient {
    public static void main(String[] args) throws IOException {
        System.out.println("客户端启动");
        //1、获取通道 并连接服务端
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",9999));
        //2、设置成非阻塞模式
        socketChannel.configureBlocking(false);
        //3、定义缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("请输入内容：");
            String msg = scanner.nextLine();
            //把输入的信息放入到缓冲区
            byteBuffer.put(msg.getBytes());
            //缓冲区转换成读模式
            byteBuffer.flip();
            //把缓冲区数据写入到通道中
            socketChannel.write(byteBuffer);
            //写入完成后，清空缓冲区
            byteBuffer.clear();
        }
        //4、
    }
}