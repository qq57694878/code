<template>
<div class="row">
	<div class="col-xs-12">
		
		<div class="col-xs-2 no-padding-left">
			<button class="btn btn-info" style="border-radius:4px" type="button" v-if="enterguard.state!='readonly'" v-on:click="add">
				<i class="ace-icon glyphicon glyphicon-plus bigger-90"></i>
				添加付款信息	
			</button>
		</div>
	</div>
</div>
<div class="space-4"></div>
<div class="row">
	<div class="col-xs-12">
		<!--  ===================== separate line ===================== -->

		<div id="sample-table-2_wrapper" class="dataTables_wrapper form-inline no-footer">
	
		<table id="sample-table-1" class="table table-striped table-bordered table-hover" style="margin-bottom:0px">
		<thead>
			<tr>
				<th>序号</th>
				<th>付款条件</th>
				<th>预计付款时间</th>
				<th>付款金额</th>
				<th>付款状态</th>
				<th >操作</th>

			</tr>
		</thead>
		<tbody>
		<tr v-for="pay in detail.payment">

			<td> {{$index + 1 }}</td>
			<td> {{pay.terms}}</td>
			<td> {{pay.estimated_date}}</td>
			<td> {{pay.estimated_amount}}</td>
			<td v-if="pay.status=='0'">未付</td>
			<td v-else>已付</td>

			<td>
				<div class="hidden-sm hidden-xs btn-group" v-if="pay.status=='1'">
					<button class="btn btn-success" v-on:click="read($index)"> <i class="ace-icon fa fa-pencil-square-o"></i> 查看 </button>
				</div>
				<div class="hidden-sm hidden-xs btn-group" v-else>
					<button class="btn btn-danger" v-if="enterguard.state!='readonly'" v-on:click="del($index)"> <i class="ace-icon fa fa-trash-o"></i> 删除</button>
					<button class="btn btn-success" v-else v-on:click="read($index)"> <i class="ace-icon fa  fa-pencil-square-o"></i> 查看</button>
				</div>

				<div class="hidden-md hidden-lg" v-if="pay.status=='1'">
					<div class="inline position-relative">
						<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
						<i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
						</button>
						
						<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
							
							<li>
							<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="查看" v-on:click="read($index)">
							<span class="green">
							<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
							</span>
							</a>
							</li>

						</ul>
					</div>
				</div>
				<div class="hidden-md hidden-lg" v-else>
					<div class="inline position-relative">
						<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto" aria-expanded="false">
						<i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
						</button>
						
						<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
							
							<li v-if="enterguard.state!='readonly'" >
							<a href="#" class="tooltip-error" data-rel="tooltip" title="" data-original-title="删除" v-on:click="del($index)">
							<span class="red">
							<i class="ace-icon fa fa-trash-o bigger-120"></i>
							</span>
							</a>
							</li>

							<li v-else >
							<a href="#" class="tooltip-success" data-rel="tooltip" title="" data-original-title="查看" v-on:click="read($index)">
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

		</div>
	</div>
</div>
		<!--  ===================== separate line ===================== -->
<div class="space-4"></div>
<div class="row">
	<div class="col-xs-12">
		<div class="clearfix no-padding-left form-actions">
				<!-- <div class="col-md-offset-2 col-md-10"> -->
				<div class="col-md-12 no-padding-left">
					<button class="btn btn-info" type="button" v-if="enterguard.state!='readonly'" v-on:click="save">
						<i class="ace-icon fa fa-check bigger-110"></i>
						保存
					</button>

					&nbsp; &nbsp; &nbsp;
					<button class="btn" type="reset" v-on:click="back">
						<i class="ace-icon fa fa-undo bigger-110"></i>
						返回
					</button>

					<button class="btn btn-info pull-right" type="button" v-if="enterguard.state!='readonly'" v-on:click="enterexcplan">
						<!-- <i class="ace-icon fa fa-undo bigger-110"></i> -->
						合同执行
					</button>
				</div>
		</div>

	</div>
</div>
</template>
	
<script>
import {getOneContractDetail} from '../../vuex/modules/project-management/ContractRegisterGetters'		
import {deleteContractRegisterDetailPayment,setEnterContractRegisterDetailPayDetailGuard,saveContractDetail} from '../../vuex/modules/project-management/ContractRegisterActions'
import {getOneContractDetailEnterGuard} from '../../vuex/modules/project-management/ContractRegisterGetters'		
	
export default {
	vuex:{
			getters:{
				detail:getOneContractDetail,
				enterguard:getOneContractDetailEnterGuard
			},
			actions:{
				deleteContractRegisterDetailPayment, 
				saveContractDetail, 
				setEnterContractRegisterDetailPayDetailGuard
			}
		},
	methods:{
		del:function(indexdata){
			let self=this;
			bootbox.confirm("您确定要删除付款吗？", function(result) {
				if(result) {
					self.deleteContractRegisterDetailPayment(indexdata);
				}
			});

		},
		add:function(){
			var guard = {state:'',payindex:-1};
			this.setEnterContractRegisterDetailPayDetailGuard(guard);
			this.$router.go({ name:'contract_register_detail_paydetail'});
		},
		read:function(indexdata){
			console.debug("=========indexdata==================================="+indexdata);
			var guard = {state:'readonly',payindex:indexdata};
			this.setEnterContractRegisterDetailPayDetailGuard(guard);
			this.$router.go({ name:'contract_register_detail_paydetail'});
		},
		save:function(){
			let self=this;
			let requestBody={ 
					id:self.detail.id,
					pid:self.detail.pid,
					cno:self.detail.cno,
					name:self.detail.name,
					vender_id:self.detail.vender_id, 
					amount:self.detail.amount,
					sdate:self.detail.sdate,
					edate:self.detail.edate,
					responsible_person_id:self.detail.responsible_person_id,
					estimated_reception_time:self.detail.estimated_reception_time,
					supervision:self.detail.supervision,
					contract_file:self.detail.contract_file,
					payment:self.detail.payment
			};
			this.saveContractDetail(self,requestBody,function(){
					//	this.$dispatch('alertmsg','success');
					 self.$router.go({name:'contract_register'});
					},function(){
						self.$dispatch('alertmsg','failure');
			});
		},
		back:function(){
//			this.$router.go({ name:'contract_register'});
			this.$router.go({ name:this.enterguard.router});
		 }
	},
	ready:function(){	

	}

}

</script>
