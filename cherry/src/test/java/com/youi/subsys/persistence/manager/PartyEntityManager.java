package com.youi.subsys.persistence.manager;

import com.youi.core.hibernate.HibernateEntityDao;
import com.youi.subsys.persistence.domain.PartyEntity;
import org.springframework.stereotype.Service;

@Service
public class PartyEntityManager extends HibernateEntityDao<PartyEntity> {
}
