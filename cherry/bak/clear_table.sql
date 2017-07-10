select concat('delete from ',TABLE_SCHEMA,'.',TABLE_NAME,';') from information_schema.`TABLES` t1
where t1.TABLE_SCHEMA='app_cherry' and TABLE_TYPE='BASE TABLE'
AND TABLE_NAME not in('act_ge_property','sys_group','sys_group_privilege','sys_org_info','sys_privilege','sys_user','sys_user_group','hw_vm_platform','hw_virtual_machine');



SET foreign_key_checks = 0;