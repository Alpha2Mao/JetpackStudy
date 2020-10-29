package com.example.jetpack.room;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "student")
public class Student {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    public int id;

    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    public String name;

    @ColumnInfo(name = "age", typeAffinity = ColumnInfo.TEXT)
    public String age;

    public Student(int id, String name, String age){
        this.id = id;
        this.name = name;
        this.age = age;
    }


    /**
     * 由于Room只能识别和使用一个构造器，如果希望定义多个构造器，
     * 可以使用Ignore标签，让Room忽略这个构造器
     * 不仅如此，Ignore标签还可以用于字段
     * Room不会持久化被@Ignore标签标记过得字段的数据
     * @param id
     * @param name
     */
    @Ignore
    public Student(int id, String name){
        this.id = id;
        this.name = name;
    }

    @Ignore
    public Student(String name, String age){
        this.name = name;
        this.age = age;
    }
}
