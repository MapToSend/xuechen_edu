package com.line.edu.eduservice;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.common.R;
import com.online.edu.eduservice.EduServiceSpringboot;
import com.online.edu.eduservice.bean.EduTeacher;
import com.online.edu.eduservice.bean.query.Chapter;
import com.online.edu.eduservice.controller.EduTeacherController;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

/**
 * @author
 * @since 2018/12/13
 */
@RunWith(SpringRunner.class)
//启动Spring
@SpringBootTest(classes = EduServiceSpringboot.class)
public class CodeGenerator {

    @Autowired
    private EduTeacherController teacherController;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;




    /**
     * 集合排序
     */
    @Test
    public void D() throws Exception {
        String s = stringRedisTemplate.opsForValue().get("1201123855951765506");
        ObjectMapper objectMapper = new ObjectMapper();

        List o = objectMapper.readValue(s, List.class);
        List<Chapter> chapters = new ArrayList<>();

        for (int i = 0; i < o.size(); i++) {
            LinkedHashMap<String, Object> o1 = (LinkedHashMap<String, Object>) o.get(i);
            Chapter target = new Chapter();
            target.setId((String) o1.get("id"));
            target.setTitle((String) o1.get("title"));
            target.setSort((String) o1.get("sort"));
            target.setChapters((List<Chapter>) o1.get("chapters"));

            chapters.add(target);
        }

        System.out.println(chapters);
        Collections.sort(chapters);
        System.out.println(chapters);

    }

    /**
     * 集合排序
     */
    @Test
    public void C() throws Exception {

        List<Chapter> chapters = new ArrayList<>();

        Chapter chapter1 = new Chapter("1", "1");
        Chapter chapter2 = new Chapter("2", "2");
        Chapter chapter3 = new Chapter("3", "3");
        Chapter chapter4 = new Chapter("4", "4");

        chapters.add(chapter1);
        chapters.add(chapter4);
        chapters.add(chapter3);
        chapters.add(chapter2);

        System.out.println(chapters);
        Collections.sort(chapters);

        System.out.println(chapters);

    }

    /**
     * POI写入
     */
    @Test
    public void B() throws Exception {

        FileInputStream inputStream = new FileInputStream("D:\\log\\teacher.xls");

        Workbook workbook = new HSSFWorkbook(inputStream);

        Sheet sheet = workbook.getSheet("讲师列表");

        String stringCellValue = sheet.getRow(0).getCell(0).getStringCellValue();

        System.out.println(stringCellValue);

        Row row = sheet.getRow(0);

        Cell cell = row.getCell(0);

    }

    /**
     * POI读取
     */
    @Test
    public void A() throws Exception {

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("讲师列表");

        R r = teacherController.queryTeacher();

        Map<String, Object> data = r.getData();
        List<EduTeacher> teacher = (List<EduTeacher>) data.get("teacher");

        for (int i = 0; i < teacher.size(); i++) {
            Row row = sheet.createRow(i);

            Cell cell = row.createCell(0);
            EduTeacher eduTeacher = teacher.get(i);
            cell.setCellValue(eduTeacher.getName());

        }

        FileOutputStream fileOutputStream = new FileOutputStream("D:\\log\\teacher.xlsx");

        workbook.write(fileOutputStream);

        fileOutputStream.close();

    }

    /**
     * mp代码生成器
     */
    @Test
    public void run() {

        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("向长城");
        gc.setOpen(false); //生成后是否打开资源管理器
        gc.setFileOverride(false); //重新生成时文件是否覆盖
        gc.setServiceName("%sService");    //去掉Service接口的首字母I
        gc.setIdType(IdType.ID_WORKER_STR); //主键策略
        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
        gc.setSwagger2(true);//开启Swagger2模式

        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/xuechen");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4、包配置com.online.edu.eduservice
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("eduservice"); //模块名
        pc.setParent("com.online.edu");
        pc.setController("controller");
        pc.setEntity("bean");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("edu_chapter", "edu_video");
        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作

        strategy.setRestControllerStyle(true); //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符

        mpg.setStrategy(strategy);

        // 6、执行
        mpg.execute();
    }
}
