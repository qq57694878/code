import * as types from '../../mutation-types'

const state = {
  items: []
}

const mutations = {
  [types.SET_DUAIL_LIST_BOX] (state, data) {
    state.items = data;
  }
}

export default {
  state,
  mutations
}
