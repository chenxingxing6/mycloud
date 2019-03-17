$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'front/file/list',
        datatype: "json",
        colModel: [
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden:true },
			{ label: '文件名', name: 'name', index: 'name', width: 80 ,
				formatter: function (value, grid, rows, state) {
                    return '<a href="javascript:void(0);" style="color:#f60" onclick="modify(\'' + rows.id + '\');">'+value+'</a>';
                }},
			{ label: '大小', name: 'length', index: 'length', width: 80 },
			{ label: '修改时间', name: 'opTime', index: 'op_time', width: 80},
            { label: '其他', name: 'viewFlag', index: 'view_flag', width: 80,
				formatter: function (value, grid, rows, state) {
                    return '<a href="javascript:void(0);" style="color:#f60" onclick="modify(\'' + rows.id + '\');">编辑 </a>' +
                    '<a href="javascript:void(0);" style="color:#f60" onclick="modify(\'' + rows.id + '\');">查看 </a>' +
                    '<a href="javascript:void(0);" style="color:#f60" onclick="modify(\'' + rows.id + '\');">下载 </a>';
                }}
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page",
            rows:"limit",
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		file: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.file = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.file.id == null ? "front/file/save" : "front/file/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.file),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}

			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "front/file/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "front/file/info/"+id, function(r){
                vm.file = r.file;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                page:page
            }).trigger("reloadGrid");
		}
	}
});
