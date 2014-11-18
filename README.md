FunPower
========
一、模块说明
函数功耗评估软件分为测试端和服务器端。测试端实现功耗信息采集、上传，功耗场景自动化测试。服务器端实现功耗信息接收、分析，提供功耗信息调用接口。
测试端包括：
其中功耗场景测试程序可通过powercat.sh脚本的参数指定，测试程序类型可以为本地C程序和Android应用程序。
文件名	类型	用途
powercat.sh	Shell脚本	启动功耗场景测试程序
启动采集程序powercat
powercat	C程序	采集功耗变量信息
上传功耗变量信息
PowerCatSence.apk	Android程序	功耗场景测试程序
实现15种功耗场景测试程序
和10中上网功耗测试程序（目前包含的场景程序有CPU全速运行、内存读写、TCP下载）
AutomTestWeb.war	Java web 程序	自动化测试网页端
实现手机端测试场景的自动化控制
设置测试场景、测试时间、服务器端IP设置
sence_cpudelay	C程序	功耗场景测试程序
全速消耗CPU
sence_cpuControl	C程序	功耗场景测试程序
逐次改变CPU利用率
使用整型计算消耗CPU
sence_cpuControl_float	C程序	功耗场景测试程序
逐次改变CPU利用率
使用浮点计算消耗CPU
sence_MemControl	C程序	功耗场景测试程序
逐次改变内存利用率
服务器端包括：
文件名	类型	用途
powercat_server	C程序	接收powercat上传的功耗序列信息，并保存为power.dat文件
powercat_analyse	C程序	分析功耗信息序列文件power.dat，提供功耗信息获取接口
演示接口的使用方式
NetSpeedServer	Java程序	功耗场景测试程序的上网功耗测试的配套服务器端程序

其结构图如下：



 




二、部署过程
部署过程：
1.	拷贝服务器端程序到服务器存储器中,即powercat_server、powercat_analyse和PowerCatServer程序；
2.	设置所有文件权限为可执行；
3.	拷贝测试端程序文件夹到测试手机存储器中,PowerCatSence.apk；
1)		文字聊天、文件上传、网络传输-HTTP、网络传输-FTP、网络传输-TCP、网页浏览、在线视频观看、网络传输-UDP等测试场景在运行之前要检查IP是否正确
2)	PowerCatServer服务器：SocketClient使用之前要注意IP地址()
3)	网络传输-HTTP、在线视频观看、网页浏览这三个测试场景用例使用的分别访问的是Tomcat服务器下的资源http://219.245.72.166:8080/myapp路径下的b.txt、a.mp4、baidu.htm和hao123.htm （若没有该资源可以先建立该资源）
4)	文件上传测试用例：上传到PowerCatServer的file文件夹下(上传的文件时c.txt，确保在手机的sd卡中存在改文件)
5)	网络传输-FTP：下载的是D:/test文件夹下的README.txt（确保其是存在的）
6)	网络传输-TCP:  下载的是D:/test文件夹下的1.txt，注意修改服务器端程序里面需要下载的文件的位置。
7)	测试用例和服务器/apache-ftpserver-1.0.6，其只是用来提供jar包的，将common/lib中的jar包导入进去，即可，采用复制导入的方式
4.	修改所有文件权限为可执行；
5.	部署Tomcat服务器，拷贝AutomTestWeb.war到Tomcat服务器
6.	修改powercat.sh脚本的ipAddr变量为服务器ip地址
执行过程：
1.	启动服务器端powercat_server程序；
2.	启动服务器端NetSpeedServer程序；
3.	启动服务器端PowerCatServer程序；
4.	启动自动化测试端网页AutomTestWeb.war程序；
Java Web版网页自动化测试端的也放apache-tomcat-8.0.9-windows-x64的webapp下面,名称：AutomTestWeb.war。使用方法：打开apache-tomcat-8.0.9-windows-x64下的bin文件夹startup.bat，开启Tomcat服务器。网页端输入： http://127.0.0.1:8080/AutomTestWeb/TestWeb.jsp，即可访问自动化测试网页。网页端输入http://127.0.0.1:8080/myapp/+文件名.jsp即可查看其保存的文件内容。

5.	启动客户端powercat.sh脚本，脚本参数为：程序名称 Activity名称。
a)	启动的为本地C程序，则程序名称为程序文件名，Activity名称为空
例：./powercat.sh  sence_cpudelay
b)	启动为Android程序，则程序名称为包名，Activity名称为接收Launcher消息的Activity名称
例：./powercat.sh  com.xd.powercatsence  MainActivity

三、环境搭建说明
1. 服务器端环境使用ubuntu12.04
2. 服务器端需安装java虚拟机
3. 服务器端8010需要不被占用
4. 服务器解析程序需要占用比较大内存，则服务器环境内存要求2G以上
5. 测试端使用Android4.0及其以上环境
6. 测试端开启网络连接
7. 测试端与服务器端处于同一局域网 

四、接口说明
intloadPowerDataInFile(char * path, PowerDataSet ** set);
参数：path代表路径，set表示PowerDataSet*的指针
返回值：返回是否正确读出数据，若正确则返回1
功能：使用path路径读出数据，并将set指向读出的数据
PowerData * getPowerData(PowerDataSet * set, long count);
参数：count为索引表的索引号，set为要获取数据的索引表
返回值：返回PowerData类型的值
功能：使用count值返回索引表里的该行数据
longgetPowerSetNum(PowerDataSet * set);
参数：set为索引表
返回值：存储数据的行数
功能：返回存储数据的行数
voidprintPowerData(PowerData * data);
参数：PowerData类型的一行数据
返回值：无
功能：打印data所指的一行数据
longfindPowerDataCountByTime(longlong time, PowerDataSet *set);
参数：time为给定的时间点，set为索引表
返回值：返回离这个时间点最近的前一个采样时间点
功能：根据所给定的时间点time，返回离这个时间点最近的前一个采样时间点
doublegetPower(int core_num,PowerDataSet * set, longlong timeStart, longlong timeEnd);
参数：set为索引表，timeStart为要计算功耗的开始时间，timeEndt为要计算功耗的结束时间
返回值：返回timeStart和timeEnd这段时间进程产生的功耗
功能：根据参数timeStart和timeEnd，返回这段时间进程产生的功耗
五、接口使用示例
//定义功耗数据集
PowerDataSet * set;

//从文件中读取数据到功耗数据集
char * filePath = "power.dat";
loadPowerDataInFile(filePath, &set);

//获取时间区间内功耗
s = 100 * 1000000000;
e = 101 * 1000000000;
power = getPower(num,set, s, e);
