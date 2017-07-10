
import Vue from 'vue'
import * as types from '../../mutation-types'

// var sendMsg=function(treeid){
// 	var treeObj=$.fn.zTree.getZTreeObj(treeid); 
// 	var nodes=treeObj.getSelectedNodes();
// 	for(var i=0;i<nodes.length;i++){
// 	   var userid = nodes[i].userId;
// 		console.debug('sendMsg'+userid);
// 	};
// }
				// <button data-bb-handler="confirm" id="sendbtn" type="button" onclick="sendMsg(\'treeDemo\')" class="btn btn-success">Send</button>\

import {getJWTtoken} from '../authentication/LoginActions'

export const getUsersTree = function(success,failure){
	let requestBody={};

	Vue.http.post('common/get_users_tree.do',{},{'headers':{'authentication':getJWTtoken()}}).then((response) => {
		// if(response.status==200){
			// console.debug("http request => "+url);
			let responseJson=response.json();
			if(!responseJson.error){

				console.debug(responseJson);

				if(success)success(responseJson.data);
			}else{
				console.debug(responseJson.error);
				if(failure)failure(responseJson);
			}
		// }
	}, (response) => {
		if(failure)failure(response);
		console.error("http request error=> "+'common/get_users_tree');
	});
}

export const send_notice = function(info,userslist,success,failure){
	console.debug('=======send_notice============'+info);
	let requestBody={notice_info:info,ruserid_list:userslist};
	Vue.http.post('tools/notice/send_notice.do',requestBody,{'headers':{'authentication':getJWTtoken()}}).then((response) => {
			let responseJson=response.json();
			if(!responseJson.error){

				console.debug(responseJson);

				if(success)success();
			}else{
				console.debug(responseJson.error);
				if(failure)failure();
			}
		// }
	}, (response) => {
		if(failure)failure(response);
		console.error("http request error=> "+'send_notice.do');
	});
}

export const showNoticeDialog=function(){
	var dialog = bootbox.dialog({
			closeButton:true,
			title:'Notice',
		    message: 
						'<div class="row"> \
							<div class="col-xs-12">\
								<div>\
									<ul id="treeDemo" class="ztree"></ul>\
								</div>\
							</div>\
						</div> \
			<div class="modal-footer" style="margin:0 -15px -15px -15px">\
				<input type="text" id="sendtxt" style="width:468px">\
				<button id="sendbtn" data-bb-handler="confirm" type="button" class="btn btn-success">Send</button>\
			</div>\
		',
			
			callback:function(result){
				console.debug('notice dialog');
			}
	});

	dialog.init(function(){

		var setting = {	
			check:{
				enable:true,
				chkStyle:"checkbox",
				chkboxType:{"Y":"ps","N":"ps"}
			}
		};
		var zNodes =[
			{ name:"父节点1 - 展开", open:true,
				children: [
					{ name:"父节点11 - 折叠",
						children: [
							{ name:"叶子节点111",userId:'2',orgId:'3'},
							{ name:"叶子节点112",userId:'3',orgId:'4'},
							{ name:"叶子节点113"},
							{ name:"叶子节点114"}
						]},
					{ name:"父节点12 - 折叠",
						children: [
							{ name:"叶子节点121"},
							{ name:"叶子节点122"},
							{ name:"叶子节点123"},
							{ name:"叶子节点124"}
						]},
					{ name:"父节点13 - 没有子节点", isParent:true,userId:'1',orgId:'2'}
				]},
			{ name:"父节点2 - 折叠",
				children: [
					{ name:"父节点21 - 展开", open:true,
						children: [
							{ name:"叶子节点211"},
							{ name:"叶子节点212"},
							{ name:"叶子节点213"},
							{ name:"叶子节点214"}
						]},
					{ name:"父节点22 - 折叠",
						children: [
							{ name:"叶子节点221"},
							{ name:"叶子节点222"},
							{ name:"叶子节点223"},
							{ name:"叶子节点224"}
						]},
					{ name:"父节点23 - 折叠",
						children: [
							{ name:"叶子节点231"},
							{ name:"叶子节点232"},
							{ name:"叶子节点233"},
							{ name:"叶子节点234"}
						]}
				]},
			{ name:"父节点3 - 没有子节点", isParent:true}

		];

		getUsersTree(function(treedata){
			$.fn.zTree.init($("#treeDemo"), setting,treedata.users);
		},function(){});
		// $(document).ready(function(){
			// $.fn.zTree.init($("#treeDemo"), setting, zNodes);
		// });
		$('#sendbtn').bind('click',function(){
			var treeObj=$.fn.zTree.getZTreeObj("treeDemo"); 
			// var nodes=treeObj.getSelectedNodes();
			var nodes=treeObj.getCheckedNodes(true);
			console.debug('======00000000000========='+nodes.length);
			var ruserid_list=[];
			for(var i=0;i<nodes.length;i++){
				if(nodes[i].type == '1'){
					var userid = nodes[i].id;
					ruserid_list.push(userid);
					console.debug('sendMsg'+userid);
				}
			};
			console.debug('=====sendbtn========'+$('#sendtxt').val());
			send_notice($('#sendtxt').val(),ruserid_list);
			// return false;
		});
	});
}

// export const showNoticeDialog=function(){
// 	var dialog = bootbox.dialog({
// 			closeButton:false,
// 		    message: 
// 		// '<div class="container"> \
// 		// 				<div class="row"> \
// 		// 					<div class="col-xs-4">\
// 		// 						<div>\
// 		// 							<ul id="treeDemo" class="ztree"></ul>\
// 		// 						</div>\
// 		// 					</div>\
// 		// 					<div class="col-xs-8">\
// 		// 						hello world\
// 		// 					</div>\
// 		// 				</div> \
// 		// 			</div>'

// 		'<div class="widget-box widget-color-pink ui-sortable-handle">\
// <div class="widget-header">\
// <h5 class="widget-title">Bottom Toolbox (Footer)</h5>\
// \
// <div class="widget-toolbar">\
// <a href="#" data-action="collapse">\
// <i class="1 ace-icon fa bigger-125 fa-chevron-up"></i>\
// </a>\
// </div>\
// \
// <div class="widget-toolbar no-border">\
// <button class="btn btn-xs btn-light bigger">\
// <i class="ace-icon fa fa-arrow-left"></i>\
// Prev\
// </button>\
// \
// <button class="btn btn-xs bigger btn-yellow dropdown-toggle" data-toggle="dropdown">\
// Next\
// <i class="ace-icon fa fa-chevron-down icon-on-right"></i>\
// </button>\
// \
// <ul class="dropdown-menu dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">\
// <li>\
// <a href="#">Action</a>\
// </li>\
// \
// <li>\
// <a href="#">Another action</a>\
// </li>\
// \
// <li>\
// <a href="#">Something else here</a>\
// </li>\
// <li class="divider"></li>\
// \
// <li>\
// <a href="#">Separated link</a>\
// </li>\
// </ul>\
// </div>\
// </div>\
// \
// <div class="widget-body" style="display: block;">\
// <div class="widget-main">\
// <p class="alert alert-info">\
// Nunc aliquam enim ut arcu aliquet adipiscing. Fusce dignissim volutpat justo non consectetur. Nulla fringilla eleifend consectetur.\
// </p>\
// <p class="alert alert-success">\
// Raw denim you probably havent heard of them jean shorts Austin.\
// </p>\
// </div>\
// \
// <div class="widget-toolbox padding-8 clearfix">\
// <button class="btn btn-xs btn-danger pull-left">\
// <i class="ace-icon fa fa-times"></i>\
// <span class="bigger-110">I dont accept</span>\
// </button>\
// \
// <button class="btn btn-xs btn-success pull-right">\
// <span class="bigger-110">I accept</span>\
// \
// <i class="ace-icon fa fa-arrow-right icon-on-right"></i>\
// </button>\
// </div>\
// </div>\
// </div>'
// 	});


