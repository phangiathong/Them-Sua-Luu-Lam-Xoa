package com.example.th2_phangiathong_28_05_21.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.th2_phangiathong_28_05_21.model.SinhVien;

//Dữ liệu SQlite
//abstract class
public class DatabaseHelper extends SQLiteOpenHelper {
    //Khai báo thuộc tính
    public static final String Database_name = "Students.db";
    public static final String Table_name = "student_table";
    public static final String col_masv = "masv";
    public static final String col_tensv = "tensv";
    public static final String col_gt = "gt";
    public static final String col_lop = "lop";

    public DatabaseHelper(@Nullable Context context) {
        super(context, Database_name, null,1);
        //Chạy vô đây trước

    }
//Gọi kế tiếp
    @Override //Giúp tạo database
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+Table_name+"(masv TEXT primary key, tensv TEXT, gt TEXT, lop TEXT)");
    }

    //Để drop table
    @Override //Update database
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + Table_name);
        onCreate(db); //Tạo lại
    }
    //insert data, Để boolean vì insert thành công hoặc thất bại
    public boolean insertDataObject(SinhVien sv) {
        SQLiteDatabase db=this.getWritableDatabase(); //Ghi dữ liệu
        ContentValues cv=new ContentValues(); //Mỗi khi insert vào cho phép làm 2 giá trị
        cv.put(col_masv, sv.getMasv());
        cv.put(col_tensv,sv.getTensv());
        cv.put(col_gt,sv.getGt());
        cv.put(col_lop,sv.getLop());
        //Syntax của SQL
        Long result=db.insert(Table_name,null,cv); //Insert data
        if(result==-1) {
            return false; //Insert không thành công.
        }else {
            return true;
        }
    }
    //Insert data
    public boolean insertData(String masv, String tensv, String gt, String lop) {
        SQLiteDatabase db=this.getWritableDatabase(); //Ghi dữ liệu
        ContentValues cv=new ContentValues(); //Mỗi khi insert vào cho phép làm 2 giá trị
        cv.put(col_masv, masv);
        cv.put(col_tensv,tensv);
        cv.put(col_gt,gt);
        cv.put(col_lop,lop);
        Long result=db.insert(Table_name,null,cv); //Insert data
        if(result==-1) {
            return false; //Insert không thành công.
        }else {
            return true;
        }
    }
    //Show data
    public Cursor showData(){
        SQLiteDatabase db=this.getWritableDatabase(); //Ghi dữ liệu
        Cursor cursor=db.rawQuery("select * from "+Table_name,null); //null là không có tham số
        return cursor;
    }
    //Hàm xóa
    public Integer delete(String masv) {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(Table_name,"masv = ?",new String[]{masv});
    }
    //update
    public boolean update(String masv, String tensv, String gt, String lop){
        SQLiteDatabase db=this.getWritableDatabase(); //Ghi dữ liệu
        ContentValues cv=new ContentValues(); //Mỗi khi insert vào cho phép làm 2 giá trị
        cv.put(col_masv, masv);
        cv.put(col_tensv,tensv);
        cv.put(col_gt,gt);
        cv.put(col_lop,lop);
        int result=db.update(Table_name,cv,"masv = ?",new String[]{masv}); //Insert data
        if(result==-1) {
            return false; //Insert không thành công.
        }else {
            return true;
        }
    }
}
