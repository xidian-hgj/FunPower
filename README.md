部署过程：
1.	拷贝服务器端程序到服务器存储器中,即powercat_server、powercat_analyse和PowerCatServer程序；
2.	设置所有文件权限为可执行；
3.	拷贝测试端程序文件夹到测试手机存储器中,PowerCatSence.apk；
	1)		文字聊天、文件上传、网络传输-HTTP、网络传输-FTP、网络传输-TCP、网页浏览、在线

视频观看、网络传输-UDP等测试场景在运行之前要检查IP是否正确
	2)	PowerCatServer服务器：SocketClient使用之前要注意IP地址()
	3)	网络传输-HTTP、在线视频观看、网页浏览这三个测试场景用例使用的分别访问的是Tomcat服务器

下的资源http://219.245.72.166:8080/myapp路径下的b.txt、a.mp4、baidu.htm和hao123.htm （若没有该资源可

以先建立该资源）
	4)	文件上传测试用例：上传到PowerCatServer的file文件夹下(上传的文件时c.txt，确保在手机的

sd卡中存在改文件)
	5)	网络传输-FTP：下载的是D:/test文件夹下的README.txt（确保其是存在的）
	6)	网络传输-TCP:  下载的是D:/test文件夹下的1.txt，注意修改服务器端程序里面需要下载的文件

的位置。
	7)	测试用例和服务器/apache-ftpserver-1.0.6，其只是用来提供jar包的，将common/lib中的jar

包导入进去，即可，采用复制导入的方式
4.	修改所有文件权限为可执行；
5.	部署Tomcat服务器，拷贝AutomTestWeb.war到Tomcat服务器
6.	修改powercat.sh脚本的ipAddr变量为服务器ip地址
执行过程：
1.	启动服务器端powercat_server程序；
2.	启动服务器端NetSpeedServer程序；
3.	启动服务器端PowerCatServer程序；
4.	启动自动化测试端网页AutomTestWeb.war程序；
Java Web版网页自动化测试端的也放apache-tomcat-8.0.9-windows-x64的webapp下面,名称：AutomTestWeb.war。

使用方法：打开apache-tomcat-8.0.9-windows-x64下的bin文件夹startup.bat，开启Tomcat服务器。网页端输入： 

http://127.0.0.1:8080/AutomTestWeb/TestWeb.jsp，即可访问自动化测试网页。网页端输入

http://127.0.0.1:8080/myapp/+文件名.jsp即可查看其保存的文件内容。

5.	启动客户端powercat.sh脚本，脚本参数为：程序名称 Activity名称。
	a)	启动的为本地C程序，则程序名称为程序文件名，Activity名称为空
	例：./powercat.sh  sence_cpudelay
	b)	启动为Android程序，则程序名称为包名，Activity名称为接收Launcher消息的Activity名称
	例：./powercat.sh  com.xd.powercatsence  MainActivity
