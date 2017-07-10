<template>
<div class="row">
	<div class="col-xs-12">
		<div class="row" >
			<div class="col-xs-12">
				 <my-alert v-bind:alert-type="alertType" v-bind:content="alertContent" v-bind:show.sync="alertShow"></my-alert>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
			<!--  ===================== separate line ===================== -->

			<div id="sample-table-2_wrapper" class="dataTables_wrapper form-inline no-footer">
			
			<table id="sample-table-1" class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
			<thead>
				<tr>
					<!-- 
					<th class="center">
						<label class="position-relative">
							<input type="checkbox" class="ace">
							<span class="lbl"></span>
						</label>
					</th>
					-->
					<th>通知类型</th>
					<th>发件人</th>
					<th>收件人</th>
					<th>通知内容</th>

					<th>时间</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr v-for="notice in noticesList.list_data">

				<!--
				<td class="center">
					<label class="position-relative">
					<input type="checkbox" class="ace">
					<span class="lbl"></span>
					</label>
				</td>
				-->

				<td>
					<i class="ace-icon fa {{notice | picFilter}} " ></i>
				</td>
				<td> {{notice.cuser_name}} </td>
				<td> {{notice.ruser_name}} </td>
				<td> {{notice.notice_info}} </td>
				<td> {{notice.ctime}} </td>
				<td v-if="notice.has_read=='1'"> 已读</td>
				<td v-else> 未读</td>

				<td>
					<div class="hidden-sm hidden-xs btn-group" v-if="notice.has_read!='0'">
						<!-- <button class="btn btn-success" v-on:click="process(room.id)"> <i class="ace-icon fa fa-pencil-square-o"></i> 编辑 </button> -->
						<!-- <button class="btn btn-danger"  v-on:click="stop(room.id)"> <i class="ace-icon fa fa-trash-o"></i> 停用</button> -->
					</div>

					<div class="hidden-sm hidden-xs btn-group" v-else>
						<button class="btn btn-success" v-on:click="del(notice.id)"> <i class="ace-icon fa fa-pencil-square-o"></i> 删除 </button>
					</div>

					<div class="hidden-md hidden-lg" v-if="notice.has_read!='0'">
						<div class="inline position-relative">
							<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
							<i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
							</button>
							
							<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
								
								<!-- <li> -->
								<!-- <a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="编辑" v-on:click="process(room.id)"> -->
								<!-- <span class="green"> -->
								<!-- <i class="ace-icon fa fa-pencil-square-o bigger-120"></i> -->
								<!-- </span> -->
								<!-- </a> -->
								<!-- </li> -->

								<!-- <li> -->
								<!-- <a href="#" class="tooltip-error" data-rel="tooltip" title="" data-original-title="停用" v-on:click="stop(room.id)"> -->
								<!-- <span class="red"> -->
								<!-- <i class="ace-icon fa fa-trash-o bigger-120"></i> -->
								<!-- </span> -->
								<!-- </a> -->
								<!-- </li> -->
							</ul>
						</div>
					</div>
					<div class="hidden-md hidden-lg" v-else>
						<div class="inline position-relative">
							<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
							<i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
							</button>
							
							<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
								
								<li>
								<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="删除" v-on:click="del(notice.id)">
								<span class="green">
								<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
								</span>
								</a>
								</li>

							</ul>
						</div>
					</div>
					</td>
				</tr>

			</tbody>
			</table>

			<div class="row">
				<div class="col-xs-6">
				</div>
				<div class="col-xs-6">
					<div class="dataTables_paginate paging_simple_numbers">
						<pages v-bind:total-size="noticesList.pagetotal" v-bind:page-size="pageSize" v-on:page-set="pageSet" v-on:page-next="next" v-on:page-prev="prev" v-on:page-first="first" v-on:page-end="end"></pages>
					</div>
				</div>
			</div>

			</div>
		</div>
	</div>
</div>
</template>

<script>
import Pages from '../common/Pages'
import myAlert from '../common/myAlert'

import {getSendNoticeList} from '../../vuex/modules/notice/NoticeGetters'
import {reqSendNoticeList,deleteNotice} from '../../vuex/modules/notice/NoticeActions'
export default {
	data:function(){
		return {
			pageSize:10,
			alertShow:false,
			alertType:'alert-success',
			alertContent:'aaaa'
		}
	},
	components: {
		Pages,
		myAlert
	},
	vuex:{
		getters:{
			noticesList:getSendNoticeList
			//CT:getCodeTable
		},
		actions:{
			reqSendNoticeList,
			deleteNotice
		}
	},
	methods:{
		next:function(pageNumber) {
			console.debug("this parent page-next event "+pageNumber);
			this.reqSendNoticeList(pageNumber,this.pageSize);
		},
		prev:function(pageNumber){
			console.debug("this parent page-prev event "+pageNumber);
			this.reqSendNoticeList(pageNumber,this.pageSize);
		},
		first:function(pageNumber){
			console.debug("this parent page-first event "+pageNumber);
			this.reqSendNoticeList(pageNumber,this.pageSize);
		},
		end:function(pageNumber){
			console.debug("this parent page-end event "+pageNumber);
			this.reqSendNoticeList(pageNumber,this.pageSize);
		},
		pageSet:function(pageNumber){
			console.debug("this parent page-set event "+pageNumber);
			this.reqSendNoticeList(pageNumber,this.pageSize);
		},
		del:function(id){
			let self=this;
			bootbox.confirm("您确定删除通知吗？", function(result) {
				if(result) {
					self.deleteNotice(self,id);
				}
			});
		}

	},
	filters:{
		picFilter:function(value){
			if(value.notice_type=="1")
				return 'fa-comments green';
			else if(value.notice_type=="11")
				return 'fa-moon-o blue';
			else if(value.notice_type=="12")
				return 'fa-pencil-square-o purple';
			else if(value.notice_type=="13")
				return 'fa-paw grey';
			else if(value.notice_type=="14")
				return 'fa-sun-o orange';
			else
				return 'fa-ellipsis-h pink';
		}
	},
	ready:function(){
		this.reqSendNoticeList(1,10);
	}
}
</script>
