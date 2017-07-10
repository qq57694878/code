import Vue from 'vue'
// import * as types from '../../mutation-types'
import {ajaxReq} from '../common/ajaxReq'

export const reqComboboxData = (store, comp, url, success, failure) => {
  // store.dispatch(types.SET_COMBOBOX_ITEMS,[])
  ajaxReq(store, {
    url: url,
    success: function (response) {
      let responseJson = response.json();
      // store.dispatch(types.SET_COMBOBOX_ITEMS,responseJson.data.list_data)
      if (success)success(comp, responseJson.data.list_data);
    },
    failure: function () {
      if (failure)failure();
    }
  });
}

export const get_new_asset_num = (store, url, success, failure) => {
  store.dispatch(types.SET_COMBOBOX_ITEMS, [])
  ajaxReq(store, {
    url: url,
    success: function (response) {
      let responseJson = response.json();
      store.dispatch(types.SET_COMBOBOX_ITEMS, responseJson.data.list_data)
      if (success)success(responseJson);
    },
    failure: function () {
      if (failure)failure();
    }
  });
}

