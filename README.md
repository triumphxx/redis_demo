# redis_demo
* Redis的通讯协议是 Redis Serialization Protocol，简称 RESP
 * Redis 协议将传输的数据结构分为 5 种最小单元，单元结束时，加上回车换行符 \r\n。
 * 1、单行字符串以 + 开始，例如 +triumphxx\r\n
 * 2、多行字符串以 开始，后面加上字符串长度，例如11\r\triumphxx\r\n
 * 3、整数值以: 开始，例如 :1024\r\n
 * 4、错误消息以 - 开始
 * 5、数组以 * 开始，后面加上数组长度。
 ### 需要注意的是，如果是客户端连接服务端，只能使用第 5 种
