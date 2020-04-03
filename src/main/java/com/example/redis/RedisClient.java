package com.example.redis;

import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.net.Socket;

/**
 * Redis的通讯协议是 Redis Serialization Protocol，简称 RESP
 * Redis 协议将传输的数据结构分为 5 种最小单元，单元结束时，加上回车换行符 \r\n。
 * 1、单行字符串以 + 开始，例如 +triumphxx\r\n
 * 2、多行字符串以 开始，后面加上字符串长度，例如11\r\triumphxx\r\n
 * 3、整数值以: 开始，例如 :1024\r\n
 * 4、错误消息以 - 开始
 * 5、数组以 * 开始，后面加上数组长度。
 *
 * ** 需要注意的是，如果是客户端连接服务端，只能使用第 5 种
 * @author triumphxx
 */
public class RedisClient {

    private Socket socket;

    public RedisClient(){
        try {
            //单纯java socket 链接redis服务实现
            socket = new Socket("127.0.0.1",6379);
            //下面是java客户端Jedis的实现方式
            Jedis jedis = new Jedis("127.0.0.1",6379);
            jedis.set("wdadad","werwerwer");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Redis链接异常");
        }
    }

    /**
     * 执行 Redis 中的 set 命令 [set,key,value]
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("*3")
                .append("\r\n")
                .append("$")
                .append("set".length())
                .append("\r\n")
                .append("set")
                .append("\r\n")
                .append("$")
                .append(key.getBytes().length)
                .append("\r\n")
                .append(key)
                .append("\r\n")
                .append("$")
                .append(value.getBytes().length)
                .append("\r\n")
                .append(value)
                .append("\r\n");
        socket.getOutputStream().write(sb.toString().getBytes());
        byte[] buf = new byte[1024];
        socket.getInputStream().read(buf);
        return new String(buf);
    }
    /**
     * 执行 Redis 中的 get 命令 [get,key]
     * @param key
     * @return
     */
    public String get(String key) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("*2")
                .append("\r\n")
                .append("$")
                .append("get".length())
                .append("\r\n")
                .append("get")
                .append("\r\n")
                .append("$")
                .append(key.getBytes().length)
                .append("\r\n")
                .append(key)
                .append("\r\n");
        socket.getOutputStream().write(sb.toString().getBytes());
        byte[] buf = new byte[1024];
        socket.getInputStream().read(buf);
        return new String(buf);
    }

    public static void main(String[] args) {
        String set = null;
        try {
            set = new RedisClient().set("wang", "wangyupeng");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(set);
//
        String k1 = null;
        try {
            k1 = new RedisClient().get("wang");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(k1);
    }
}
