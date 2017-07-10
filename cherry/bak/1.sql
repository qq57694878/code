select t1.`name`, t1.hostid
from hosts t1,hosts_groups t2
where t1.hostid = t2.hostid
and t2.groupid=6
#and t1.`status`=0