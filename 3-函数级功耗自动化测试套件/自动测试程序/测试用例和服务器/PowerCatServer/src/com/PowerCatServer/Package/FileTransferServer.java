package com.PowerCatServer.Package;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//��0���������˴���
public class FileTransferServer   
{  
     //�̳߳�   
     private ExecutorService executorService;  
     //�����˿�   
     private int port;  
     //�˳�   
     private boolean quit = false;  
     private ServerSocket server;  
     //��Ŷϵ�����   
     private Map<Long, FileLog> datas = new HashMap<Long, FileLog>();  
            
     public FileTransferServer(int port)  
     {  
         this.port = port;  
         //�����̳߳أ����о���(cpu����*50)���߳�  
         executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 50);  
     }  
       
     /** 
     * �˳� 
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
     * �������� 
      * @throws Exception 
      */  
     public void start() throws Exception  
     {  
         //ʵ�ֶ˿ڼ���   
         server = new ServerSocket(port);  
         while(!quit)  
         {  
             try   
             {  
                 Socket socket = server.accept();  
                 //Ϊ֧�ֶ��û��������ʣ������̳߳ع���ÿһ���û�����������   
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
                
                //�����������PushbackInputStream���Ի��˵�֮ǰ��ĳ���㿪ʼ���ж�ȡ   
                PushbackInputStream inStream = new PushbackInputStream(socket.getInputStream());  
                //�õ��ͻ��˷����ĵ�һ��Э�����ݣ�Content-Length=143253434;filename=xxx.3gp;sourceid=   
                //����û������ϴ��ļ���sourceid��ֵΪ�ա�   
                String head = StreamTool.readLine(inStream); 
                //System.out.println(head);  
                if(head!=null)  
                {  
                    //�����Э����������ȡ�������ֵ   
                    String[] items = head.split(";");
                    String transfertype = items[0].substring(items[0].indexOf("=")+1);                	
                    String filelength = items[1].substring(items[1].indexOf("=")+1);
                    String filename = items[2].substring(items[2].indexOf("=")+1); 
                    String sourceid = items[3].substring(items[3].indexOf("=")+1); 
                    
                    
                    //System.out.println("now is " + transfertype + "ing...");
	                //������Դid�������ҪΨһ�ԣ����Բ���UUID  
	                long id = System.currentTimeMillis(); 
	                FileLog log = null; 
	                if(sourceid!=null && !"".equals(sourceid)) 
	                {  
	                    id = Long.valueOf(sourceid);  
	                    //�����ϴ����ļ��Ƿ�����ϴ���¼   
	                    log = find(id);  
	                    }  
	                 File file = null;  
	                 int position = 0;  
	                    //����ϴ����ļ��������ϴ���¼,Ϊ�ļ���Ӹ��ټ�¼   
	                 if(log==null)  
	                 {  
	                     //String path = new SimpleDateFormat("yyyy_MM_dd_HH").format(new Date());  
	                     //���ô�ŵ�λ���뵱ǰӦ�õ�λ���й�  
	                     File dir = new File("file/"); 
	                     if(!dir.exists()) dir.mkdirs(); 
	                     file = new File(dir, filename); 
	                     //����ϴ����ļ�����������Ȼ����и���  
	                     /*if(file.exists())  
	                        {  
	                            filename = filename.substring(0, filename.indexOf(".")-1)+ dir.listFiles().length+ filename.substring(filename.indexOf(".")); 
	                            file = new File(dir, filename);  
	                        }  */
	                      save(id, file);  
	                    }  
	                    // ����ϴ����ļ������ϴ���¼,��ȡ�ϴεĶϵ�λ��   
	                  else  
	                  {  
	                      //���ϴ���¼�еõ��ļ���·��   
	                      file = new File(log.getPath());  
	                      if(file.exists())  
	                      {  
	                          File logFile = new File(file.getParentFile(), file.getName()+".log");  
	                          if(logFile.exists())  
	                          {  
	                              Properties properties = new Properties();  
	                              properties.load(new FileInputStream(logFile));  
	                              //��ȡ�ϵ�λ��   
	                              position = Integer.valueOf(properties.getProperty("length")); 
	                          }  
	                      }  
	                  }  
	                      
	                  OutputStream outStream = socket.getOutputStream();  
	                  String response = "sourceid="+ id+ "; position="+ position+ "\r\n"; 
	                  //�������յ��ͻ��˵�������Ϣ�󣬸��ͻ��˷�����Ӧ��Ϣ��sourceid=1274773833264;position=0  
	                  //sourceid�ɷ������ɣ�Ψһ��ʶ�ϴ����ļ���positionָʾ�ͻ��˴��ļ���ʲôλ�ÿ�ʼ�ϴ�   
	                  outStream.write(response.getBytes());  
	                  RandomAccessFile fileOutStream = new RandomAccessFile(file, "rwd");  
	                  //�����ļ�����   
	                  if(position==0) fileOutStream.setLength(Integer.valueOf(filelength));  
	                  //�ƶ��ļ�ָ����λ�ÿ�ʼд������   
	                  fileOutStream.seek(position);  
	                  byte[] buffer = new byte[1024];  
	                  int len = -1; 
	                  int length = position; 
	                  //���������ж�ȡ����д�뵽�ļ���  
	                  while( (len=inStream.read(buffer)) != -1) 
	                  { 
	                      fileOutStream.write(buffer, 0, len);  
	                      length += len;  
	                      Properties properties = new Properties(); 
	                      properties.put("length", String.valueOf(length)); 
	                      FileOutputStream logFile = new FileOutputStream(new File(file.getParentFile(), file.getName()+".log"));  
	                      //ʵʱ��¼�ļ�����󱣴�λ��  
	                      properties.store(logFile, null);  
	                      logFile.close();  
	                    }  
	                  //����������ȵ���ʵ�ʳ������ʾ�����ɹ�   
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
     //�����ϴ���¼   
     public void save(Long id, File saveFile)  
     {  
         //�պ���Ըĳ�ͨ�����ݿ���   
         datas.put(id, new FileLog(id, saveFile.getAbsolutePath()));  
     }  
     //���ļ��ϴ���ϣ�ɾ����¼   
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
