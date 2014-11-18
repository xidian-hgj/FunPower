package com.xd.powercatsence;

import java.io.File;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UploadLogService   
{  
  private DBOpenHelper dbOpenHelper;  
  //给出上下文对象   
  public UploadLogService(Context context)  
  {  
      this.dbOpenHelper = new DBOpenHelper(context);  
  }  
  //保存上传文件断点数据   
  public void save(String sourceid, File uploadFile)  
  {  
      SQLiteDatabase db = dbOpenHelper.getWritableDatabase();  
      db.execSQL("insert into uploadlog(uploadfilepath, sourceid) values(?,?)",  
      new Object[]{uploadFile.getAbsolutePath(),sourceid});  
      }  
  //删除上传文件断点数据   
  public void delete(File uploadFile)  
  {  
      SQLiteDatabase db = dbOpenHelper.getWritableDatabase();  
      db.execSQL("delete from uploadlog where uploadfilepath=?", new Object[]{uploadFile.getAbsolutePath()});  
  }  
  //根据文件的上传路径得到绑定的id   
  public String getBindId(File uploadFile)  
  {  
      SQLiteDatabase db = dbOpenHelper.getReadableDatabase();  
      Cursor cursor = db.rawQuery("select sourceid from uploadlog where uploadfilepath=?",   
      new String[]{uploadFile.getAbsolutePath()});  
      if(cursor.moveToFirst())  
      {  
          return cursor.getString(0);  
      }  
      return null;  
  }  
}  

