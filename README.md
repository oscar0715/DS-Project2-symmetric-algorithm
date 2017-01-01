# DS-Project2-symmetric-algorithm
对称加密实现 client 端和 server 端的 socket 加密通信

业务

1. client 向 server 发送自己的位置和消息。
2. client 在 server 有自己用户名和密码。 Sever 根据 client 用户名密码是否正确判断 client 是否合法。
3. 加密的部分，使用 TEA 对称算法。 Client 和 Server 共享一个 symmetric key 。在判断用户名密码之前， Client 首先要提交对称加密的密钥，如果密钥不通过那也就不用判断用户名密码了
4. 如果对称密钥和用户名密码的验证都通过以后，Server 端将 Client 端的位置写入 kml 文件。

代码实现：

1. Server 端
    - Server端代码在 TCPSpyCommanderUsingTEAandPasswords.java
    - Connection.java 类拓展了 Thread 类，是 server 端的处理 client 的密钥和消息的业务逻辑。
    - 新建服务端进程 serverSocket 监听 7777 端口，每次监听到一个客户端进程 socket ，就新开一个 Connection 类来处理一个 client 请求
2. Client 端
    - Client 端代码在TCPSpyUsingTEAandPasswords.java 
    - 每次新建一个客户端 socket 进程和 server 端交互。
3. 辅助类
    - TEA 类实现加密算法
    - CommonUtil 是一个辅助类，将一些业务逻辑抽象出来
    - PasswordHash 是用来生成 client 的 hash 以后的密码的
    - KMLWriter 是用来将 client 的地址写入 kml file.
    - kml file 的路径是 "./src/SecretAgents.kml"
