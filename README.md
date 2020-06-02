# dubbo-dev-plugin
解决`dubbo`微服务多人开发，在开发环境中调用问题

1、解决本机服务调用负载至本机

2、解决调用开发环境不会负载至其他开发者机器

3、解决间断性负载仍然可以负载至本机

## 使用

在`classpath`下新增配置文件

`lb-dev.properties` 只针对consumer

配置内容

```bash
##本机ip
dubbo.lb.host=192.168.1.195
##开发环境所有服务ip以逗号隔开
dubbo.lb.default=192.168.1.100,192.168.1.111,192.168.1.112
```

项目中引入包，当然事先你先`clone`此项目 `maven clean install`至你本地仓库

```
<dependency>
            <groupId>dubbo.plugin</groupId>
            <artifactId>dubbo-dev-plugin</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```

provider配置新增

```
<!--过滤器配置-->
    <dubbo:provider filter="providerDevFilter"/>
```

consumer配置新增

```
 <!--过滤器配置-->
    <dubbo:consumer filter="consumerDevFilter" loadbalance="devLoadbalance"/>
```



## 原理

项目使用`spi`扩展增加两个`Filter`和一个`LoadBalance`当服务存在负载时优先调用`dubbo.lb.host`配置的ip,假如不存在则优先调用`dubbo.lb.default`配置的ip,假如都不存在则进行random负载。并将这个两个配置随调用链进行传播。当遇到负载时重复前面策略。

