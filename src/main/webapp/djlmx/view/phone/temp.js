Ext.define('dossieradmin.view.system.case.CaseView', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.caseView',
	title : '案卷类型管理',
	store : 'system.case.CaseStore',//这个就会加载这个数据的
	
	selType : 'checkboxmodel',
	selModel : {
        mode : 'MULTI'
    },
	columnLines : true,
	initComponent : function() {
		this.columns = [ {
			header : 'code',
			dataIndex : 'code',
			flex : 1,
		},  {
			header : 'type',
			dataIndex : 'pcode',
			
			renderer: function(value, metadata, record) {
				
				if(value==null || value=="") {
					//record.setColor();$body-background-color
					return "大类";
				} else{
					return "小类"; 						
				}
	        },
			
			flex : 1,
		}, {
			header : 'pcode',
			dataIndex : 'pcode',
			flex : 1,
		}, {
			header : 'name',
			dataIndex : 'name',
			flex : 2,
		}, ];
		
		this.callParent(arguments);
	},
//	viewConfig:{
//		
//		forceFit:true,getRowClass : function(record,rowIndex,rowParams,store){ 
//        if(record.data.pcode==null){
//            return '#999999';
//        }
//	  }
//	 },

	tbar : {
		xtype : 'toolbar',
		cls : 'x-panel-header',
		layout : 'column',
		items : [  {
            text : '添加',
            xtype : 'button',
            iconCls : 'tab-add-icon',
            tooltip : '添加'
        }, '-', {
            text : '删除',
            xtype : 'button',
            iconCls : 'tab-remove-icon',
            tooltip : '删除'
        },  '-', {
			labelAlign : 'right',
			fieldLabel : '输入查询code',
			itemId : 'code',
			text : 'codetext',
			xtype : 'textfield',
			tooltip : 'code'
		},'-', {
			fieldLabel : '填入查询内容',
			labelAlign : 'right',
			itemId : 'name',
			text : 'nametext',
			xtype : 'textfield',
			tooltip : 'name'
		}, '-', {
			xtype : 'button',
			iconCls : 'search-icon',
			tooltip : 'search',
			action : 'case-search',

		} ]
	
	},

	bbar : [ {
		xtype : 'pagingtoolbar',
		itemId : 'userbbar',
		store : 'system.enduser.EnduserStore',
		displayInfo : true,
	} ],

});