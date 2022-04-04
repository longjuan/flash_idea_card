# 项目介绍

本项目是基于spring cloud alibaba的看板工具平台项目

前端：https://github.com/longjuan/flash_idea_card_ui

![image-20220404193613984](https://one.zway.top/api/v3/file/source/129/image-20220404193613984_vzL.png?sign=izbrRa7fgKt40euyvXcrqb5raoOtat06W5n4KFuWfU0%3D%3A0)



![image-20220404193639789](https://one.zway.top/api/v3/file/source/130/image-20220404193639789_jMp.png?sign=2WiajeDF86-MadHkXjpew4SkQ3EXX4pGrAdVNgollAI%3D%3A0)

![image-20220404194022449](https://one.zway.top/api/v3/file/source/131/image-20220404194022449_TUu.png?sign=X3smQ6mr2EySRw61RXngnLVpnuhTxBcQrIfSIHFPCOc%3D%3A0)

# 技术栈

* spring cloud alibaba
* redis
* rabbitmq
* elasticsearch
* docker

# 目录结构

```bash
.
├── fic-auth # 登录授权模块
├── fic-common
│   ├── fic-common-base # 基础依赖
│   ├── fic-common-redis # redis基础依赖
│   ├── fic-common-web # web基础依赖
├── fic-gateway # 网关
├── fic-kanban # 看板模块
├── fic-search # 搜索模块
└── fic-user # 用户模块
```

# 配置文件示例

在每个`resources`文件夹下的`fic-xxx-dev.yaml.nacos.example`

