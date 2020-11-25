# 安装单机版redis
    此处省略redis安装步骤，默认为已经安装好redis
# redis集群配置
 - 集群采用一主一从模式，所以建议集群中最少应该有6个节点，此处集群实例端口分别采用7000、7001、7002、7003、7004、7005，前提保证这些端口linux没有限制，或者说这些端口可以根据实际情况自定义，不冲突即可。
    ```sh
    cd xxxx/redis
    mkdir redis-cluster
    cd redis-cluster
    mkdir 7000 7001 7002 7003 7004 7005
    cp ../redis/redis.conf 7000/
    vim 7000/redis.conf
    修改端口号，开启集群模式相关配置，如下配置是最简单的配置，其他配置可按实际需求进行修改：
    port 7000
    cluster-enabled yes
    cluster-config-file nodes.conf
    cluster-node-timeout 5000
    appendonly yes
    然后重复执行拷贝配置文件，以及修改配置文件的操作
    cp redis-cli redis-cluster ../redis-cluster
    ./redis-server 7000/redis.conf
    依次执行剩余五个端口的redis实例
    ```
 - 初始化集群
     ```sh
     redis-cli --cluster create 127.0.0.1:7000 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005 --cluster-replicas 1
     ````
     如果出现 [OK] All 16384 slots covered ，即代表redis集群初始化成功。
 - 测试集群
   ```sh
   ./redis-cli -c -p 7000
   redis 127.0.0.1:7000> set foo bar
   -> Redirected to slot [12182] located at 127.0.0.1:7002 OK
   redis 127.0.0.1:7002> set hello world
   -> Redirected to slot [866] located at 127.0.0.1:7000 OK
   redis 127.0.0.1:7000> get foo
   -> Redirected to slot [12182] located at 127.0.0.1:7002 "bar"
   redis 127.0.0.1:7000> get hello
   -> Redirected to slot [866] located at 127.0.0.1:7000 "world"
   ```
# 其他问题
 - 为什么需要至少三个主节点
 - 为什么hash slot是16384个
