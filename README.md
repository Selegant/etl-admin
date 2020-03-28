
## ETL_ADMIN

ETL_ADMIN 是基于XXL_JOB分布式调度平台和KETTLE的ETL数据采集平台。
因为现有集成KETTLE的采集调度平台功能都不太完善，无法很好的将KETTLE资源库的任务管理起来，同时对于KETTLE日志查看和日志告警的功能也不能很好的
支持，无法确认现阶段有哪些任务报错，所以希望有一款能够支持同步KETTLE资源库，能够支持定时任务，支持实时查看KETTLE采集日志，支持分布式， 
支持详细监控的的采集工具。同时在未来也会增加DataX的可视化功能，统一整合KETTLE和DataX两大采集工具，这也是该项目的初衷与目标

## System Requirements

- Language: Java 8
- Environment: MacOS， Windows，Linux
- Database: Mysql5.7，Mysql8
- Kettle: 7.0以上版本


## Features
- 1、用户管理
- 2、支持KETTLE指定数据源
- 3、支持DataX可视化


## Quick Start

### 1.下载或拉取 ETL_ADMIN 代码

### 2.执doc/db下的SQL文件

### 3.将plugin下的plugins文件解压放到指定目录

### 4.修改web模块下的application.yml的数据库配置信息

### 5.修改web模块下的application.yml的Kettle配置信息

```
kettle:
  repository-name: lx  #KETTLE资源库名称
  repository-username: admin  #KETTLE资源库用户名
  repository-password: shulan.com  #KETTLE资源库密码
  access-type: Native #KETTLE资源数据库连接类型
  database-type: MYSQL #KETTLE资源数据库类型
  database-host: localhost #KETTLE资源数据库地址
  database-name: kettle_lx #KETTLE资源数据库名称
  database-port: 3306 #KETTLE资源数据库端口号
  database-user: root #KETTLE资源数据库用户名
  database-password: 12345678 #KETTLE资源数据库密码
  plugins-path: /Users/selegant/Downloads/plugins #KETTLE插件地址
  app-name: kettle-job #执行器名称

```

### 6.修改kettle-job模块下的application.yml的数据库配置信息(同web模块)

### 7.修改kettle-job模块下的application.yml的Kettle配置信息(同web模块)

### 8.执行器配置(使用开源项目xxl-job)
- 1、"调度中心OnLine:"右侧显示在线的"调度中心"列表, 任务执行结束后, 将会以failover的模式进行回调调度中心通知执行结果, 避免回调的单点风险;
- 2、"执行器列表" 中显示在线的执行器列表, 可通过"OnLine 机器"查看对应执行器的集群机器;
#### 执行器属性说明
```
1、AppName: （与datax-executor中application.yml的datax.job.executor.appname保持一致）
   每个执行器集群的唯一标示AppName, 执行器会周期性以AppName为对象进行自动注册。可通过该配置自动发现注册成功的执行器, 供任务调度时使用;
2、名称: 执行器的名称, 因为AppName限制字母数字等组成,可读性不强, 名称为了提高执行器的可读性;
3、排序: 执行器的排序, 系统中需要执行器的地方,如任务新增, 将会按照该排序读取可用的执行器列表;
4、注册方式：调度中心获取执行器地址的方式；
    自动注册：执行器自动进行执行器注册，调度中心通过底层注册表可以动态发现执行器机器地址；
    手动录入：人工手动录入执行器的地址信息，多地址逗号分隔，供调度中心使用；
5、机器地址："注册方式"为"手动录入"时有效，支持人工维护执行器的地址信息；
```

### 9. idea启动 WebApplication，KettleJobApplication

### 10. 启动成功后打开页面（默认管理员用户名：admin 密码：123456）
http://localhost:8100
![](https://github.com/Selegant/etl-admin/blob/master/doc/img/login.jpg)
![](https://github.com/Selegant/etl-admin/blob/master/doc/img/index.jpg)

### 11. 点击KETTLE菜单选择用同步作业或转换

![](https://github.com/Selegant/etl-admin/blob/master/doc/img/job_manage.jpg)

### 12. 选择任务管理菜单开始任务或转换

![](https://github.com/Selegant/etl-admin/blob/master/doc/img/task.jpg)

### 13. 选择日志管理菜单查看日志或者同步任务管理选择某一作业菜单查看

![](https://github.com/Selegant/etl-admin/blob/master/doc/img/log_manage.jpg)

![](https://github.com/Selegant/etl-admin/blob/master/doc/img/log.jpg)

### 14. 若任务错误则点击右上角铃铛查看日志告警 确认某一日志后则会消除告警日志

![](https://github.com/Selegant/etl-admin/blob/master/doc/img/alert.jpg)



## UI
[前端github地址](https://github.com/Selegant/etl-admin-ui.git)



## Copyright and License
This product is open source and free, and will continue to provide free community technical support. Individual or enterprise users are free to access and use.

- Licensed under the GNU General Public License (GPL) v3.
- Copyright (c) 2020-present, selegant.

产品开源免费，并且将持续提供免费的社区技术支持。个人或企业内部可自由的接入和使用。

## 欢迎打赏

<img src="https://github.com/Selegant/etl-admin/blob/master/doc/img/ali.jpg" width=256 height=256 />

<img src="https://github.com/Selegant/etl-admin/blob/master/doc/img/weixin.jpg" width=256 height=256 />
