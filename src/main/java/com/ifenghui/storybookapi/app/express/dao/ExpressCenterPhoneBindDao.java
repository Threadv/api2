package com.ifenghui.storybookapi.app.express.dao;

import com.ifenghui.storybookapi.app.express.entity.ExpressCenterOrder;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterPhoneBind;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpressCenterPhoneBindDao extends JpaRepository<ExpressCenterPhoneBind, Integer> {

}
