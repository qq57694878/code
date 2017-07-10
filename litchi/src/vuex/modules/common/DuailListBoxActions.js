import Vue from 'vue'
import * as types from '../../mutation-types'
import {ajaxReq} from '../common/ajaxReq'

export const reqDuailListboxData = (store, comp, url, props, success, failure) => {
  if(url=='x86'){
    url='hardware/x86/get-x86-list-by-main-usage.do';
  }
  else if(url=='storage'){
    url='hardward/storage/get-storage-list-by-main-usage.do';
  }
console.debug(props);
  ajaxReq(store, {
    url: url,
    body: props,
    success: function (response) {
      let responseJson = response.json();
      if (success)success(comp, responseJson.data.list_data);
    },
    failure: function () {
      if (failure)failure();
    }
  });
}


