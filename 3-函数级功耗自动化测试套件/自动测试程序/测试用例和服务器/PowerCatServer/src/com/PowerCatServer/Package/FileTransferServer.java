package com.PowerCatServer.Package;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//（0）服务器端代码
public class FileTransferServer   
{  
     //线程池   
     private ExecutorService executorService;  
     //监听端口   
     private int port;  
     //退出   
     private boolean quit = false;  
     private ServerSocket server;  
     //存放断点数据   
     private Map<Long, FileLog> datas = new HashMap<Long, FileLog>();  
            
     public FileTransferServer(int port)  
     {  
         this.port = port;  
         //创建线程池，池中具有(cpu个数*50)条线程  
         executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 50);  
     }  
       
     /** 
     * 退出 
      */  
     public void quit()  
     {  
        this.quit = true;  
        try   
        {  
            server.close();  
        }   
        catch (IOException e)   
        {  
            e.printStackTrace();  
        }  
     }  
       
     /** 
     * 启动服务 
      * @throws Exception 
      */  
     public void start() throws Exception  
     {  
         //实现端口监听   
         server = new ServerSocket(port);  
         while(!quit)  
         {  
             try   
             {  
                 Socket socket = server.accept();  
                 //为支持多用户并发访问，采用线程池管理每一个用户的连接请求   
                 executorService.execute(new SocketTask(socket));  
             }   
             catch (Exception e)   
             {  
                 e.printStackTrace();  
             }  
         }  
     }  
       
     private final class SocketTask implements Runnable  
     {  
        private Socket socket = null;  
        public SocketTask(Socket socket)  
        {  
            this.socket = socket;  
        }  
          
        @Override 
        public void run()   
        {  
            try   
            {  
                //System.out.println("Accept: "+ socket.getInetAddress()+ ":"+ socket.getPort());  
                
                //这里的输入流PushbackInputStream可以回退到之前的某个点开始进行读取   
                PushbackInputStream inStream = new PushbackInputStream(socket.getInputStream());  
                //得到客户端发来的第一行协议数据：Content-Length=143253434;filename=xxx.3gp;sourceid=   
                //如果用户初次上传文件，sourceid的值为空。   
                String head = StreamTool.readLine(inStream); 
                //System.out.println(head);  
                if(head!=null)  
                {  
                    //下面从协议数据中提取各项参数值   
                    String[] items = head.split(";");
                    String transfertype = items[0].substring(items[0].indexOf("=")+1);                	
                    String filelength = items[1].substring(items[1].indexOf("=")+1);
                    String filename = items[2].substring(items[2].indexOf("=")+1); 
                    String sourceid = items[3].substring(items[3].indexOf("=")+1); 
                    
                    
                    //System.out.println("now is " + transfertype + "ing...");
	                //生产资源id，如果需要唯一性，可以采用UUID  
	                long id = System.currentTimeMillis(); 
	                FileLog log = null; 
	                if(sourceid!=null && !"".equals(sourceid)) 
	                {  
	                    id = Long.valueOf(sourceid);  
	                    //查找上传的文件是否存在上传记录   
	                    log = find(id);  
	                    }  
	                 File file = null;  
	                 int position = 0;  
	                    //如果上传的文件不存在上传记录,为文件添加跟踪记录   
	                 if(log==null)  
	                 {  
	                     //String path = new SimpleDateFormat("yyyy_MM_dd_HH").format(new Date());  
	                     //设置存放的位置与当前应用的位置有关  
	                     File dir = new File("file/"); 
	                     if(!dir.exists()) dir.mkdirs(); 
	                     file = new File(dir, filename); 
	                     //如果上传的文件发生重名，然后进行改名  
	                     /*if(file.exists())  
	                        {  
	                            filename = filename.substring(0, filename.indexOf(".")-1)+ dir.listFiles().length+ filename.substring(filename.indexOf(".")); 
	                            file = new File(dir, filename);  
	                        }  */
	                      save(id, file);  
	                    }  
	                    // 如果上传的文件存在上传记录,读取上次的断点位置   
	                  else  
	                  {  
	                      //从上传记录中得到文件的路径   
	                      file = new File(log.getPath());  
	                      if(file.exists())  
	                      {  
	                          File logFile = new File(file.getParentFile(), file.getName()+".log");  
	                          if(logFile.exists())  
	                          {  
	                              Properties properties = new Properties();  
	                              properties.load(new FileInputStream(logFile));  
	                              //读取断点位置   
	                              position = Integer.valueOf(properties.getProperty("length")); 
	                          }  
	                      }  
	                  }  
	                      
	                  OutputStream outStream = socket.getOutputStream();  
	                  String response = "sourceid="+ id+ "; position="+ position+ "\r\n"; 
	                  //服务器收到客户端的请求信息后，给客户端返回响应信息：sourceid=1274773833264;position=0  
	                  //sourceid由服务生成，唯一标识上传的文件，position指示客户端从文件的什么位置开始上传   
	                  outStream.write(response.getBytes());  
	                  RandomAccessFile fileOutStream = new RandomAccessFile(file, "rwd");  
	                  //设置文件长度   
	                  if(position==0) fileOutStream.setLength(Integer.valueOf(filelength));  
	                  //移动文件指定的位置开始写入数据   
	                  fileOutStream.seek(position);  
	                  byte[] buffer = new byte[1024];  
	                  int len = -1; 
	                  int length = position; 
	                  //从输入流中读取数据写入到文件中  
	                  while( (len=inStream.read(buffer)) != -1) 
	                  { 
	                      fileOutStream.write(buffer, 0, len);  
	                      length += len;  
	                      Properties properties = new Properties(); 
	                      properties.put("length", String.valueOf(length)); 
	                      FileOutputStream logFile = new FileOutputStream(new File(file.getParentFile(), file.getName()+".log"));  
	                      //实时记录文件的最后保存位置  
	                      properties.store(logFile, null);  
	                      logFile.close();  
	                    }  
	                  //如果长传长度等于实际长度则表示长传成功   
	                  if(length==fileOutStream.length()) delete(id);  
	                  fileOutStream.close();                   
	                  inStream.close();  
	                  outStream.close();  
	                  file = null;  
                  }
                   
            }  
            catch (Exception e)   
            {  
                e.printStackTrace();  
            }  
            finally 
            {  
                try  
                {  
                    if(socket!=null && !socket.isClosed()) socket.close();  
                }   
                catch (IOException e)  
                {  
                    e.printStackTrace();  
                }  
            }          
        }    
     }  
       
     public FileLog find(Long sourceid)  
     {  
         return datas.get(sourceid);  
     }  
     //保存上传记录   
     public void save(Long id, File saveFile)  
     {  
         //日后可以改成通过数据库存放   
         datas.put(id, new FileLog(id, saveFile.getAbsolutePath()));  
     }  
     //当文件上传完毕，删除记录   
     public void delete(long sourceid)  
     {  
         if(datas.containsKey(sourceid)) datas.remove(sourceid);  
     }  
       
     private class FileLog{  
        private Long id;  
        private String path;  
        public Long getId() {  return id;  }  
        public void setId(Long id) {  this.id = id;  }  
        public String getPath() {  return path;  }  
        public void setPath(String path) {  this.path = path; }  
        public FileLog(Long id, String path) 
        {  
            this.id = id;  
            this.path = path; 
        }     
     }  
}  
