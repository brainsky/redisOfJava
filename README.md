# redisOfJava
使用java模拟redis功能
# 实现功能
## 模拟基础数据结构、SDS、链表、字典、跳跃表、整数集合、压缩列表
### 2020年11月27日前完成了jemalloc的算法的部分复制（subpage的allocate和free)，参考的是netty的jemalloc的算法。
### 由Arena --> chunkList --> chunk --> Subpage 分配内存。subpage的分配是使用long数组的bitmap算法计算。
### 接着复制chunk的allocate和Arena的allocate。

