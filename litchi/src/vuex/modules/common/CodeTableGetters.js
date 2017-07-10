import store from '../../store.js'

import CodeTable from './CodeTableState'

export function getCodeTable(store){
	  return store.CodeTable
}

export function getCodeList(valuename){
  var list_map = new Array();
  for(var pro in CodeTable.state[valuename]){
    list_map.push({code:pro,value:CodeTable.state[valuename][pro]});
  }
  return list_map;
}
