
架构基盘
===================================
* 基于Lemon OA开发的有爱团JAVA架构基盘，用于快速开发基于工作流的业务系统，当前版本v0.1


IDE
-----------------------------------
* 建议使用intellij idea开发,[官网下载入口](http://http://www.jetbrains.com/idea/)



取代码
-----------------------------------
* sudo vim /etc/ssh/ssh_config
 * insert
```
Host www.tongpinlife.com
Port 8008
```
* 通过ssh取代码
 * 输入`git clone git@www.tongpinlife.com:ljl/cherry.git
* 通过idea取代码
 * configure -> Preferences -> Version Control -> Git ,将SSH executable改成“Native”，点击OK
 * 点击VCS->check out from Version Control->git
 * 在Git Repository URL输入 `git@www.tongpinlife.com:ljl/cherry.git`


运行
-----------------------------------
* Run -> Edit Configuarations
* + Maven 点击OK
work dictionary:D:/IdeaProjects2016/cherry 替换为你的程序目录
commond line：jetty:run
点击tab：Runner
VM options：-DMAVEN_OPTS=-Xms1024M -Xmx1024M -XX:PermSize=256M -XX:MaxPermSize=256M
* 访问地址 [http://localhost:8080/cherry]



前段jsp页面放到/webapp/content文件夹下
如果用beetl模板引擎放到/webapp/template下,以.html为后缀 如：sublist.html

基于springmvc的测试例子在/src/test/ 目录下。MainSpringTestDemo：是基于main方法的测试形式，可支持spring除了springmvc。 TestService1是基于JUIT的测试形式，可模拟web环境支持springmvc。
但这样的测试赌需要写一些测试代码，我觉得都是麻烦的，现在想考虑一下，直接基于springlaoded的热部署。

动态部署：
配置：
点击工具栏配置的下拉图标->Edit Configurations... ->Runner
VM options输入：-javaagent:D:\IdeaProjects2016\cherry\springloaded-1.2.5.RELEASE.jar -noverify -Xms1024M -Xmx1024M -XX:PermSize=256M -XX:MaxPermSize=256M
其中：D:\IdeaProjects2016\cherry\springloaded-1.2.5.RELEASE.jar 替换为你电脑上的路径
当java文件修改后Ctrl+Shift+F9编译单个文件，Crtl+F9编译所有修改。当然可以点击菜单栏Build下选择功能按钮点击