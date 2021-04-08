package com.ifenghui.storybookapi.adminapi.service;

import com.ifenghui.storybookapi.app.express.dao.ExpressCenterOrderDao;
import com.ifenghui.storybookapi.app.express.dao.ExpressCenterTrackDao;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterOrder;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterTrack;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ExpressCenterExcelServiceImpl implements ExpressCenterExcelService {

    @Autowired
    ExpressCenterTrackDao expressCenterTrackDao;

    @Autowired
    ExpressCenterOrderDao expressCenterOrderDao;


    @Override
    public Integer trackImport(String fileName, InputStream is) throws Exception {
        boolean notNull = false;
        Integer status = 1;
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            String error = "上传文件格式不正确";
            status = 0;
            return status;
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
//        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            notNull = true;
        }
//        System.out.println(sheet.getLastRowNum());
        List<ExpressCenterTrack> tracks = new ArrayList<>();
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            ExpressCenterTrack track = new ExpressCenterTrack();
            Row row = sheet.getRow(r);
            if (row == null){
                continue;
            }
            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);//设置读取转String类型
            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);

            //类型
            String srcType = row.getCell(0).getStringCellValue();
            //外部订单号
            String outOrderId = row.getCell(1).getStringCellValue();
            //根据类型和外部订单号查询订单
            if (null != outOrderId && !"".equals(outOrderId) && !"".equals(srcType) && null != srcType) {
                ExpressCenterOrder order = expressCenterOrderDao.findOne(outOrderId,Integer.valueOf(srcType));
                //判断是否存在订单
                if(order != null){
                    track.setCenterOrderId(order.getId());
                    //渠道
                    track.setSrcMark(order.getSrcMark());
                    //渠道类型;
                    track.setSrcType(order.getSrcType());
                    //联系方式
                    track.setPhone(order.getPhone());
                    //用户联系人
                    track.setFullname(order.getFullname());
                    //订购日期
                    track.setOrderTime(order.getOrderTime());
                    //年
                    String year = row.getCell(2).getStringCellValue();
                    if (null != year && !"".equals(year)) {
                        track.setYear(Integer.valueOf(year));
                    }
                    //月
                    String month = row.getCell(3).getStringCellValue();
                    if (null != month && !"".equals(month)) {
                        track.setMonth(Integer.valueOf(month));
                    }
                    //寄送刊数
                    String volCount = row.getCell(4).getStringCellValue();
                    if (null != volCount && !"".equals(volCount)) {
                        track.setVolCount(Integer.valueOf(volCount));
                    }
                    //物流公司 1：圆通 ；2：中通；3：韵达
                    String trackType = row.getCell(5).getStringCellValue();
                    if (null != trackType && !"".equals(trackType)) {
                        if ("圆通快递".equals(trackType)){
                            track.setTrackType(1);
                        }else if ("中通快递".equals(trackType)){
                            track.setTrackType(2);
                        }else if ("韵达快递".equals(trackType)){
                            track.setTrackType(3);
                        }
                    }
                    //运单号
                    String trackNo = row.getCell(6).getStringCellValue();
                    if (null != trackNo && !"".equals(trackNo)) {
                        track.setTrackNo(trackNo);
                    }
                    track.setCreateTime(new Date());
                    track.setSendTime(new Date());
                    track.setIsSend(1);
                    tracks.add(track);
                }
            }


        }
        //新增物流数据库
        for (ExpressCenterTrack t:tracks){
            // 物流是物流公司id+单号+订单id为唯一标识 如果存在数据不做操作,不存在则添加数据
            List<ExpressCenterTrack> trackList = expressCenterTrackDao.findTracks(t.getTrackType(),t.getTrackNo(),t.getCenterOrderId(),t.getMonth());
            if (trackList.size() == 0){
                expressCenterTrackDao.save(t);
                //修改order表中已发送期数,判断is_open状态
                ExpressCenterOrder order = expressCenterOrderDao.findOne(t.getCenterOrderId());
                if (order.getVolOver() == null){
                    order.setVolOver(0);
                }
                int volOver = order.getVolOver();
                volOver = volOver + t.getVolCount();
                order.setVolOver(volOver);
                if (order.getVolOver() == order.getVolCount()){
                    order.setIsOpen(0);
                }
                expressCenterOrderDao.save(order);

            }
        }




        return status;
    }


    @Override
    public Integer orderImport(String fileName, InputStream is) throws Exception {
        boolean notNull = false;
        Integer status = 1;
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            String error = "上传文件格式不正确";
            status = 0;
            return status;
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
//        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if (sheet != null) {
            notNull = true;
        }
        Row row0 = sheet.getRow(0);
        row0.getCell(1).getStringCellValue();
        List<ExpressCenterOrder> orders = new ArrayList<>();
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            ExpressCenterOrder order = new ExpressCenterOrder();
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }

            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);//设置读取转String类型
            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
//            row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
            row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);

            //id与数据库id不一致
            String id = row.getCell(0).getStringCellValue();

            //渠道类型
            String srcType = row.getCell(1).getStringCellValue();
            if (!"".equals(srcType) && null != srcType) {
                order.setSrcType(Integer.valueOf(srcType));
            }
            //渠道
            String srcMark = row.getCell(2).getStringCellValue();
            if (!"".equals(srcMark) && null != srcMark) {
                order.setSrcMark(srcMark);
            }
            //外部订单号 唯一标识
            String outOrderId = row.getCell(3).getStringCellValue();
            if (null != outOrderId && !"".equals(outOrderId)) {
                order.setOutOrderId(outOrderId);
            }
            //订购日期
            Date time = row.getCell(4).getDateCellValue();
            if (time != null){
                order.setOrderTime(time);
            }

            //姓名
            String name = row.getCell(5).getStringCellValue();
            if (null != name && !"".equals(name)) {
                order.setFullname(name);
            }
            //省
            String province = row.getCell(6).getStringCellValue();
            if (null != province && !"".equals(province)) {
                order.setProvince(province);
            }
            //市
            String city = row.getCell(7).getStringCellValue();
            if (null != city && !"".equals(city)) {
                order.setCity(city);
            }
            //区
            String district = row.getCell(8).getStringCellValue();
            if (null != district && !"".equals(district)) {
                order.setDistrict(district);
            }
            //地址
            String address = row.getCell(9).getStringCellValue();
            if (null != address && !"".equals(address)) {
                order.setAddress(address);
            }
            //联系电话
            String phone = row.getCell(10).getStringCellValue();
            if (null != phone && !"".equals(phone)) {
                order.setPhone(phone);
            }
            //总刊数
            String colCount = row.getCell(11).getStringCellValue();
            if (null != colCount && !"".equals(colCount)) {
                order.setVolCount(Integer.valueOf(colCount));
            }
            if (order.getOutOrderId() != null){
                order.setIsOpen(1);
                order.setVolOver(0);

                orders.add(order);
            }
        }
        //循环添加数据库
        for (ExpressCenterOrder o:orders){
            // 订单号暂时以外部单号为唯一标识 如果存在数据不做操作,不存在则添加数据
            List<ExpressCenterOrder> orderList = expressCenterOrderDao.findOrderByOutId(o.getOutOrderId());
            if (orderList.size() == 0){
                expressCenterOrderDao.save(o);
            }
        }

        return status;
    }


}
