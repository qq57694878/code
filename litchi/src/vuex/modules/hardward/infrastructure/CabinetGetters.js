/**
 * Created by cheng on 16/10/5.
 */

//查询列表初始化
export function getInitList(store){
  return store.hardward_cabinet.cabinetListInfo;
}

//表单列表初始化
export function getInitForm(store) {
  return store.hardward_cabinet.cabinetForm;
}

//取页面参数
export function getPageNum(store) {
  return store.hardward_cabinet.cabinetPagenum;
}
