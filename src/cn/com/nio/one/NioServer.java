package cn.com.nio.one;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * Nio 客户端
 */
public class NioServer {

    public static void main(String[] args) {
        System.out.println("服务端启动成功");
        try {
            //1、获取通道 用于接收客户端的链接请求
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //2、切换为非阻塞式模式
            serverSocketChannel.configureBlocking(false);
            //3、绑定连接的端口
            serverSocketChannel.bind(new InetSocketAddress(9999));
            //4、获取选择器
            Selector selector = Selector.open();
            //5、把通道注册到选择器上 并制定监听接收事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            //6、选择器轮询相关事件
            while (selector.select() > 0){
                //获取选择器中所有的事件
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                //遍历所有事件
                while(iterator.hasNext()){
                    SelectionKey sk = iterator.next();
                    //判断当前事件是否为接入事件
                    if(sk.isAcceptable()){
                        System.out.println("当前事件为接入事件");
                        //获取当前接入的客户端通道
                        SocketChannel sc = serverSocketChannel.accept();
                        //设置非阻塞
                        sc.configureBlocking(false);
                        //讲当前客户端事件注册到选择器中，并监听读事件
                        sc.register(selector,SelectionKey.OP_READ);
                    }else if(sk.isReadable()){//判断当前事件是否为读事件
                        System.out.println("当前事件为读事件");
                        //获取当前选择器上的读就绪事件
                        SocketChannel sc = (SocketChannel) sk.channel();
                        //定义缓冲区 并设置缓冲区大小 1kb
                        ByteBuffer bb = ByteBuffer.allocate(1024);
                        int len = 0;
                        while((len=sc.read(bb))>0){
                            //把缓冲区的转换成读模式
                            bb.flip();
                            System.out.println(new String(bb.array(),0,len));
                            bb.clear();//清除缓冲区的数据
                        }
                    }
                    //处理完事件后，要移除当前事件,以免重复执行
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //2、
    }
}