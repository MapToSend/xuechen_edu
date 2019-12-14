package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.eduservice.bean.EduSubject;
import com.online.edu.eduservice.bean.query.SubjectSelect;
import com.online.edu.eduservice.bean.query.SubjectSelect_2;
import com.online.edu.eduservice.bean.query.TreeChildren;
import com.online.edu.eduservice.mapper.EduSubjectMapper;
import com.online.edu.eduservice.service.EduSubjectService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author 向长城
 * @since 2019-11-30
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    @Autowired
    private EduSubjectMapper subjectMapper;

    /**
     * 查询分类节点数
     *
     * @return
     */
    @Override
    public List<SubjectSelect> querySubject() {

        List<EduSubject> eduSubjects = subjectMapper.selectList(null);

        List<SubjectSelect> selects = new ArrayList<>();

        for (EduSubject eduSubject : eduSubjects) {

            if ("0".equals(eduSubject.getParentId())) {

                SubjectSelect subjectSelect = new SubjectSelect(eduSubject.getId(), eduSubject.getTitle());

                for (EduSubject subject : eduSubjects) {


                    if (eduSubject.getId().equals(subject.getParentId())) {
                        SubjectSelect_2 subjectSelect_1 = new SubjectSelect_2(subject.getId(), subject.getTitle());
                        subjectSelect.getChildren().add(subjectSelect_1);
                    }

                }

                selects.add(subjectSelect);

            }

        }

        return selects;
    }
    /**
     * 删除节点树
     * @param title
     * @return
     */
    @Override
    public Boolean deleteSub(String title) {
        try {
            QueryWrapper<EduSubject> objectQueryWrapper = new QueryWrapper<>();
            objectQueryWrapper.eq("title",title);

            EduSubject subject = subjectMapper.selectOne(objectQueryWrapper);

            //1级分类删除
            if ("0".equals(subject.getParentId())){
                QueryWrapper<EduSubject> objectQueryWrapper_1 = new QueryWrapper<>();

                objectQueryWrapper_1.eq("parent_id",subject.getId());

                List<EduSubject> eduSubjects = subjectMapper.selectList(objectQueryWrapper_1);

                for (EduSubject eduSubject : eduSubjects) {
                    subjectMapper.deleteById(eduSubject.getId());
                }
            }
            subjectMapper.deleteById(subject.getId());
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }

        return true;
    }



    /**
     * 课程节点树查询
     * data: [{
     *           label: '一级 1',
     *           children: [{
     *             label: '二级 1-1',
     *             children: [{
     *               label: '三级 1-1-1'
     *             }]
     *           }]
     *         }, {
     * @return
     */
    @Override
    public List<TreeChildren> list() {

        List<EduSubject> eduSubjects = subjectMapper.selectList(null);

        ArrayList<TreeChildren> treeChildren=new ArrayList<>();

        for (EduSubject eduSubject : eduSubjects) {

            if ("0".equals(eduSubject.getParentId())){
                //父节点
                TreeChildren father = new TreeChildren();
                //设置父节点
                father.setLabel(eduSubject.getTitle());

                for (EduSubject subject_1 : eduSubjects) {

                    if (eduSubject.getId().equals(subject_1.getParentId())){
                        //子节点
                        TreeChildren Children = new TreeChildren();

                        Children.setLabel(subject_1.getTitle());

                        father.getChildren().add(Children);
                    }

                }

                treeChildren.add(father);

            }

        }






        return treeChildren;
    }



    /**
     * 文件存储课程
     * @param file
     * @return
     */
    @Override
    @Transactional
    public Boolean uploadSubject(MultipartFile file) {

        try {
            InputStream inputStream = file.getInputStream();

            Workbook workbook=new HSSFWorkbook(inputStream);

            Sheet sheetAt = workbook.getSheetAt(0);
            int lastRowNum = sheetAt.getLastRowNum();



            for (int i = 1; i <lastRowNum+1 ; i++) {
                Row row = sheetAt.getRow(i);


                //如果有间隔跳过继续添加
                if (row==null){
                    continue;
                }

                Cell cell = row.getCell(0);



                String stringCellValue = cell.getStringCellValue();

                QueryWrapper<EduSubject> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("title",stringCellValue);
                EduSubject selecteduSubject = subjectMapper.selectOne(queryWrapper);

                if (selecteduSubject==null) {
                    //1级分类不存在 添加一级分类，并添加起对应行的二级分类


                    //1级分类存储
                    EduSubject eduSubject = new EduSubject();
                    eduSubject.setSort(0);
                    eduSubject.setParentId("0");
                    eduSubject.setTitle(stringCellValue);
                    subjectMapper.insert(eduSubject);

                    //二级分类存储
                    Cell cell_1 = row.getCell(1);
                    String stringCellValue_1 = cell_1.getStringCellValue();
                    QueryWrapper<EduSubject> queryWrapper_1=new QueryWrapper<>();
                    queryWrapper_1.eq("title",stringCellValue_1);
                    EduSubject selecteduSubject_1 = subjectMapper.selectOne(queryWrapper_1);

                    if (selecteduSubject_1==null){
                        EduSubject eduSubject_1 = new EduSubject();
                        eduSubject_1.setSort(0);
                        eduSubject_1.setParentId(eduSubject.getId());
                        eduSubject_1.setTitle(stringCellValue_1);
                        subjectMapper.insert(eduSubject_1);
                    }


                }else {
                    //一级分类存在，根据1级分类查询出对应的id，添加到二级分类中国
                    Cell cell_1 = row.getCell(1);
                    String stringCellValue_1 = cell_1.getStringCellValue();
                    QueryWrapper<EduSubject> queryWrapper_1=new QueryWrapper<>();
                    queryWrapper_1.eq("title",stringCellValue_1);
                    EduSubject selecteduSubject_1 = subjectMapper.selectOne(queryWrapper_1);

                    if (selecteduSubject_1==null){
                        EduSubject eduSubject_1 = new EduSubject();
                        eduSubject_1.setSort(0);
                        eduSubject_1.setParentId(selecteduSubject.getId());
                        eduSubject_1.setTitle(stringCellValue_1);
                        subjectMapper.insert(eduSubject_1);
                    }


                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


}
