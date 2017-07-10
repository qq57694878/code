import {SET_PROJECT_LIST,SET_PROJECT_QUERY,SET_PROJECTAPPLY_ADD_ENTERGUARD,DELETE_PROJECT_SUCCESS,SET_ONE_PROJECT_DETAIL } from '../../mutation-types'

const state = {
	projectslist:{
		pagetotal:0,
		projects:[
			{
				id:'',
				name:'app1',
				year:'2016',
				apply_dept_id:"app1",
				apply_dept_name:"app1",
				total_investment:"123.00",
				balance_investment:"123.00",
				apply_date:'2016-09-08',
				expected_sdate:'',
				expected_edate:'',
				status:'1'
			},
			{ 
				id:'',
				name:'app2',
				year:'2016',
				apply_dept_id:"app2",
				apply_dept_name:"app2",
				total_investment:"123.00",
				balance_investment:"123.00",
				apply_date:'2016-09-08',
				expected_sdate:'',
				expected_edate:'',
				status:'0'
			}
		]
	},
	projectquery:{
		year:'',
	    projectname:'',
		apply_dept:''
	},
	projectapplyaddenterguard:{
		state:'',
		projectid:''
	},
	oneproject_detail:{
		id:'1',
		name:'app1',
		year:'2016',
		apply_dept_id:"app2",
		apply_dept_name:"app2",
		total_investment:"123.00",
		balance_investment:"123.00",
		apply_date:'2016-09-08',
		expected_sdate:'',
		expected_edate:'',
		description:'',
		attachments:{  
			attach_code:{
				'1':{
					attach_code:'1',
					file_id:'1',
					file_name:'hello1',
					file_type:'', //文件类型 pic | pdf | word | exel
					url:'',
					creator_id:'',
					creator_name:'hhh',
					cdate:'2016-09-08'
				},
				'2':{
					attach_code:'2',
					file_id:'2',
					file_name:'hello2',
					file_type:'', //文件类型 pic | pdf | word | exel
					url:'',
					creator_id:'',
					creator_name:'wkq',
					cdate:'2016-09-08'
				}
			},
			others:[
			{
				attach_code:'',
				file_id:'3',
				file_name:'hello',
				file_type:'', //文件类型 pic | pdf | word | exel
				url:'',
				creator_id:'',
				creator_name:'hhh',
				cdate:'2016-09-08'
			}
			]
		},
		status:'1',
		applicant_id:'hh',
		applicant_name:'hh'
	}
}

const mutations = {
	[SET_PROJECT_LIST] (state,list) {
		state.projectslist = list
	},
	[SET_PROJECT_QUERY] (state,query) {
		state.projectquery = query
	},
	[SET_PROJECTAPPLY_ADD_ENTERGUARD] (state,enterguard) {
		state.projectapplyaddenterguard = enterguard
	},
	[SET_ONE_PROJECT_DETAIL] (state,data) {
		state.oneproject_detail = data
	},
	[DELETE_PROJECT_SUCCESS] (state,id) {
		var arr = state.projectslist.projects;
		var index = -1; 
		for(var i=0,length = arr.length;i < length; i++){
			if(arr[i].id==id){
				index= i;
				break;
			}
		}
		if(index != -1){
			// console.debug("====DELETE_NOTICE_SUCCESS========"+index);
			arr.splice(index,1);
		}
	}

}

export default {
	state,
	mutations
}
