import * as types from '../../mutation-types'

export const addToMainMenu = ({dispatch},menulist) => {
	dispatch(types.ADD_TO_MAINMENU,menulist)
}
