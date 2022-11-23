package com.lzg.guli2.excel;

import com.alibaba.excel.EasyExcel;
import com.lzg.commonutils.R;
import com.lzg.guli2.edu.entity.DemoData;
import com.lzg.guli2.edu.handler.EasyExcelListener;
import com.lzg.guli2.edu.service.impl.CommentClient;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.util.List;

public class EasyExcelTest {
    private static List<DemoData> data;

//    static {
//        data = new ArrayList<>();
//        for (int i=0;i<10;i++) {
//            DemoData demoData = new DemoData();
//            demoData.setSno(i);
//            demoData.setSname("lucy" + i);
//            data.add(demoData);
//        }
//    }

    /**
     *         // 写法2：
     *         InputStream in = new BufferedInputStream(new FileInputStream("F:\\01.xlsx"));
     *         ExcelReader excelReader = EasyExcel.read(in, ReadData.class, new ExcelListener()).build();
     *         ReadSheet readSheet = EasyExcel.readSheet(0).build();
     *         excelReader.read(readSheet);
     *         // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
     *         excelReader.finish();
     * @param args
     */


    //easyExcel的写操作
    public static void main(String[] args) throws FileNotFoundException {
//        String excelFile = "f://test.xlsx";
//        EasyExcel.write(excelFile, DemoData.class).sheet("写入方法一").doWrite(data);

        String fileName = "f://test.xlsx";
//        InputStream in = new BufferedInputStream(new FileInputStream(fileName));
//        ExcelReader excelReader = EasyExcel.read(in, DemoData.class, new EasyExcelListener()).build();
//        ReadSheet readSheet = EasyExcel.readSheet(0).build();
//        excelReader.read(readSheet);
//        excelReader.finish();


        EasyExcel.read(fileName,DemoData.class,new EasyExcelListener()).sheet().doRead();

    }
    @Resource
    private CommentClient commentClient;

    @Test
    public void testRemoteOpenFeign() {
        R r = this.commentClient.remoteParseToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndWxpLXVzZXIiLCJpYXQiOjE2Njg4NjM2NzEsImV4cCI6MTY2ODk1MDA3MSwiaWQiOiIxNTkyNzE0NzI1NzM5ODIzMTA2Iiwibmlja25hbWUiOiJsaXpoaWdhbyJ9.DOBMp48hnnL5Nr63A17bpJ3r1UDbfkau16gwIiWyDuY");
        System.out.println(r.getData().get("user"));
    }
}
