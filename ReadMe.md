
#### ReadMe
分享一个自定义插件demo
就是自定义插件，并在指定类指定方法插入 代码,javassist 
1 hilt就是基于注解，butterknife之类都是 在源码到java的中间进行处理
2 aspectj 是在java 到class文件之间进行了代码插入（指静态插入，还有一种就是反射属于动态）
3 javassist就是在 class 到dex字节码之前 进行处理，插入代码

方式：源码--apt(aop)-->java--（aspectj）-->class--(javassist/asm)--->dex--->apk

可以了解到：插件自定义


# 建议查看文档
https://www.teambition.com/project/5dca25fa4b66d8001893b3ce/tasks/scrum/5dca25fa6e74fb0018d09d83

美团Robust
https://github.com/Meituan-Dianping/Robust


# 打包插件的三种方式
https://www.jianshu.com/p/f902b51e242b
