package com.youi.subsys.persistence.manager;

import com.youi.core.hibernate.HibernateEntityDao;
import com.youi.subsys.persistence.domain.SubsysInfo;
import org.springframework.stereotype.Service;

/**
 * Created by jinliang on 2016/6/27.
 */
@Service
public class SubsysManager extends HibernateEntityDao<SubsysInfo> {
}
