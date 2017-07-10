import { SET_AUTHENTICATION,SET_LOGIN_ERROR} from '../../mutation-types'


const state = {
	authentication:0,
	login_error:false
}

const mutations = {
	[ SET_AUTHENTICATION] (state,auth) {
		state.authentication= auth
	},
	[ SET_LOGIN_ERROR] (state,error) {
		state.login_error= error
	}
}

export default {
	state,
	mutations
}
